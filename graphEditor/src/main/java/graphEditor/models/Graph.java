package graphEditor.models;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Graph extends Observable {

    List<GraphVertex> vertices;
    List<GraphEdge> edges;

    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public Graph(File file) {
        load(file);
    }

    public void load(File file) {
        // read data
        String result;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
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

        // load vertices
        this.vertices = new ArrayList<>(numVertex);
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
            vertices.add(vertex);
        }

        // load edges
        this.edges = new ArrayList<>(numEdge);
        for (int i = 0; i < numEdge; i++) {
            tmp = resultSplit[1 + numVertex + i].split(" ");
            // get associated vertices
            GraphVertex v1 = vertices.get(Integer.parseInt(tmp[0]));
            GraphVertex v2 = vertices.get(Integer.parseInt(tmp[1]));
            GraphEdge edge = new GraphEdge(v1, v2);
            edges.add(edge);
        }

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

    public String getSaveData() {
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
        edges.add(edge);
        setChanged();
        notifyObservers();
        return edge;
    }

    public GraphEdge removeEdge(GraphEdge edge) {
        edges.remove(edge);
        setChanged();
        notifyObservers();
        return edge;
    }

    public GraphVertex addVertex() {
        GraphVertex vertex = new GraphVertex();
        vertices.add(vertex);
        setChanged();
        notifyObservers();
        return vertex;
    }

    public GraphVertex addVertex(String name) {
        GraphVertex vertex = new GraphVertex(name);
        vertices.add(vertex);
        setChanged();
        notifyObservers();
        return vertex;
    }

    public GraphVertex removeVertex(GraphVertex vertex) {
        boolean didRemove = vertices.remove(vertex);
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
}
