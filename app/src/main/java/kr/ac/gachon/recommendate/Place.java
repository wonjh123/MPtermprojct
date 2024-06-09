package kr.ac.gachon.recommendate;

import com.google.firebase.firestore.GeoPoint;
import java.util.List;

public class Place {
    private String name;
    private List<String> tags;
    private GeoPoint location;

    public Place() {}

    public Place(String name, List<String> tags, GeoPoint location) {
        this.name = name;
        this.tags = tags;
        this.location = location;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public GeoPoint getLocation() { return location; }
    public void setLocation(GeoPoint location) { this.location = location; }
}
