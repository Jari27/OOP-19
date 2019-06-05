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
    private boolean ignoreNextSelection = false; // ensures release and click dont trigger select + deselect

    public SelectionController(Graph graph, GraphPanel panel) {
        this.graph = graph;
        this.panel = panel;
    }

    private void startDragging(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        for (GraphVertex vertex : graph.getVertices()) {
            if (vertex.isSelected()) {
                // these vertices might have to be moved
                startLocations.put(vertex, vertex.getLocation());
                if (vertex.isInside(e.getPoint())) {
                    // we need to start in a selected vertex for a dragging event
                    isDragging = true;
                }
            }
        }
    }

    private void endDragging(MouseEvent e) {
        isDragging = false;
        startLocations.clear();
    }

    private void doDrag(MouseEvent e) {
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
                    vertex.setLocation(newLocation);
                }
            }
        }
    }

    private void handleSelection(MouseEvent e) {
        boolean hitOne = false;
        for (GraphVertex vertex : graph.getVertices()) {
            if (vertex.isInside(e.getPoint())) {
                hitOne = true;
                if (e.getButton() == MouseEvent.BUTTON1 && !vertex.isSelected()) {
                    // add vertex to selection
                    if (e.isControlDown()) {
                        graph.select(vertex);
                    } else {
                        graph.selectOnly(vertex);
                    }
                } else if (e.getButton() == MouseEvent.BUTTON1 && vertex.isSelected()) {
                    // remove vertex from selection
                    if (e.isControlDown()) {
                        graph.unSelect(vertex);
                    } else {
                        graph.selectOnly(vertex);
                    }
                }
            }
        }
        if (!hitOne && e.getButton() == MouseEvent.BUTTON1) {
            graph.unselectAll();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
         if (ignoreNextSelection) {
            ignoreNextSelection = false;
            return;
        }
        handleSelection(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(graph.getVertices().stream().filter(graphVertex -> graphVertex.isSelected()).count() <= 1) {
            handleSelection(e);
            ignoreNextSelection = true;
        }
        startDragging(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        endDragging(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        doDrag(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
