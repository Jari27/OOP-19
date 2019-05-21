package graphEditor.controllers.actions;

import graphEditor.models.Graph;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddVertexAction extends AbstractAction {

    private Graph graph;

    public AddVertexAction(Graph graph) {
        super("Add a new vertex");
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        graph.addVertex();
    }
}
