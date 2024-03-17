package agh.ics.oop.model.maps.plants;

import agh.ics.oop.model.entities.FertileField;
import agh.ics.oop.model.utilities.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class ForestedEquatorTest {

    @Test
    public void testGenerateFertilePositions() {
        Vector2d upperRight = new Vector2d(10, 10);
        ForestedEquator forestedEquator = new ForestedEquator(upperRight);
        HashMap<Vector2d, FertileField> fertileFields = forestedEquator.generateFertilePositions();

        Assertions.assertEquals(22, fertileFields.size());

        for (int x = 0; x <= upperRight.get_x(); x++) {
            for (int y = 5; y <= 6; y++) {
                Vector2d position = new Vector2d(x, y);
                FertileField fertileField = fertileFields.get(position);

                Assertions.assertNotNull(fertileField);
            }
        }
    }
}
