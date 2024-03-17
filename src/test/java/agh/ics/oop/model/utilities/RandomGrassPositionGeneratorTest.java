package agh.ics.oop.model.utilities;

import agh.ics.oop.model.entities.FertileField;
import agh.ics.oop.model.entities.Grass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomGrassPositionGeneratorTest {

    @Test
    public void testGenerateGrassPositions() {
        int maxWidth = 10;
        int maxHeight = 10;
        int grassCount = 5;
        Map<Vector2d, FertileField> fertileFields = new HashMap<>();
        Map<Vector2d, Grass> grassMap = new HashMap<>();

        RandomGrassPositionGenerator generator = new RandomGrassPositionGenerator(maxWidth, maxHeight, grassCount, fertileFields, grassMap);
        List<Vector2d> grassPositions = generator.generateGrassPositions();

        Assertions.assertEquals(grassCount, grassPositions.size());

        for (Vector2d position : grassPositions) {
            Assertions.assertTrue(position.get_x() >= 0 && position.get_x() <= maxWidth);
            Assertions.assertTrue(position.get_y() >= 0 && position.get_y() <= maxHeight);
        }

        for (int i = 0; i < grassPositions.size(); i++) {
            for (int j = i + 1; j < grassPositions.size(); j++) {
                Assertions.assertNotEquals(grassPositions.get(i), grassPositions.get(j));
            }
        }
    }
}
