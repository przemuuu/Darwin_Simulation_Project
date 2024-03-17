package agh.ics.oop.model.maps.plants;

import agh.ics.oop.model.entities.FertileField;
import agh.ics.oop.model.entities.Grass;
import agh.ics.oop.model.utilities.RandomGrassPositionGenerator;
import agh.ics.oop.model.utilities.Vector2d;

import java.util.HashMap;
import java.util.Map;

public class GrassGenerator {
    private final Vector2d upperRight;
    protected Map<Vector2d, Grass> grasses;
    protected Map<Vector2d, FertileField> fertileFields;
    public GrassGenerator(Vector2d upperRight, Map<Vector2d, Grass> grasses, Map<Vector2d,FertileField> fertileFields) {
        this.upperRight = upperRight;
        this.grasses = grasses;
        this.fertileFields = fertileFields;
    }
    public Map<Vector2d, Grass> growGrass(int grassNumber) {
        Map<Vector2d, Grass> newGrasses = new HashMap<>();
        RandomGrassPositionGenerator randomPositionGenerator = new RandomGrassPositionGenerator(upperRight.get_x(), upperRight.get_y(), grassNumber, fertileFields, grasses);
        for (Vector2d grassPosition : randomPositionGenerator) {
            Grass grass = new Grass(grassPosition);
            newGrasses.put(grassPosition, grass);
        }
        newGrasses.putAll(grasses);
        return newGrasses;
    }
}
