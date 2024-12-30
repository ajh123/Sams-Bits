package me.ajh123.sams_bits.roads;

import java.util.Objects;

import de.topobyte.osm4j.core.model.impl.Node;
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
        long lat = -position.getZ();
        long lon = position.getX();
        return new Node(id, lon, lat);
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDelated(boolean deleted) {
        this.deleted = deleted;
    }
}
