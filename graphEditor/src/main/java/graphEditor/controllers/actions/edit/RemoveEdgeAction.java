package graphEditor.controllers.actions.edit;

import graphEditor.controllers.edits.RemoveEdgeEdit;
import graphEditor.models.Graph;
import graphEditor.models.GraphEdge;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

public class RemoveEdgeAction extends AbstractAction implements Observer {

    private Graph graph;

    public RemoveEdgeAction(Graph graph) {
        super("Remove edge(s)");
        this.graph = graph;
        if (graph != null) {
            graph.addObserver(this);
            fixEnabled();
        }
    }

    private List<GraphEdge> getDeletableEdges() {
        return graph.getEdges().stream()
                .filter(edge -> graph.isSelected(edge) && edge.getV1().isSelected() && edge.getV2().isSelected())
                .collect(Collectors.toList());
    }

    private void fixEnabled() {
        if (getDeletableEdges().isEmpty()) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RemoveEdgeEdit edit = new RemoveEdgeEdit(graph, getDeletableEdges());
        graph.addEdit(edit);
    }

    @Override
    public void update(Observable o, Object arg) {
        fixEnabled();
    }
}
