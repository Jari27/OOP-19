package graphEditor.controllers.actions;

import graphEditor.models.Graph;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddVertexAction extends AbstractAction {

    private Graph graph;

    public AddVertexAction(Graph graph) {
        super("Add vertex");
        this.graph = graph;
    }

    public AddVertexAction setGraph(Graph graph) {
        this.graph = graph;
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        graph.addVertex();
    }

}
