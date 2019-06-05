package graphEditor.controllers.edits;

import graphEditor.models.Graph;
import graphEditor.models.GraphEdge;
import graphEditor.models.GraphVertex;
import graphEditor.views.GraphPanel;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddEdgeEdit extends AbstractUndoableEdit {

    private final ArrayList<GraphEdge> addedEdges = new ArrayList<>();
    private Graph graph;
    private GraphPanel panel;

    public AddEdgeEdit(Graph graph, GraphPanel panel) {
        this.graph = graph;
        this.panel = panel;
        List<GraphVertex> selectedVertices = graph.getVertices().stream()
                .filter(vertex -> vertex.isSelected())
                .collect(Collectors.toList());

        if (selectedVertices.size() > 1) {
            for (int i = 0; i < selectedVertices.size(); i++) {
                for (int j = i; j < selectedVertices.size(); j++) {
                    if (graph.hasEdgeBetween(selectedVertices.get(i), selectedVertices.get(j))) {
                        continue;
                    } else {
                        GraphEdge newEdge = graph.addEdge(selectedVertices.get(i), selectedVertices.get(j));
                        addedEdges.add(newEdge);
                    }

                }
            }
        } else if (selectedVertices.size() == 1) {
            AddEdgeMouseListener ml = new AddEdgeMouseListener(selectedVertices.get(0), panel, graph);
            panel.addMouseMotionListener(ml);
            panel.addMouseListener(ml);
        }
    }

    public boolean didSomething() {
        return addedEdges.size() > 0;
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        addedEdges.forEach((edge) -> graph.removeEdge(edge));
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        addedEdges.forEach((edge) -> graph.addEdge(edge));
    }

    // temporary mouse listener for drawing new edge
    private class AddEdgeMouseListener implements MouseListener, MouseMotionListener {

        GraphVertex start;
        GraphPanel panel;
        Graph graph;
        Point drawStart;

        AddEdgeMouseListener(GraphVertex start, GraphPanel panel, Graph graph) {
            this.start = start;
            this.panel = panel;
            this.graph = graph;
            panel.setDrawingNewEdge(true);
            drawStart = new Point();
            drawStart.x = start.getLocation().x + start.getSize().width / 2;
            drawStart.y = start.getLocation().y + start.getSize().height / 2;
            Line2D line = new Line2D.Double(drawStart, start.getLocation());
            panel.setNewEdgeLocation(line);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            for (GraphVertex vertex : graph.getVertices()) {
                if (vertex.isInside(e.getPoint())) {
                    if (!graph.hasEdgeBetween(start, vertex)) {
                        GraphEdge newEdge = graph.addEdge(start, vertex); //updates graph
                        addedEdges.add(newEdge);
                    } else {
                        // TODO remove debugging prints
                        System.out.println("Already an edge between these two vertices. Doing nothing.");
                    }
                    break;
                }
            }
            panel.setDrawingNewEdge(false);
            panel.removeMouseMotionListener(this);
            panel.removeMouseListener(this);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Line2D line = new Line2D.Double(drawStart, e.getPoint());
            panel.setNewEdgeLocation(line);
        }
    }
}
