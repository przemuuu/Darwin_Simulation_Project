package agh.ics.oop.model.maps.plants;

import agh.ics.oop.model.entities.FertileField;
import agh.ics.oop.model.entities.Grass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import agh.ics.oop.model.utilities.Vector2d;

import java.util.HashMap;
import java.util.Map;


public class GrassGeneratorTest {

    private Vector2d upperRight;
    private Map<Vector2d, Grass> grasses;
    private Map<Vector2d, FertileField> fertileFields;
    private GrassGenerator grassGenerator;

    @BeforeEach
    public void setUp() {
        upperRight = new Vector2d(10, 10);
        grasses = new HashMap<>();
        fertileFields = new HashMap<>();
        grassGenerator = new GrassGenerator(upperRight, grasses, fertileFields);
    }

    @Test
    public void testGrowGrass() {
        int grassNumber = 5;
        Map<Vector2d, Grass> newGrasses = grassGenerator.growGrass(grassNumber);

        Assertions.assertEquals(grassNumber, newGrasses.size());
    }
}
