package me.ajh123.sams_bits.roads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jgrapht.graph.DefaultWeightedEdge;

import de.topobyte.osm4j.core.model.impl.Tag;

public class RoadWay extends DefaultWeightedEdge {
    public static long nextId = 0;
    private long id;
    private Map<String, String> tags = new HashMap<>();
    public long source_id;
    public long target_id;

    public RoadWay() {
        // Empty constructor for Jackson
    }

    public RoadWay(long id) {
        this.id = id;
        this.tags.put("highway", "unclassified");
        this.tags.put("name", "Unnamed Road");
    }

    public void addTag(String key, String value) {
        tags.put(key, value);
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public List<Tag> OSMTags() {
        List<Tag> osmTags = new ArrayList<>();
        tags.forEach((key, value) -> osmTags.add(new Tag(key, value)));
        return osmTags;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tags, source_id, target_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RoadWay other = (RoadWay) obj;
        return id == other.id &&
               source_id == other.source_id &&
               target_id == other.target_id &&
               Objects.equals(tags, other.tags);
    }
}
