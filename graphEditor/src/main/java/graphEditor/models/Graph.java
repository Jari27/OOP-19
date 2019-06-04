package graphEditor.models;

import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Graph extends Observable implements Observer {

    private final List<GraphVertex> vertices = new ArrayList<>();
    private final List<GraphEdge> edges = new ArrayList<>();

    private final UndoManager undoManager = new UndoManager();

    public Graph() {
        GraphVertex v1 = new GraphVertex();
        v1.setName("Test1");
        v1.setSize(new Dimension(100, 100));
        v1.setLocation(new Point(200, 200));

        GraphVertex v2 = new GraphVertex();
        v2.setSize(new Dimension(100, 100));
        v2.setName("Test2");

        vertices.add(v1);
        v1.addObserver(this);
        vertices.add(v2);
        v2.addObserver(this);

        addEdge(v1, v2);
    }

    public Graph(File file) {
        load(file);
    }

    public void load(File file) {
        // read data
        String result;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            result = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // interpret data
        String[] resultSplit = result.split("\n");
        String[] tmp = resultSplit[0].split(" ");
        int numVertex = Integer.parseInt(tmp[0]);
        int numEdge = Integer.parseInt(tmp[1]);

        // quick and dirty file verification
        assert (resultSplit.length != numVertex + numEdge + 1);

        ArrayList<GraphVertex> tmpVertices = new ArrayList<>();
        // load vertices
        for (int i = 0; i < numVertex; i++) {
            GraphVertex vertex = new GraphVertex();
            tmp = resultSplit[1 + i].split(" ");
            // set location
            int x = Integer.parseInt(tmp[0]);
            int y = Integer.parseInt(tmp[1]);
            Point location = new Point(x, y);
            vertex.setLocation(location);
            // set size
            int width = Integer.parseInt(tmp[2]);
            int height = Integer.parseInt(tmp[3]);
            Dimension size = new Dimension(width, height);
            vertex.setSize(size);
            // set name
            vertex.setName(tmp[4]);
            tmpVertices.add(vertex);
        }

        ArrayList<GraphEdge> tmpEdges = new ArrayList<>();
        // load edges
        for (int i = 0; i < numEdge; i++) {
            tmp = resultSplit[1 + numVertex + i].split(" ");
            // get associated vertices
            GraphVertex v1 = tmpVertices.get(Integer.parseInt(tmp[0]));
            GraphVertex v2 = tmpVertices.get(Integer.parseInt(tmp[1]));
            GraphEdge edge = new GraphEdge(v1, v2);
            tmpEdges.add(edge);
        }

        // remove old vertices
        // cast to new list to prevent concurrent modification
        for (GraphVertex v : new ArrayList<>(vertices)) {
            this.removeVertex(v);
        }

        // add new vertices
        vertices.addAll(tmpVertices);
        edges.addAll(tmpEdges);

        // ensure dragging works
        for (GraphVertex v : vertices) {
            v.addObserver(this);
        }

        // remove stored undos and redos
        undoManager.discardAllEdits();

        // done
        setChanged();
        notifyObservers();
    }

    public void save(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(getSaveData());
            writer.flush();
        } catch (IOException e) {
            // TODO: nice error handling
            e.printStackTrace();
        }
    }

    private String getSaveData() {
        StringBuilder builder = new StringBuilder();
        builder.append(vertices.size())
                .append(" ")
                .append(edges.size())
                .append("\n");

        for (GraphVertex vertex : vertices) {
            Point location = vertex.getLocation();
            Dimension size = vertex.getSize();
            builder.append(location.x).append(" ")
                    .append(location.y).append(" ")
                    .append(size.width).append(" ")
                    .append(size.height).append(" ")
                    .append(vertex.getName()).append("\n");
        }

        for (GraphEdge edge : edges) {
            int index1 = vertices.indexOf(edge.getV1());
            int index2 = vertices.indexOf(edge.getV2());
            builder.append(index1).append(" ").append(index2).append("\n");
        }
        return builder.toString();
    }

    public GraphEdge addEdge(GraphVertex v1, GraphVertex v2) {
        GraphEdge edge = new GraphEdge(v1, v2);
        return addEdge(edge);
    }

    public GraphEdge addEdge(GraphEdge edge) {
        edges.add(edge);
        setChanged();
        notifyObservers();
        return edge;
    }

    public boolean hasEdgeBetween(GraphEdge edge) {
        if (edges.contains(edge)) return true;
        for (GraphEdge e : edges) {
            if (e.getV1() == edge.getV1() && e.getV2() == edge.getV2()
                    || e.getV1() == edge.getV2() && e.getV2() == edge.getV1()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEdgeBetween(GraphVertex v1, GraphVertex v2) {
        GraphEdge edge = new GraphEdge(v1, v2);
        return hasEdgeBetween(edge);
    }

    private GraphEdge getSimilarEdge(GraphEdge edge) {
        if (edges.contains(edge)) {
            return edge;
        }
        for (GraphEdge e : edges) {
            if (e.getV1() == edge.getV1() && e.getV2() == edge.getV2()
                    || e.getV1() == edge.getV2() && e.getV2() == edge.getV1()) {
                return e;
            }
        }
        return null;
    }

    public GraphEdge removeEdge(GraphEdge edge) {
        edges.remove(edge);
        setChanged();
        notifyObservers();
        return edge;
    }

    public GraphVertex addVertex() {
        GraphVertex vertex = new GraphVertex();
        return addVertex(vertex);
    }

    public GraphVertex addVertex(GraphVertex vertex) {
        vertices.add(vertex);
        selectOnly(vertex);
        vertex.addObserver(this);
        setChanged();
        notifyObservers();
        return vertex;
    }

    public GraphVertex removeVertex(GraphVertex vertex) {
        boolean didRemove = vertices.remove(vertex);
        vertex.deleteObserver(this);
        // O(n^2)
        // can make quicker using LinkedList and ListIterator
        List<GraphEdge> edgesToRemove = new ArrayList<>();
        for (GraphEdge edge : edges) {
            if (edge.connectsTo(vertex)) {
                edgesToRemove.add(edge);
            }
        }
        edges.removeAll(edgesToRemove);
        setChanged();
        notifyObservers();
        return didRemove ? vertex : null;
    }

    public GraphVertex removeVertex(int index) {
        return removeVertex(vertices.get(index));
    }

    public List<GraphVertex> getVertices() {
        return vertices;
    }

    public List<GraphEdge> getEdges() {
        return edges;
    }

    public void select(GraphVertex... vertices) {
        for (GraphVertex vertex : vertices) {
            vertex.setSelected(true);
        }
        setChanged();
        notifyObservers();
    }

    public void selectOnly(GraphVertex... vertices) {
        unselectAll();
        for (GraphVertex vertex : vertices) {
            vertex.setSelected(true);
        }
        setChanged();
        notifyObservers();
    }

    public void selectAll() {
        for (GraphVertex vertex : vertices) {
            vertex.setSelected(true);
        }
        setChanged();
        notifyObservers();
    }

    public void unSelect(GraphVertex... vertices) {
        for (GraphVertex vertex : vertices) {
            vertex.setSelected(false);
        }
        setChanged();
        notifyObservers();
    }

    public void unselectAll() {
        for (GraphVertex vertex : vertices) {
            vertex.setSelected(false);
        }
        setChanged();
        notifyObservers();
    }

    public boolean isSelected(GraphVertex vertex) {
        return vertex.isSelected();
    }

    public boolean isAnyVertexSelected() {
        for (GraphVertex vertex : vertices) {
            if (vertex.isSelected()) {
                return true;
            }
        }
        return false;
    }

    public boolean isSelected(GraphEdge edge) {
        return edge.getV1().isSelected() || edge.getV2().isSelected();
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }

    public void addEdit(UndoableEdit edit) {
        undoManager.addEdit(edit);
        setChanged();
        notifyObservers();
    }

    public void undo() {
        undoManager.undo();
    }

    public void redo() {
        undoManager.redo();
    }

    public boolean canUndo() {
        return undoManager.canUndo();
    }

    public boolean canRedo() {
        return undoManager.canRedo();
    }
}
