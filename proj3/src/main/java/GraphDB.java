import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Wraps the parsing functionality of the MapDBHandler as an example.
 * You may choose to add to the functionality of this class if you wish.
 *
 * @author Alan Yao
 */
public class GraphDB {
    public static HashMap<String, GraphNode> graphNodes = new HashMap<>();

    /**
     * Example constructor shows how to create and start an XML parser.
     *
     * @param db_path Path to the XML file to be parsed.
     */
    public GraphDB(String db_path) {
        try {
            File inputFile = new File(db_path);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            MapDBHandler maphandler = new MapDBHandler(this);
            saxParser.parse(inputFile, maphandler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        List<String> toClean = new ArrayList<>();
        for (Map.Entry<String, GraphNode> entry : graphNodes.entrySet()) {
            if (entry.getValue().getConnections().size() == 0) {
                toClean.add(entry.getKey());
            }
        }
        for (String key : toClean) {
            graphNodes.remove(key);
        }
    }

    public static String getClosestNode(double lon, double lat) {
        double minDist = 1;
        String closestNode = null;
        for (Map.Entry<String, GraphNode> entry : graphNodes.entrySet()) {
            double dist = Math.sqrt(Math.pow(lon - entry.getValue().getLon(), 2.0) + Math.pow(lat - entry.getValue().getLat(), 2.0));
            if (dist < minDist) {
                closestNode = entry.getKey();
                minDist = dist;
            }
        }
        return closestNode;
    }

    public static List<String> findRoute(String originNode, String destNode) {
        PriorityQueue<GraphNode> queue = new PriorityQueue<>(11, new NodeCompare());
        queue.add(graphNodes.get(originNode));
        boolean solved = false;
        GraphNode curr;
        List<String> toReturn = new ArrayList<>();
        HashMap<String, GraphNode> seen = new HashMap<>();
        seen.put(originNode, graphNodes.get(originNode));
        while (!solved && !queue.isEmpty()) {
            curr = queue.poll();
            seen.put(curr.getID(), curr);
            if (curr.getID().equals(destNode)) {
                solved = true;
                toReturn.add(curr.getID());
                while (curr.routeParent != null) {
                    toReturn.add(curr.routeParent.getID());
                    curr = curr.routeParent;
                }
                Collections.reverse(toReturn);
            } else {
                for (GraphNode other : curr.getConnections()) {
                    double newroutedistance = curr.routeDistance + Math.sqrt(Math.pow(curr.getLon()
                            - other.getLon(), 2.0) + Math.pow(curr.getLat() - other.getLat(), 2.0));
                    if (curr.routeParent == null || !seen.containsKey(other.getID())) {

                        other.routeParent = curr;
                        other.routeDistance = newroutedistance;
                        queue.add(other);
                    }
                }
            }
        }
        return toReturn;
    }
}
