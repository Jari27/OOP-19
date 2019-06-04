package graphEditor.controllers.actions.edit;

import graphEditor.controllers.edits.RemoveVertexEdit;
import graphEditor.models.Graph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class RemoveVertexAction extends AbstractAction implements Observer {

    private Graph graph;

    public RemoveVertexAction(Graph graph) {
        super("Remove vertex(-ices)");
        this.graph = graph;
        if (graph != null) {
            graph.addObserver(this);
            fixEnabled();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RemoveVertexEdit edit = new RemoveVertexEdit(graph);
    }

    private void fixEnabled() {
        if (graph.isAnyVertexSelected()) {
            setEnabled(true);
        } else {
            setEnabled(false);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        fixEnabled();
    }
}
