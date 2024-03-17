package agh.ics.oop.model.maps.plants;

import agh.ics.oop.model.entities.FertileField;
import agh.ics.oop.model.utilities.Vector2d;

import java.util.HashMap;

public interface PlantsType {
    HashMap<Vector2d, FertileField> generateFertilePositions();
    void addFertileField(Vector2d position);
    void fertileFieldNextDay();
}
