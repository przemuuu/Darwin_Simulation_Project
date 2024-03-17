package agh.ics.oop.model.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2dTest {

    @Test
    public void testAdd() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(4, 5);
        Vector2d result = v1.add(v2);

        Assertions.assertEquals(6, result.get_x());
        Assertions.assertEquals(8, result.get_y());
    }

    @Test
    public void testEquals() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(2, 3);

        Assertions.assertEquals(v1, v2);
    }

    @Test
    public void testHashCode() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(2, 3);

        Assertions.assertEquals(v1.hashCode(), v2.hashCode());
    }
    @Test
    public void testGetX() {
        Vector2d v = new Vector2d(2, 3);

        Assertions.assertEquals(2, v.get_x());
    }

    @Test
    public void testGetY() {
        Vector2d v = new Vector2d(2, 3);

        Assertions.assertEquals(3, v.get_y());
    }

    @Test
    public void testToString() {
        Vector2d v = new Vector2d(2, 3);

        Assertions.assertEquals("(2,3)", v.toString());
    }
}
