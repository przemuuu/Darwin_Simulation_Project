package agh.ics.oop.model.entities;

import agh.ics.oop.model.utilities.Vector2d;

public class FertileField implements WorldElement {
    private final Vector2d position;
    private int lifeLength;
    public FertileField(Vector2d position, int lifeLength) {
        this.position = position;
        this.lifeLength = lifeLength;
    }
    @Override
    public Vector2d getPosition() {
        return this.position;
    }
    @Override
    public String toString() {
        return "fertile";
    }
    public int getLifeLength() {
        return this.lifeLength;
    }
    public void nextDay(){
        this.lifeLength--;
    }
}
