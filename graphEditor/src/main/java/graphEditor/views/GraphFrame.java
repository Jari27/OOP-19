package graphEditor.views;

import graphEditor.controllers.actions.AddVertexAction;
import graphEditor.models.Graph;

import javax.swing.*;
import java.awt.*;

public class GraphFrame extends JFrame {

    Graph graph;

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
    }

    private void updateInterface() {
        updateMenuBar();
        updatePanel();
        pack();
    }

    private void updatePanel() {
        GraphPanel panel = new GraphPanel(graph);
        getContentPane().add(panel);
    }

    public void updateMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        menuBar.add(file);
        menuBar.add(edit);
        edit.add(new AddVertexAction(graph));
        edit.add("TODO3");

        file.add("TODO1");
        file.add("TODO2");
        setJMenuBar(menuBar);
    }

    public GraphFrame setGraph(Graph graph) {
        this.graph = graph;
        this.updateInterface();
        return this;
    }
}
