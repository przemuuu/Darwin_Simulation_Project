package agh.ics.oop.model.maps.plants;

import agh.ics.oop.model.entities.FertileField;
import agh.ics.oop.model.utilities.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class LifegivingCorpsesTest {

    private Vector2d upperRight;
    private LifegivingCorpses lifegivingCorpses;

    @BeforeEach
    public void setUp() {
        upperRight = new Vector2d(10, 10);
        lifegivingCorpses = new LifegivingCorpses(upperRight, 2, 3);
    }

    @Test
    public void testAddFertileField() {
        Vector2d position = new Vector2d(5, 5);
        lifegivingCorpses.addFertileField(position);

        Assertions.assertEquals(1, lifegivingCorpses.fertileFieldsList.size());
        Assertions.assertEquals(position, lifegivingCorpses.fertileFieldsList.get(0).getPosition());
        Assertions.assertEquals(3, lifegivingCorpses.fertileFieldsList.get(0).getLifeLength());
    }

    @Test
    public void testFertileFieldNextDay() {
        Vector2d position1 = new Vector2d(5, 5);
        Vector2d position2 = new Vector2d(7, 7);
        lifegivingCorpses.addFertileField(position1);
        lifegivingCorpses.addFertileField(position2);

        lifegivingCorpses.fertileFieldNextDay();

        Assertions.assertEquals(2, lifegivingCorpses.fertileFieldsList.get(0).getLifeLength());
        Assertions.assertEquals(2, lifegivingCorpses.fertileFieldsList.get(1).getLifeLength());

        Assertions.assertEquals(2, lifegivingCorpses.fertileFieldsList.size());

        lifegivingCorpses.fertileFieldNextDay();

        Assertions.assertEquals(2, lifegivingCorpses.fertileFieldsList.size());
        Assertions.assertEquals(position2, lifegivingCorpses.fertileFieldsList.get(1).getPosition());
    }

    @Test
    public void testGenerateFertilePositions() {
        Vector2d position1 = new Vector2d(5, 5);
        Vector2d position2 = new Vector2d(7, 7);
        lifegivingCorpses.addFertileField(position1);
        lifegivingCorpses.addFertileField(position2);

        Map<Vector2d, FertileField> fertileFields = lifegivingCorpses.generateFertilePositions();

        Assertions.assertEquals(41, fertileFields.size());

        Assertions.assertTrue(fertileFields.containsKey(new Vector2d(4, 4)));
        Assertions.assertEquals(3, fertileFields.get(new Vector2d(4, 4)).getLifeLength());

        Assertions.assertTrue(fertileFields.containsKey(new Vector2d(6, 6)));
        Assertions.assertEquals(3, fertileFields.get(new Vector2d(6, 6)).getLifeLength());
    }
}
