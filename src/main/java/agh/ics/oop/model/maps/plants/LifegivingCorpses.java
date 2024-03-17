package agh.ics.oop.model.maps.plants;

import agh.ics.oop.model.entities.FertileField;
import agh.ics.oop.model.utilities.Vector2d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LifegivingCorpses implements PlantsType{
    private final Vector2d upperRight;
    private final int FERTILE_RADIUS;
    private final int FERTILIZATION_TIME;
    protected List<FertileField> fertileFieldsList = new ArrayList<>();
    public LifegivingCorpses(Vector2d upperRight, int radius, int fertilizationTime) {
        this.upperRight = upperRight;
        this.FERTILE_RADIUS = radius;
        this.FERTILIZATION_TIME = fertilizationTime;
    }
    @Override
    public void addFertileField(Vector2d position) {
        fertileFieldsList.add(new FertileField(position, this.FERTILIZATION_TIME));
    }
    @Override
    public void fertileFieldNextDay() {
        fertileFieldsList.forEach(FertileField::nextDay);
        fertileFieldsList.removeIf(field -> field.getLifeLength() <= 0);
    }
    @Override
    public HashMap<Vector2d, FertileField> generateFertilePositions() {
        HashMap<Vector2d, FertileField> fertileFields = new HashMap<>();
        for(FertileField field : fertileFieldsList) {
            Vector2d newMiddlePosition = field.getPosition();
            int newX = newMiddlePosition.get_x();
            int newY = newMiddlePosition.get_y();
            int boundYHigh = newY + FERTILE_RADIUS;
            int boundYLow = newY - FERTILE_RADIUS;
            if(boundYLow<0) boundYLow = 0;
            if(boundYHigh > upperRight.get_y()) boundYHigh = upperRight.get_y();
            int boundXHigh = newX + FERTILE_RADIUS;
            int boundXLow = newX - FERTILE_RADIUS;
            for(int x = boundXLow; x <= boundXHigh; x++){
                for(int y = boundYLow; y <= boundYHigh; y++){
                    int xRotated = x;
                    if(x < 0) xRotated = upperRight.get_x() + x + 1;
                    if(x > upperRight.get_x()) xRotated = x - upperRight.get_x() - 1;
                    fertileFields.put(new Vector2d(xRotated,y),new FertileField(new Vector2d(xRotated,y), FERTILIZATION_TIME));
                }
            }
        }
        return fertileFields;
    }
}
