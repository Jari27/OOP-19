package graphEditor.controllers.actions;

import graphEditor.models.Graph;
import graphEditor.models.GraphVertex;
import graphEditor.views.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AddEdgeAction extends AbstractAction implements Observer {

    private Graph graph;
    private GraphPanel panel;

    public AddEdgeAction(Graph graph, GraphPanel panel) {
        super("Add edge(s)");
        this.graph = graph;
        this.panel = panel;
        if (graph != null) {
            graph.addObserver(this);
            fixEnabled();
        }
    }

    private void fixEnabled() {
        if (graph.isAnyVertexSelected()) {
            setEnabled(true);
        } else {
            setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<GraphVertex> tmpVertices = new ArrayList<>();
        for (GraphVertex vertex : graph.getVertices()) {
            if (vertex.isSelected()) {
                tmpVertices.add(vertex);
            }
        }
        if (tmpVertices.size() > 1) {
            for (int i = 0; i < tmpVertices.size(); i++) {
                for (int j = i; j < tmpVertices.size(); j++) {
                    graph.addEdge(tmpVertices.get(i), tmpVertices.get(j));
                }
            }
        } else if (tmpVertices.size() == 1) {
            AddEdgeMouseListener ml = new AddEdgeMouseListener(tmpVertices.get(0), panel, graph);
            panel.addMouseMotionListener(ml);
            panel.addMouseListener(ml);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        fixEnabled();
    }


    // temporary mouse listener for drawing new edge
    private class AddEdgeMouseListener implements MouseListener, MouseMotionListener {

        GraphVertex start;
        GraphPanel panel;
        Graph graph;
        Point drawStart;

        public AddEdgeMouseListener(GraphVertex start, GraphPanel panel, Graph graph) {
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
                    graph.addEdge(start, vertex); //updates graph
                    panel.setDrawingNewEdge(false);
                    panel.removeMouseMotionListener(this);
                    panel.removeMouseListener(this);
                    return;
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
