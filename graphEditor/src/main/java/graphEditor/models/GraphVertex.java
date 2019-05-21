package graphEditor.models;

import java.awt.*;

public class GraphVertex {

    private static Point defaultLocation = new Point(0, 0);
    private static Dimension defaultSize = new Dimension(80, 30);
    private static int num = 0;

    private Dimension size;
    private Point location;
    private String name;

    public GraphVertex() {
        this.size = new Dimension(defaultSize);
        this.location = new Point(defaultLocation);
        this.name = "vertex_" + num++;
    }

    public GraphVertex(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public GraphVertex setName(String name) {
        this.name = name;
        return this;
    }

    public Dimension getSize() {
        return size;
    }

    public GraphVertex setSize(Dimension size) {
        this.size = size;
        return this;
    }

    public Point getLocation() {
        return location;
    }

    public GraphVertex setLocation(Point location) {
        this.location = location;
        return this;
    }

    public static Point getDefaultLocation() {
        return defaultLocation;
    }

    public static void setDefaultLocation(Point defaultLocation) {
        GraphVertex.defaultLocation = defaultLocation;
    }

    public static Dimension getDefaultSize() {
        return defaultSize;
    }

    public static void setDefaultSize(Dimension defaultSize) {
        GraphVertex.defaultSize = defaultSize;
    }
}
