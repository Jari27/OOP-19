package graphEditor.controllers.actions;

import graphEditor.Constants;
import graphEditor.models.Graph;
import graphEditor.views.GraphFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class SaveGraphAction extends AbstractAction{

    private Graph graph;
    private GraphFrame parent;
    private JFileChooser fileChooser = null;

    public SaveGraphAction(Graph graph, GraphFrame parent) {
        super("Save");
        this.parent = parent;
        this.graph = graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (fileChooser == null) {
            fileChooser = parent.getFileChooser();
        }
        int result = fileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(Constants.EXTENSION)) {
                file = new File(file.getAbsoluteFile() + Constants.EXTENSION);
            }
            graph.save(file);
        }
    }
}
