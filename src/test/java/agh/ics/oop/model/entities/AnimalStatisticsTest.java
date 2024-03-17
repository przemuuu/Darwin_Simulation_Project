package agh.ics.oop.model.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalStatisticsTest {

    @Test
    public void testAnimalDied() {
        AnimalStatistics animalStatistics = new AnimalStatistics(10);
        animalStatistics.animalDied(20);

        Assertions.assertEquals(20, animalStatistics.getDeathDateInt());
    }

    @Test
    public void testEatPlant() {
        AnimalStatistics animalStatistics = new AnimalStatistics(10);
        animalStatistics.eatPlant();

        Assertions.assertEquals(1, animalStatistics.getEatenPlantsNumber());
    }

    @Test
    public void testNewChild() {
        AnimalStatistics firstParent = new AnimalStatistics(5);
        AnimalStatistics secondParent = new AnimalStatistics(7);
        AnimalStatistics animalStatistics = new AnimalStatistics(firstParent, secondParent, 10);

        Assertions.assertEquals(1, firstParent.getChildrenNumber());
        Assertions.assertEquals(1, secondParent.getChildrenNumber());
        Assertions.assertEquals(1, firstParent.getDescendantsNumber());
        Assertions.assertEquals(1, secondParent.getDescendantsNumber());
    }

    @Test
    public void testGetDeathDate() {
        AnimalStatistics animalStatistics = new AnimalStatistics(10);
        animalStatistics.animalDied(20);

        Assertions.assertEquals("20", animalStatistics.getDeathDate());
    }

    @Test
    public void testGetDeathDateWhenAlive() {
        AnimalStatistics animalStatistics = new AnimalStatistics(10);

        Assertions.assertEquals("Animal is still alive", animalStatistics.getDeathDate());
    }

    @Test
    public void testGetEatenPlantsNumber() {
        AnimalStatistics animalStatistics = new AnimalStatistics(10);
        animalStatistics.eatPlant();
        animalStatistics.eatPlant();

        Assertions.assertEquals(2, animalStatistics.getEatenPlantsNumber());
    }
}