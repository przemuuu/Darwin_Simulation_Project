package agh.ics.oop.model.entities;

import agh.ics.oop.model.movement.MapDirection;
import agh.ics.oop.model.utilities.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class AnimalTest {

    @Test
    public void testMove() {
        MapDirection orientation = MapDirection.NORTH;
        Vector2d position = new Vector2d(2, 2);
        int initialEnergy = 10;
        List<Integer> genotype = Arrays.asList(1, 2, 3, 4);
        int activeGeneIndex = 0;
        AnimalStatistics statistics = new AnimalStatistics(0);
        int moveEnergy = 1;

        Animal animal = new Animal(orientation, position, initialEnergy, genotype, activeGeneIndex, statistics, moveEnergy);

        animal.move();

        Assertions.assertEquals(new Vector2d(1, 3), animal.getPosition());
        Assertions.assertEquals(9, animal.getEnergy());
        Assertions.assertEquals(1, animal.getActiveGeneIndex());
    }

    @Test
    public void testEatPlant() {
        MapDirection orientation = MapDirection.NORTH;
        Vector2d position = new Vector2d(0, 0);
        int initialEnergy = 10;
        List<Integer> genotype = Arrays.asList(1, 2, 3, 4);
        int activeGeneIndex = 0;
        AnimalStatistics statistics = new AnimalStatistics(0);
        int moveEnergy = 1;

        Animal animal = new Animal(orientation, position, initialEnergy, genotype, activeGeneIndex, statistics, moveEnergy);

        animal.eatPlant(5);

        Assertions.assertEquals(15, animal.getEnergy());
        Assertions.assertEquals(1, statistics.getEatenPlantsNumber());
    }

    @Test
    public void testMutation() {
        MapDirection orientation = MapDirection.NORTH;
        Vector2d position = new Vector2d(0, 0);
        int initialEnergy = 10;
        List<Integer> genotype = Arrays.asList(1, 2, 3, 4);
        int activeGeneIndex = 0;
        AnimalStatistics statistics = new AnimalStatistics(0);
        int moveEnergy = 1;

        Animal animal = new Animal(orientation, position, initialEnergy, genotype, activeGeneIndex, statistics, moveEnergy);

        animal.mutation(0, 1, 2);

        Assertions.assertTrue(animal.getGenotype().contains(0) || animal.getGenotype().contains(1));
    }
    @Test
    public void testMoveBack() {
        MapDirection orientation = MapDirection.NORTH;
        Vector2d position = new Vector2d(0, 0);
        int initialEnergy = 10;
        List<Integer> genotype = Arrays.asList(1, 2, 3, 4);
        int activeGeneIndex = 0;
        AnimalStatistics statistics = new AnimalStatistics(0);
        int moveEnergy = 1;

        Animal animal = new Animal(orientation, position, initialEnergy, genotype, activeGeneIndex, statistics, moveEnergy);

        animal.moveBack();

        Assertions.assertEquals(new Vector2d(0, -1), animal.getPosition());
    }

    @Test
    public void testMoveAround() {
        MapDirection orientation = MapDirection.NORTH;
        Vector2d position = new Vector2d(-1, 0);
        int initialEnergy = 10;
        List<Integer> genotype = Arrays.asList(1, 2, 3, 4);
        int activeGeneIndex = 0;
        AnimalStatistics statistics = new AnimalStatistics(0);
        int moveEnergy = 1;

        Animal animal = new Animal(orientation, position, initialEnergy, genotype, activeGeneIndex, statistics, moveEnergy);

        animal.moveAround(5);

        Assertions.assertEquals(new Vector2d(5, 0), animal.getPosition());
    }

    @Test
    public void testEnergyMultiplicate() {
        MapDirection orientation = MapDirection.NORTH;
        Vector2d position = new Vector2d(0, 0);
        int initialEnergy = 10;
        List<Integer> genotype = Arrays.asList(1, 2, 3, 4);
        int activeGeneIndex = 0;
        AnimalStatistics statistics = new AnimalStatistics(0);
        int moveEnergy = 1;

        Animal animal = new Animal(orientation, position, initialEnergy, genotype, activeGeneIndex, statistics, moveEnergy);

        animal.energyMultiplicate(3);

        Assertions.assertEquals(7, animal.getEnergy());
    }

    @Test
    public void testIsAlive() {
        MapDirection orientation = MapDirection.NORTH;
        Vector2d position = new Vector2d(0, 0);
        int initialEnergy = 10;
        List<Integer> genotype = Arrays.asList(1, 2, 3, 4);
        int activeGeneIndex = 0;
        AnimalStatistics statistics = new AnimalStatistics(0);
        int moveEnergy = 1;

        Animal animal = new Animal(orientation, position, initialEnergy, genotype, activeGeneIndex, statistics, moveEnergy);

        Assertions.assertTrue(animal.isAlive());

        animal.energyMultiplicate(10);

        Assertions.assertFalse(animal.isAlive());
    }
    @Test
    public void testGetGenotype() {
        MapDirection orientation = MapDirection.NORTH;
        Vector2d position = new Vector2d(0, 0);
        int initialEnergy = 10;
        List<Integer> genotype = Arrays.asList(1, 2, 3, 4);
        int activeGeneIndex = 0;
        AnimalStatistics statistics = new AnimalStatistics(0);
        int moveEnergy = 1;

        Animal animal = new Animal(orientation, position, initialEnergy, genotype, activeGeneIndex, statistics, moveEnergy);

        List<Integer> expected = Arrays.asList(1, 2, 3, 4);
        List<Integer> actual = animal.getGenotype();

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testGetPosition() {
        MapDirection orientation = MapDirection.NORTH;
        Vector2d position = new Vector2d(0, 0);
        int initialEnergy = 10;
        List<Integer> genotype = Arrays.asList(1, 2, 3, 4);
        int activeGeneIndex = 0;
        AnimalStatistics statistics = new AnimalStatistics(0);
        int moveEnergy = 1;

        Animal animal = new Animal(orientation, position, initialEnergy, genotype, activeGeneIndex, statistics, moveEnergy);

        Vector2d expected = new Vector2d(0, 0);
        Vector2d actual = animal.getPosition();

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testToString() {
        MapDirection orientation = MapDirection.NORTH;
        Vector2d position = new Vector2d(0, 0);
        int initialEnergy = 10;
        List<Integer> genotype = Arrays.asList(1, 2, 3, 4);
        int activeGeneIndex = 0;
        AnimalStatistics statistics = new AnimalStatistics(0);
        int moveEnergy = 1;

        Animal animal = new Animal(orientation, position, initialEnergy, genotype, activeGeneIndex, statistics, moveEnergy);

        String expected = "N";
        String actual = animal.toString();

        Assertions.assertEquals(expected, actual);
    }
}