package pojo;

import java.util.ArrayList;
import java.lang.reflect.Array;
import java.util.List;

public class Vertex {

    private long id;
    private double lat;
    private double lon;
    private String name;
    private List<Long> adjIdList;


    public Vertex(long id, double lon, double lat) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.adjIdList = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public List<Long> getadjIdList() {
        return adjIdList;
    }

    public void addAdjIdList(long adjId) {
        this.adjIdList.add(adjId);
    }
}
