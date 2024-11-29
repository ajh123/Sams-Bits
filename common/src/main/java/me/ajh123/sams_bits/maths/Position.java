package me.ajh123.sams_bits.maths;

public class Position {
    private int x;
    private int y;
    private int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
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
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        if (z != other.z)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + ", z=" + z + "]";
    }
}
