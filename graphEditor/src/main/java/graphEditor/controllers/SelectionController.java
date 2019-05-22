package graphEditor.controllers;

import graphEditor.models.Graph;
import graphEditor.models.GraphVertex;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class SelectionController extends Observable implements MouseListener {

    private final List<Object> selecteds = new ArrayList<>();
    private Graph graph;

    public SelectionController(Graph graph) {
        this.graph = graph;
        addObserver(graph);
    }

    public List<Object> getSelecteds() {
        return selecteds;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && !e.isControlDown()) {
            selecteds.clear();
        }
        for (GraphVertex vertex : graph.getVertices()) {
            if (e.getPoint().x < vertex.getLocation().x ||
                    e.getPoint().x > vertex.getLocation().x + vertex.getSize().width ||
                    e.getPoint().y < vertex.getLocation().y ||
                    e.getPoint().y > vertex.getLocation().y + vertex.getSize().height) {
                // not selected
                continue;
            }
            // selected
            if (e.getButton() == MouseEvent.BUTTON1) {
                selecteds.add(vertex);
            } else if (e.getButton() == MouseEvent.BUTTON2) {
                selecteds.remove(vertex);
            }
        }
        setChanged();
        notifyObservers();
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
}
