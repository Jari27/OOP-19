package graphEditor.views;

import graphEditor.controllers.SelectionController;
import graphEditor.controllers.actions.AddVertexAction;
import graphEditor.models.Graph;

import javax.swing.*;
import java.awt.*;

public class GraphFrame extends JFrame {

    Graph graph;
    GraphPanel panel;
    SelectionController selectionController;

    public GraphFrame() throws HeadlessException {
        init();
        setGraph(new Graph());
    }

    private void init() {
        setTitle("My favourite Graph Editor!");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 800));
        setResizable(false);
        setVisible(true);
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
        edit.add("TODO3");

        file.add("Load");
        file.add("Save");
        file.add("Close program");
        setJMenuBar(menuBar);

    }

    public GraphFrame setGraph(Graph graph) {
        if (this.graph != null) {
            this.graph.deleteObservers();
        }
        this.graph = graph;
        selectionController = new SelectionController(graph);
        this.updateInterface();

        return this;
    }
}
