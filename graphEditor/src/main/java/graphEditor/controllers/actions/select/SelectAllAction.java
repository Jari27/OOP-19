package graphEditor.controllers.actions.select;

import graphEditor.models.Graph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class SelectAllAction extends AbstractAction implements Observer {

    // does not need to be undoable
    private Graph graph;

    public SelectAllAction(Graph graph) {
        super("Select all");
        this.graph = graph;
        if (graph != null) {
            graph.addObserver(this);
            fixEnabled();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        graph.selectAll();
    }

    @Override
    public void update(Observable o, Object arg) {
        fixEnabled();
    }

    private void fixEnabled() {
        // need at least one vertex to select it and not all vertices selected
        if (graph.getVertices().isEmpty() || graph.getVertices().stream().allMatch(vertex -> vertex.isSelected())) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }
}
