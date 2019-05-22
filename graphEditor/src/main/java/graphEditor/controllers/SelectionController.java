package graphEditor.controllers;

import graphEditor.models.Graph;
import graphEditor.models.GraphVertex;
import graphEditor.views.GraphPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

public class SelectionController implements MouseListener, MouseMotionListener {

    private final int DRAG_MARGIN = 10;

    private final Map<GraphVertex, Point> startLocations = new HashMap<>();
    private Graph graph;
    private GraphPanel panel;
    private int startX;
    private int startY;
    private boolean isDragging = false;

    public SelectionController(Graph graph, GraphPanel panel) {
        this.graph = graph;
        this.panel = panel;
    }

    private boolean isInside(MouseEvent e, GraphVertex vertex) {
        return e.getPoint().x >= vertex.getLocation().x
                && e.getPoint().x <= vertex.getLocation().x + vertex.getSize().width
                && e.getPoint().y >= vertex.getLocation().y
                && e.getPoint().y <= vertex.getLocation().y + vertex.getSize().height;
    }

    private void startDragging(MouseEvent e) {
        isDragging = true;
        startX = e.getX();
        startY = e.getY();
        for (GraphVertex vertex : graph.getVertices()) {
            if (vertex.isSelected()) {
                startLocations.put(vertex, vertex.getLocation());
            }
        }
        System.out.println("Start: " + startX + ", " + +startY);
    }

    private void handleSelection(MouseEvent event) {
        boolean hitOneOrMore = false;
        for (GraphVertex vertex : graph.getVertices()) {
            if (isInside(event, vertex)) {
                hitOneOrMore = true;
                if (event.getButton() == MouseEvent.BUTTON1 && event.isControlDown()) {
                    if (vertex.isSelected()) {
                        graph.unSelect(vertex);
                    } else {
                        graph.select(vertex);
                    }
                } else if (event.getButton() == MouseEvent.BUTTON1) {
                    graph.selectOnly(vertex);
                }
            }
        }
        if (!hitOneOrMore) {
            graph.unselectAll();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        handleSelection(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        boolean isPreSelected = false;
        boolean inside = false;
        for (GraphVertex vertex : graph.getVertices()) {
            if (isInside(e, vertex)) {
                inside = true;
                if (vertex.isSelected()) {
                    isPreSelected = true;
                }
            }
        }
        if (isPreSelected) {
            startDragging(e);
        } else if (inside) {
            handleSelection(e);
            startDragging(e);
        } else {
            handleSelection(e);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isDragging = false;
        startLocations.clear();
        if (e.getX() == startX && e.getY() == startY) {
            // mouse didnt move so handle selection
            handleSelection(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isDragging) {
            for (GraphVertex vertex : graph.getVertices()) {
                if (vertex.isSelected()) {
                    Point oldLocation = startLocations.get(vertex);

                    Point newLocation = new Point(oldLocation);
                    newLocation.x += e.getX() - startX;
                    newLocation.y += e.getY() - startY;
                    Dimension size = vertex.getSize();
                    if (newLocation.x >= panel.getWidth() - DRAG_MARGIN) {
                        newLocation.x = panel.getWidth() - DRAG_MARGIN;
                    } else if (newLocation.x + size.width <= DRAG_MARGIN) {
                        newLocation.x = DRAG_MARGIN - size.width;
                    }
                    if (newLocation.y >= panel.getHeight() - DRAG_MARGIN) {
                        newLocation.y = panel.getHeight() - DRAG_MARGIN;
                    } else if (newLocation.y + size.height <= DRAG_MARGIN) {
                        newLocation.y = DRAG_MARGIN - size.height;
                    }
                    System.out.println(newLocation);
                    vertex.setLocation(newLocation);
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
