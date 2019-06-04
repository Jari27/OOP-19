package graphEditor.controllers.edits;

import graphEditor.models.Graph;
import graphEditor.models.GraphEdge;
import graphEditor.models.GraphVertex;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

public class RemoveVertexEdit extends AbstractUndoableEdit {

    private final ArrayList<GraphVertex> removedVertices = new ArrayList<>();
    private final ArrayList<GraphEdge> removedEdges = new ArrayList<>();

    private Graph graph;

    public RemoveVertexEdit(Graph graph) {
        this.graph = graph;
        if (graph != null) {
            // store removed vertices
            for (GraphVertex vertex : graph.getVertices()) {
                if (vertex.isSelected()) {
                    removedVertices.add(vertex);
                }
            }
            // store removed edges
            for (GraphEdge edge : graph.getEdges()) {
                for (GraphVertex vertex : removedVertices) {
                    if (edge.connectsTo(vertex) && !removedEdges.contains(edge)) {
                        removedEdges.add(edge);
                    }
                }
            }
            // remove edges and vertices
            for (GraphVertex vertex : removedVertices) {
                graph.removeVertex(vertex);
            }
        }
        graph.addEdit(this);
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        for (GraphVertex vertex : removedVertices) {
            graph.addVertex(vertex);
        }
        for (GraphEdge edge : removedEdges) {
            graph.addEdge(edge);
        }
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        for (GraphVertex vertex : removedVertices) {
            graph.removeVertex(vertex);
        }
    }
}
