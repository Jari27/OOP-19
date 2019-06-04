package graphEditor.controllers.edits;

import graphEditor.models.Graph;
import graphEditor.models.GraphEdge;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;
import java.util.List;

public class RemoveEdgeEdit extends AbstractUndoableEdit {


    private Graph graph;
    private final ArrayList<GraphEdge> removedEdges = new ArrayList<>();

    public RemoveEdgeEdit(Graph graph, List<GraphEdge> deletableEdges) {
        this.graph = graph;
        for (GraphEdge edge : deletableEdges) {
            graph.removeEdge(edge);
            removedEdges.add(edge);
        }
    }

    @Override
    public void undo() throws CannotUndoException {
        removedEdges.forEach(edge -> graph.addEdge(edge));
    }

    @Override
    public void redo() throws CannotRedoException {
        removedEdges.forEach(edge -> graph.removeEdge(edge));
    }
}
