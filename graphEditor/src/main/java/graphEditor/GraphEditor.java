package graphEditor;

import graphEditor.models.Graph;
import graphEditor.views.GraphFrame;

import javax.swing.*;
import java.io.File;

class GraphEditor {

    public static void main(String[] args) {
        Graph graph = null;
        if (args.length > 0) {
            String filename = args[0];
            File file = new File(filename);
            graph = new Graph(file);
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        GraphFrame frame = new GraphFrame();
        if (graph != null) {
            frame.setGraph(graph);
        }
    }
}