package graphEditor.controllers.actions.file;

import graphEditor.Constants;
import graphEditor.models.Graph;
import graphEditor.views.GraphFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class LoadGraphAction extends AbstractAction {

    private Graph graph;
    private GraphFrame parent;
    private JFileChooser fileChooser = null;

    public LoadGraphAction(Graph graph, GraphFrame parent) {
        super("Load");
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
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith("." + Constants.EXTENSION)) {
                System.out.println("Probably an invalid file but whatever.");
            }
            try {
                graph.load(file);
            } catch (Exception exc) {
                System.out.println("We encountered an error trying to load your file. " +
                        "Are you sure this is the correct format?");
                exc.printStackTrace();
            }
        }
    }
}
