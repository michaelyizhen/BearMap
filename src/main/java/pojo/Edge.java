package pojo;
import java.util.ArrayList;

public class Edge {
    private long id;
    private ArrayList<Vertex> vertexList;
    private boolean isHighWay;
    private long lastVertexId;
    private String locationName;

    public Edge(long id) {
        this.id = id;
        this.isHighWay = false;
        this.vertexList = new ArrayList<>();
    }

    public boolean addVertex(Vertex ver) {
        return vertexList.add(ver);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Vertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(ArrayList<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public boolean isHighWay() {
        return isHighWay;
    }

    public void setHighWay(boolean highWay) {
        isHighWay = highWay;
    }

    public long getLastVertexId() {
        return lastVertexId;
    }

    public void setLastVertexId(long lastVertexId) {
        this.lastVertexId = lastVertexId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
