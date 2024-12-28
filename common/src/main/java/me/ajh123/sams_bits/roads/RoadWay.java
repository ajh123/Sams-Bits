package me.ajh123.sams_bits.roads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultWeightedEdge;

import de.topobyte.osm4j.core.model.impl.Tag;

public class RoadWay extends DefaultWeightedEdge {
    private static long nextId = 0;
    private final long id;
    private final Map<String, String> tags;

    public RoadWay() {
        this.id = nextId++;
        this.tags = new HashMap<>();
        this.tags.put("highway", "unclassified");
        this.tags.put("name", "Unnamed Road");
    }

    public void addTag(String key, String value) {
        tags.put(key, value);
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public long getId() {
        return id;
    }

    public List<Tag> OSMTags() {
        List<Tag> osmTags = new ArrayList<>();
        tags.forEach((key, value) -> osmTags.add(new Tag(key, value)));
        return osmTags;
    }
}
