package graphEditor.views;

import graphEditor.Constants;
import graphEditor.controllers.actions.*;
import graphEditor.models.Graph;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class GraphFrame extends JFrame {

    Graph graph;
    GraphPanel panel;
    JFileChooser fc;

    public GraphFrame() throws HeadlessException {
        init();
        setGraph(new Graph());
    }

    private void init() {
        setTitle(Constants.NAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 800));
        setResizable(false);
        setVisible(true);
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.setFileHidingEnabled(true);
        fc.setFileFilter(new FileNameExtensionFilter(Constants.DESCRIPTION, Constants.EXTENSION));
        // set panel
        createOrUpdatePanel();
        // set menubar
        createOrUpdateMenuBar();
    }

    private void updateInterface() {
        createOrUpdatePanel();
        createOrUpdateMenuBar();
        pack();
    }

    private void createOrUpdatePanel() {
        if (this.panel == null) {
            panel = new GraphPanel();
            getContentPane().add(panel);
        } else {
            panel.setGraph(graph);
        }
    }

    /**
     * Always creates a new menubar and sets it. This is easier than updating all actions (but not all components, e.g. exit)
     */
    public void createOrUpdateMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        menuBar.add(file);
        menuBar.add(edit);
        edit.add(new AddVertexAction(graph));
        edit.add(new RemoveVertexAction(graph));
        edit.add(new AddEdgeAction(graph, panel));
        edit.add(new RemoveEdgeAction(graph));

        file.add(new LoadGraphAction(graph, this));
        file.add(new SaveGraphAction(graph, this));
        file.add(new ExitProgramAction());

        setJMenuBar(menuBar);
    }

    public GraphFrame setGraph(Graph graph) {
        this.graph = graph;
        this.updateInterface();

        return this;
    }

    public JFileChooser getFileChooser() {
        return fc;
    }
}
