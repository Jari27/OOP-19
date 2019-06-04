package graphEditor.controllers.actions.file;

import graphEditor.models.Graph;
import graphEditor.views.GraphFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewFrameAction extends AbstractAction {

    Graph graph;

    public NewFrameAction(Graph graph) {
        super("Duplicate this window");
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraphFrame frame = new GraphFrame();
        frame.setGraph(graph);
    }
}
