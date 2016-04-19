import java.util.ArrayList;
import java.util.List;

public class GraphNode {
    private String id;
    public double lon;
    public double lat;
    private List<GraphNode> connections;
    public double routeDistance;
    public GraphNode routeParent;

    public GraphNode(String id, double lon, double lat) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.routeDistance = 0;
        this.connections = new ArrayList<>();
        this.routeParent = null;
    }

    public void addConnection(GraphNode n) {
        this.connections.add(n);
    }

    public List<GraphNode> getConnections() {
        return connections;
    }

    public String getID() {
        return id;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
