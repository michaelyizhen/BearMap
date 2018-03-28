import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import pojo.Edge;
import pojo.Location;
import pojo.Vertex;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */

    HashMap<Long, Vertex> vertexHashMap = new HashMap<>();
    HashMap<Long, Edge> edgeHashMap = new HashMap<>();
    TrieST<Location> locationTrieST = new TrieST<>();

    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputFile, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        // TODO: Your code here.
        LinkedList<Long> removedID = new LinkedList<>();
        for (Vertex v : vertexHashMap.values()) {
            if (v.getadjIdList().size() == 0) {
                removedID.offer(v.getId());
            }
        }
        vertexHashMap.keySet().removeAll(removedID);
    }

    /** Returns an iterable of all vertex IDs in the graph. */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        return vertexHashMap.keySet();
    }

    /** Returns ids of all vertices adjacent to v. */
    Iterable<Long> adjacent(long v) {
        return vertexHashMap.get(v).getadjIdList();
    }

    /** Returns the Euclidean distance between vertices v and w, where Euclidean distance
     *  is defined as sqrt( (lonV - lonV)^2 + (latV - latV)^2 ). */
    double distance(long v, long w) {
        double width = lon(v) - lon(w);
        double height = lat(v) - lat(w);
        return Math.sqrt(width * width + height * height);
    }

    /** Return the Euclidean distance between vertices V and target lat and lon */
    double distance(long v, double lon, double lat) {
        double width = lon(v) - lon;
        double height = lat(v) - lat;
        return Math.sqrt(width * width + height * height);
    }

    /** Returns the vertex id closest to the given longitude and latitude. */
    long closest(double lon, double lat) {
        double minDistance = Double.MAX_VALUE;
        long id = 0;
        //Iterable<Long> iterable = vertices();
        for (long vertexId : vertices()) {
            double distance = distance(vertexId, lon, lat);
            if (minDistance > distance) {
                minDistance = distance;
                id = vertexId;
            }
        }
        return id;
    }

    /** Longitude of vertex v. */
    double lon(long v) {
        return vertexHashMap.get(v).getLon();
    }

    /** Latitude of vertex v. */
    double lat(long v) {
        return vertexHashMap.get(v).getLat();
    }

    void addVertex(Vertex ver) {
        vertexHashMap.put(ver.getId(), ver);
    }

    void addEdge(Edge edge) {
        Vertex last = null;
        for(Vertex ver : edge.getVertexList()) {
            if (last != null) {
                last.addAdjIdList(ver.getId());
                ver.addAdjIdList(last.getId());
            }
            last = ver;
        }
        edgeHashMap.put(edge.getId(), edge);
    }


    Vertex getVertex(long vid) {
        return vertexHashMap.get(vid);
    }

    Edge getEdge(long eid) {
        return edgeHashMap.get(eid);
    }

}
