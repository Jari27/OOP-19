package graphEditor.controllers.actions.edit;

import graphEditor.controllers.edits.AddEdgeEdit;
import graphEditor.models.Graph;
import graphEditor.views.GraphPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
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
        AddEdgeEdit edit = new AddEdgeEdit(graph, panel);
        if (edit.didSomething()) { // only store the edit if it actually added an edge
            graph.addEdit(edit);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        fixEnabled();
    }

}
