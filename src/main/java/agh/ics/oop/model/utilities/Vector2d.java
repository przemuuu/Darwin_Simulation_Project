package agh.ics.oop.model.utilities;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;
    public Vector2d(int x,int y) {
        this.x=x;
        this.y=y;
    }
    public int get_x() {
        return this.x;
    }
    public int get_y() {
        return this.y;
    }
    public String toString() {
        return ("("+this.x+","+this.y+")");
    }
    public Vector2d add(Vector2d other) {
        Vector2d created = new Vector2d(this.x+other.x,this.y+other.y);
        return created;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
