package me.ajh123.sams_bits.roads;

import java.util.Objects;

import de.topobyte.osm4j.core.model.impl.Metadata;
import de.topobyte.osm4j.core.model.impl.Node;
import me.ajh123.sams_bits.maths.MercatorProjection;
import me.ajh123.sams_bits.maths.Position;

public class RoadNode {
    public static long nextId = 0;
    private long id;
    private Position position;
    private boolean deleted = false;

    public RoadNode() {
        // Empty constructor for Jackson
    }

    public RoadNode(Position position, long id) {
        this.position = position;
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RoadNode other = (RoadNode) obj;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RoadNode [position=" + position + " id= " + id + "]";
    }

    public Node toOSMNode() {
        // Convert Minecraft coordinates to Mercator latitude and longitude
        double lon = MercatorProjection.minecraftToLongitude(position.getX());
        double lat = MercatorProjection.minecraftToLatitude(position.getZ());

        Node node = new Node(id, lon, lat);
        node.setMetadata(new Metadata(
            1,
            0L,
            node.getId(),
            "null",
            0L
        ));

        // Return the OSM node with calculated latitude and longitude
        return node;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDelated(boolean deleted) {
        this.deleted = deleted;
    }
}
