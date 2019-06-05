package graphEditor.controllers;

import graphEditor.models.Graph;
import graphEditor.models.GraphVertex;
import graphEditor.views.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
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
        // we iterate over a copy, so we can modify the order of vertices in the select functions
        // this ensure we can always select the topmost vertex draws it nicely and stuff
        ArrayList<GraphVertex> copyForIter = new ArrayList<>(graph.getVertices());
        for (int i = copyForIter.size() - 1; i >= 0; i--) {
            GraphVertex vertex = copyForIter.get(i);
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

    private void removeTextfield() {
        panel.getVertexNamer().setEditable(false);
        panel.getVertexNamer().setVisible(false);
        panel.validate();
        panel.repaint();
    }

    private void showNameChangeField(GraphVertex vertex) {
        // set text, location, size
        JTextField vertexNamer = panel.getVertexNamer();
        vertexNamer.setText(vertex.getName());
        int height = (int) (vertexNamer.getFontMetrics(vertexNamer.getFont()).getHeight() * 1.5);
        vertexNamer.setLocation(vertex.getLocation().x + 1,
                vertex.getLocation().y + (vertex.getSize().height - height) / 2 + 1);
        vertexNamer.setSize(vertex.getSize().width - 2, height);
        // remove old listeners
        for (FocusListener l : vertexNamer.getFocusListeners()) {
            vertexNamer.removeFocusListener(l);
        }
        for (KeyListener l : vertexNamer.getKeyListeners()) {
            vertexNamer.removeKeyListener(l);
        }
        // add new listeners
        // these listeners make sure the textbox disappears if it loses focus
        vertexNamer.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                vertexNamer.selectAll();
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                removeTextfield();
            }
        });
        vertexNamer.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    removeTextfield();
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    vertex.setName(vertexNamer.getText());
                    removeTextfield();
                }
            }
        });
        // show the textbox
        vertexNamer.setEditable(true);
        vertexNamer.setVisible(true);
        vertexNamer.requestFocusInWindow();
        vertexNamer.selectAll();
        panel.validate();
        panel.repaint();
    }

    private void showNameChangeFieldIfCorrectDoubleClick(MouseEvent e) {
        // find the selected vertex
        GraphVertex clicked = graph.getVertices().stream()
                .filter(vertex -> vertex.isInside(e.getPoint()))
                .findFirst().orElse(null);
        if (clicked != null) {
            showNameChangeField(clicked);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            showNameChangeFieldIfCorrectDoubleClick(e);
        }
        if (ignoreNextSelection) {
            ignoreNextSelection = false;
        } else {
            handleSelection(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        removeTextfield();
        if (graph.getVertices().stream().filter(graphVertex -> graphVertex.isSelected()).count() <= 1) {
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
