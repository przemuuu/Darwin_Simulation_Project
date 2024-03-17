package agh.ics.oop.model.entities;

import agh.ics.oop.model.utilities.Vector2d;

public class Grass implements WorldElement {
    private Vector2d position;
    public Grass(Vector2d position) {
        this.position = position;
    }
    @Override
    public Vector2d getPosition() {
        return position;
    }
    @Override
    public String toString() {
        return "*";
    }
}
