package agh.ics.oop.model.entities;

import agh.ics.oop.model.utilities.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FertileFieldTest {

    @Test
    public void testGetPosition() {
        Vector2d position = new Vector2d(3, 4);
        FertileField fertileField = new FertileField(position, 10);

        Assertions.assertEquals(position, fertileField.getPosition());
    }

    @Test
    public void testToString() {
        FertileField fertileField = new FertileField(new Vector2d(3, 4), 10);

        Assertions.assertEquals("fertile", fertileField.toString());
    }

    @Test
    public void testGetLifeLength() {
        FertileField fertileField = new FertileField(new Vector2d(3, 4), 10);

        Assertions.assertEquals(10, fertileField.getLifeLength());
    }

    @Test
    public void testNextDay() {
        FertileField fertileField = new FertileField(new Vector2d(3, 4), 10);
        fertileField.nextDay();

        Assertions.assertEquals(9, fertileField.getLifeLength());
    }
}