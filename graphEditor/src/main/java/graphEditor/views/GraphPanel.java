package graphEditor.views;

import graphEditor.controllers.SelectionController;
import graphEditor.models.Graph;
import graphEditor.models.GraphEdge;
import graphEditor.models.GraphVertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.Observable;
import java.util.Observer;

public class GraphPanel extends JPanel implements Observer {

    private final Color COLOR_NEW_EDGE = Color.RED;

    private final Color COLOR_VERTEX_EDGE_DEFAULT = Color.BLACK;
    private final Color COLOR_VERTEX_FILL_DEFAULT = Color.WHITE;
    private final Color COLOR_EDGE_DEFAULT = Color.BLACK;
    private final Color COLOR_TEXT_DEFAULT = Color.BLACK;

    private final Color COLOR_VERTEX_EDGE_SELECTED = Color.BLUE;
    private final Color COLOR_VERTEX_FILL_SELECTED = Color.CYAN;
    private final Color COLOR_TEXT_SELECTED = Color.BLACK;
    private final Color COLOR_EDGE_SELECTED = Color.BLUE;

    private final int DEFAULT_LINE_WIDTH = 2; //px

    Graph graph;

    private boolean drawingNewEdge = false;
    private Line2D newEdge;
    private final JTextField vertexNamer = new JTextField(15);

    public GraphPanel(){
        createVertexNamer();
        this.setLayout(null);
    }

    public GraphPanel(Graph graph) {
        this.setGraph(graph);
        this.setLayout(null);
        createVertexNamer();
    }

    private void createVertexNamer() {

        vertexNamer.setHorizontalAlignment(JTextField.CENTER);
        vertexNamer.setVisible(false);
        vertexNamer.setEditable(true);
        vertexNamer.getCaret().setSelectionVisible(true);
        vertexNamer.getCaret().setVisible(true);
//        vertexNamer.setOpaque(true);
//        vertexNamer.setRequestFocusEnabled(true);
        vertexNamer.setBorder(null);
//        vertexNamer.setBackground(Color.GREEN);
        this.add(vertexNamer);
        this.validate();
    }

    public GraphPanel setGraph(Graph graph) {
        if (this.graph != null) {
            graph.deleteObserver(this);
        }
        this.graph = graph;
        this.graph.addObserver(this);
        // remove old listeners
        for (MouseMotionListener listener : getMouseMotionListeners()) {
            removeMouseMotionListener(listener);
        }
        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }
        // add new listeners
        SelectionController controller = new SelectionController(graph, this);
        this.addMouseListener(controller);
        this.addMouseMotionListener(controller);

        return this;
    }


    private void drawNewEdge(Graphics g) {
        g.setColor(COLOR_NEW_EDGE);
        g.drawLine((int) newEdge.getX1(), (int) newEdge.getY1(), (int) newEdge.getX2(), (int) newEdge.getY2());
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
        for (int i = graph.getVertices().size() - 1; i >= 0; i--) {
            GraphVertex vertex = graph.getVertices().get(i);
            int x = vertex.getLocation().x;
            int y = vertex.getLocation().y;
            int width = vertex.getSize().width;
            int height = vertex.getSize().height;
            if (vertex.isSelected()) {
                g.setColor(COLOR_VERTEX_FILL_SELECTED);
            } else {
                g.setColor(COLOR_VERTEX_FILL_DEFAULT);
            }
            g.fillRect(x, y, width, height);
            if (vertex.isSelected()) {
                g.setColor(COLOR_VERTEX_EDGE_SELECTED);
            } else {
                g.setColor(COLOR_VERTEX_EDGE_DEFAULT);
            }
            g.drawRect(x, y, width, height);
            if (vertex.isSelected()) {
                g.setColor(COLOR_TEXT_SELECTED);
            } else {
                g.setColor(COLOR_TEXT_DEFAULT);
            }
            drawCenteredString(x, y, width, height, vertex.getName(), g);
        }
    }

    private void drawCenteredString(int x, int y, int width, int height, String text, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        // ensure we dont pass boundaries
        boolean removed = false;
        while (fm.stringWidth(text) > width) {
            removed = true;
            text = text.substring(0, text.length() - 1);
        }
        if (removed) {
            text = text.substring(0, text.length() - 3) + "...";
        }
        // calculate location and draw
        int newX = x + (width - fm.stringWidth(text)) / 2;
        int newY = y + (height - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, newX, newY);
    }

    public void setDrawingNewEdge(boolean drawingNewEdge) {
        this.drawingNewEdge = drawingNewEdge;
    }

    public void setNewEdgeLocation(Line2D location) {
        this.newEdge = location;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // set line thickness
        ((Graphics2D) g).setStroke(new BasicStroke(DEFAULT_LINE_WIDTH));
        if (graph == null) {
            return;
        }
        drawEdges(g);
        if (drawingNewEdge) {
            drawNewEdge(g);
        }

        drawVertices(g);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    public JTextField getVertexNamer() {
        return vertexNamer;
    }
}
