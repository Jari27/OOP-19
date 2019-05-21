package graphEditor.models;

public class GraphEdge {

    private GraphVertex v1;
    private GraphVertex v2;

    public GraphEdge(GraphVertex v1, GraphVertex v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public boolean connectsTo(GraphVertex vertex) {
        return v1 == vertex || v2 == vertex;
    }

    public GraphVertex[] getConnection() {
        return new GraphVertex[]{v1, v2};
    }

    public GraphVertex[] setConnection(GraphVertex v1, GraphVertex v2) {
        this.v1 = v1;
        this.v2 = v2;
        return new GraphVertex[]{v1, v2};
    }

    public GraphVertex getV1() {
        return v1;
    }

    public GraphEdge setV1(GraphVertex v1) {
        this.v1 = v1;
        return this;
    }

    public GraphVertex getV2() {
        return v2;
    }

    public GraphEdge setV2(GraphVertex v2) {
        this.v2 = v2;
        return this;
    }
}
