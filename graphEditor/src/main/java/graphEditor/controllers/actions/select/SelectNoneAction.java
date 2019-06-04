package graphEditor.controllers.actions.select;

import graphEditor.models.Graph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class SelectNoneAction extends AbstractAction implements Observer {
    // does not need to be undoable
    private Graph graph;

    public SelectNoneAction(Graph graph) {
        super("Deselect all");
        this.graph = graph;
        if (graph != null) {
            graph.addObserver(this);
            fixEnabled();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        graph.unselectAll();
    }

    @Override
    public void update(Observable o, Object arg) {
        fixEnabled();
    }

    private void fixEnabled() {
        if (graph.getVertices().stream().anyMatch(vertex -> vertex.isSelected())) {
            setEnabled(true);
        } else {
            setEnabled(false);
        }
    }
}
