package graphEditor.models;

import java.awt.*;

public class GraphVertex extends Selectable {

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

    public void setName(String name) {
        this.name = name;
        setChanged();
        notifyObservers();
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
        setChanged();
        notifyObservers();
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
        setChanged();
        notifyObservers();
    }

    public boolean isInside(Point p) {
        return p.x >= getLocation().x
                && p.x <= getLocation().x + getSize().width
                && p.y >= getLocation().y
                && p.y <= getLocation().y + getSize().height;
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
