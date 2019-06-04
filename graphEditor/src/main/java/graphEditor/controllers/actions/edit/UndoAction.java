package graphEditor.controllers.actions.edit;

import graphEditor.models.Graph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class UndoAction extends AbstractAction implements Observer {

    private Graph graph;

    public UndoAction(Graph graph) {
        super("Undo");
        this.graph = graph;
        if (graph != null) {
            graph.addObserver(this);
            fixEnabled();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        graph.undo();
    }

    private void fixEnabled() {
        if (graph.canUndo()) {
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
