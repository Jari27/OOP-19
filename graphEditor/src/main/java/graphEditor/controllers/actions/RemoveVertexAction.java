package graphEditor.controllers.actions;

import graphEditor.models.Graph;
import graphEditor.models.GraphVertex;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class RemoveVertexAction extends AbstractAction implements Observer {

    private Graph graph;

    public RemoveVertexAction(Graph graph) {
        super("Remove vertex/vertices");
        this.graph = graph;
        if (graph != null) {
            graph.addObserver(this);
            fixEnabled();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (graph != null) {
            ArrayList<GraphVertex> tmpList = new ArrayList<>();
            for (GraphVertex vertex : graph.getVertices()) {
                if (vertex.isSelected()) {
                    tmpList.add(vertex);
                }
            }
            for (GraphVertex vertex : tmpList) {
                graph.removeVertex(vertex);
            }
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
    public void update(Observable o, Object arg) {
        fixEnabled();
    }
}
