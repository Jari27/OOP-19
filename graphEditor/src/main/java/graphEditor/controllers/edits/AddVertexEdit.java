package graphEditor.controllers.edits;

import graphEditor.models.Graph;
import graphEditor.models.GraphVertex;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class AddVertexEdit extends AbstractUndoableEdit {

    private Graph graph;
    private GraphVertex addedVertex;

    public AddVertexEdit(Graph graph) {
        this.graph = graph;
        addedVertex = graph.addVertex();
    }

    @Override
    public void undo() throws CannotUndoException {
        graph.removeVertex(addedVertex);
    }

    @Override
    public void redo() throws CannotRedoException {
        graph.addVertex(addedVertex);
    }
}
