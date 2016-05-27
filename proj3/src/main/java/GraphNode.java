import java.util.ArrayList;
import java.util.List;

public class GraphNode {
    private String id;
    private double lon;
    private double lat;
    private List<GraphNode> connections;
    private double routeDistance;
    private GraphNode routeParent;

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

    public double getRouteDistance() {
        return routeDistance;
    }

    public void setRouteDistance(double x) {
        this.routeDistance = x;
    }

    public GraphNode getRouteParent() {
        return routeParent;
    }

    public void setRouteParent(GraphNode x) {
        this.routeParent = x;
    }
}
