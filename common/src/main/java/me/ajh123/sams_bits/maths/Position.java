package me.ajh123.sams_bits.maths;

import java.util.List;
import java.util.Objects;

public class Position {
    private Integer x;
    private Integer y;
    private Integer z;

    public Position(Integer x, Integer y, Integer z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(Position position) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public double distanceTo(Position other) {
        int dx = other.x - this.x;
        int dy = other.y - this.y;
        int dz = other.z - this.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public List<Integer> toList() {
        return List.of(x, y, z);
    }

    public void add(Position other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }

    public void sub(Position other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
    }

    public void mul(Position other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
    }

    public void div(Position other) {
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
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
        Position other = (Position) obj;
        if (!Objects.equals(x, other.x))
            return false;
        if (!Objects.equals(y, other.y))
            return false;
        if (!Objects.equals(z, other.z))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + ", z=" + z + "]";
    }
}
