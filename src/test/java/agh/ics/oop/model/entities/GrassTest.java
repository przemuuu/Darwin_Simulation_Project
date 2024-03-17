package agh.ics.oop.model.entities;

import agh.ics.oop.model.utilities.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GrassTest {

    @Test
    public void testGetPosition() {
        Vector2d position = new Vector2d(3, 4);
        Grass grass = new Grass(position);

        Assertions.assertEquals(position, grass.getPosition());
    }

    @Test
    public void testToString() {
        Grass grass = new Grass(new Vector2d(3, 4));

        Assertions.assertEquals("*", grass.toString());
    }
}