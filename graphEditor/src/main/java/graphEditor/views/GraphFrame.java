package graphEditor.views;

import graphEditor.Constants;
import graphEditor.controllers.actions.edit.*;
import graphEditor.controllers.actions.file.ExitProgramAction;
import graphEditor.controllers.actions.file.LoadGraphAction;
import graphEditor.controllers.actions.file.NewFrameAction;
import graphEditor.controllers.actions.file.SaveGraphAction;
import graphEditor.controllers.actions.select.SelectAllAction;
import graphEditor.controllers.actions.select.SelectNoneAction;
import graphEditor.models.Graph;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

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
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
        // destroy old accelerator binds
        JMenuBar old = getJMenuBar();
        if (old != null) {
            old.removeAll();
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu select = new JMenu("Select");
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(select);
        // edit actions
        // undo
        JMenuItem undo = edit.add(new UndoAction(graph));
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        // redo
        JMenuItem redo = edit.add(new RedoAction(graph));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        // add vertex
        JMenuItem addVertex = edit.add(new AddVertexAction(graph));
        addVertex.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        // remove vertex
        JMenuItem removeVertex = edit.add(new RemoveVertexAction(graph));
        removeVertex.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        // add edge
        JMenuItem addEdge = edit.add(new AddEdgeAction(graph, panel));
        addEdge.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
        // remove edge
        JMenuItem removeEdge = edit.add(new RemoveEdgeAction(graph));
        removeEdge.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_DOWN_MASK));

        // file actions
        // load
        JMenuItem load = file.add(new LoadGraphAction(graph, this));
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        // save
        JMenuItem save = file.add(new SaveGraphAction(graph, this));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        // duplicate window
        JMenuItem duplicate = file.add(new NewFrameAction(graph));
        duplicate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        // quit
        JMenuItem quit = file.add(new ExitProgramAction());
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        // select action
        // select all
        JMenuItem selectAll = select.add(new SelectAllAction(graph));
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        // unselect all
        JMenuItem unselectAll = select.add(new SelectNoneAction(graph));
        unselectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                InputEvent.CTRL_MASK + InputEvent.SHIFT_DOWN_MASK));
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
