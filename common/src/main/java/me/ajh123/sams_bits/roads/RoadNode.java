package me.ajh123.sams_bits.roads;

import me.ajh123.sams_bits.maths.Position;

public class RoadNode {
    private static long nextId = 0;
    private final long id;
    private Position position;

    public RoadNode(Position position) {
        this.position = position;
        this.id = nextId++;
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
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
        return true;
    }

    @Override
    public String toString() {
        return "RoadNode [position=" + position + "]";
    }
}
