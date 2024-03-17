package agh.ics.oop.model.maps.plants;

import agh.ics.oop.model.entities.FertileField;
import agh.ics.oop.model.utilities.Vector2d;
import java.util.HashMap;

public class ForestedEquator implements PlantsType {
    private final Vector2d upperRight;
    public ForestedEquator(Vector2d upperRight) {
        this.upperRight = upperRight;
    }
    @Override
    public void addFertileField(Vector2d position) {}
    @Override
    public void fertileFieldNextDay() {}
    @Override
    public HashMap<Vector2d, FertileField> generateFertilePositions() {
        HashMap<Vector2d, FertileField> fertileFields = new HashMap<>();
        int equatorHeight = (int) Math.round(0.2*(upperRight.get_y()+1));
        if(equatorHeight == 0 && upperRight.get_y()>2){
            equatorHeight = 1;
        }
        for (int x = 0; x <= upperRight.get_x(); x++) {
            for(int y = (upperRight.get_y() - equatorHeight) / 2 + 1; y <= (upperRight.get_y() + equatorHeight) / 2; y++){
                fertileFields.put(new Vector2d(x,y),new FertileField(new Vector2d(x,y), 2));
            }
        }
        return fertileFields;
    }
}
