package graphEditor.views;

import graphEditor.models.Graph;
import graphEditor.models.GraphVertex;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GraphPanel extends JPanel implements Observer {

    Graph graph;

    public GraphPanel(Graph graph) {
        this.graph = graph;
        graph.addObserver(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("Drawing!");
        super.paintComponent(g);
        Color old = g.getColor();
        g.setColor(Color.black);
        for (GraphVertex vertex : graph.getVertices()) {
            int x = vertex.getLocation().x;
            int y = vertex.getLocation().y;
            int width = vertex.getSize().width;
            int height = vertex.getSize().height;
            g.drawRect(x, y, width, height);
            drawCenteredString(x, y, width, height, vertex.getName(), g);
        }
        g.setColor(old);
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
