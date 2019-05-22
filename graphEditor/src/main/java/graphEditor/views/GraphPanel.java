package graphEditor.views;

import graphEditor.models.Graph;
import graphEditor.models.GraphEdge;
import graphEditor.models.GraphVertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

public class GraphPanel extends JPanel implements Observer {

    private final Color COLOR_EDGE_DEFAULT = Color.BLACK;
    private final Color COLOR_FILL_DEFAULT = Color.WHITE;

    private final Color COLOR_EDGE_SELECTED = Color.BLUE;
    private final Color COLOR_FILL_SELECTED = Color.WHITE;

    Graph graph;

    public GraphPanel(){
    }

    public GraphPanel(Graph graph) {
        this.setGraph(graph);

    }

    public GraphPanel setGraph(Graph graph) {
        if (this.graph != null) {
            graph.deleteObservers();
        }
        this.graph = graph;
        this.graph.addObserver(this);
        // remove old listeners
        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }
        // add new listeners
        this.addMouseListener(this.graph.getSelectionController());
        return this;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graph == null) {
            return;
        }
        drawEdges(g);
        drawVertices(g);
    }

    private void drawEdges(Graphics g) {
        for (GraphEdge edge : graph.getEdges()) {
            if (graph.isSelected(edge)) {
                g.setColor(COLOR_EDGE_SELECTED);
            } else {
                g.setColor(COLOR_EDGE_DEFAULT);
            }
            GraphVertex v1 = edge.getV1();
            GraphVertex v2 = edge.getV2();
            int x1 = v1.getLocation().x + v1.getSize().width / 2;
            int y1 = v1.getLocation().y + v1.getSize().height / 2;

            int x2 = v2.getLocation().x + v2.getSize().width / 2;
            int y2 = v2.getLocation().y + v2.getSize().height / 2;

            g.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawVertices(Graphics g) {
        for (GraphVertex vertex : graph.getVertices()) {
            int x = vertex.getLocation().x;
            int y = vertex.getLocation().y;
            int width = vertex.getSize().width;
            int height = vertex.getSize().height;
            if (graph.isSelected(vertex)) {
                g.setColor(COLOR_FILL_SELECTED);
            } else {
                g.setColor(COLOR_FILL_DEFAULT);
            }
            g.fillRect(x, y, width, height);
            if (graph.isSelected(vertex)) {
                g.setColor(COLOR_EDGE_SELECTED);
            } else {
                g.setColor(COLOR_EDGE_DEFAULT);
            }
            g.drawRect(x, y, width, height);
            drawCenteredString(x, y, width, height, vertex.getName(), g);
        }
    }

    private void drawCenteredString(int x, int y, int width, int height, String text, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int newX = x + (width - fm.stringWidth(text)) / 2;
        int newY = y + (height - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, newX, newY);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
