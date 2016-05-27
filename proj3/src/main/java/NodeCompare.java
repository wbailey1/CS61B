import java.util.Comparator;

public class NodeCompare implements Comparator<GraphNode> {
    public int compare(GraphNode a, GraphNode b) {
        GraphNode destNode = GraphDB.getGraphNodes().get(MapServer.getDestNode());
        double calca = Math.sqrt(Math.pow(a.getLon() - destNode.getLon(), 2.0)
                + Math.pow(a.getLat() - destNode.getLat(), 2.0)) + a.getRouteDistance();
        double calcb = Math.sqrt(Math.pow(b.getLon() - destNode.getLon(), 2.0)
                + Math.pow(b.getLat() - destNode.getLat(), 2.0)) + b.getRouteDistance();
        if (calca < calcb) {
            return -1;
        } else if (calca > calcb) {
            return 1;
        } else {
            return 0;
        }
    }
}
