package agh.ics.oop.simulation;

import agh.ics.oop.gui.SimulationConfiguration;
import agh.ics.oop.model.maps.GlobeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SimulationTest {

    private Simulation simulation;
    private GlobeMap map;

    @BeforeEach
    public void setup() {
        SimulationConfiguration configuration = new SimulationConfiguration.Builder()
                .setAnimalNumber(10)
                .setMoveEnergy(1)
                .setAnimalStartingEnergy(20)
                .setAnimalGenomeLength(5)
                .setMapWidth(10)
                .setMapHeight(10)
                .setEnergyPerPlantEaten(10)
                .setWellFedAnimal(50)
                .setReproductionEnergy(100)
                .setMutationType(1)
                .setMinMutation(1)
                .setMaxMutation(5)
                .setPlantsType(0)
                .setPlantsGrowingPerDay(5)
                .setFertileRadius(2)
                .setFertilizationTime(3)
                .setPlantsNumber(20)
                .setMoveEnergy(5)
                .build();
        map = new GlobeMap(configuration);
        simulation = new Simulation(configuration, map);
    }

    @Test
    public void testGetAnimalsCount() {
        int expectedCount = 10;
        int actualCount = simulation.getAnimalsCount();
        Assertions.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testGetDay() {
        int expectedDay = 0;
        int actualDay = simulation.getDay();
        Assertions.assertEquals(expectedDay, actualDay);
    }

    @Test
    public void testGetAvgEnergy() {
        int expectedAvgEnergy = 20;
        int actualAvgEnergy = simulation.getAvgEnergy();
        Assertions.assertEquals(expectedAvgEnergy, actualAvgEnergy);
    }

    @Test
    public void testGetAvgLifespan() {
        int expectedAvgLifespan = 0;
        int actualAvgLifespan = simulation.getAvgLifespan();
        Assertions.assertEquals(expectedAvgLifespan, actualAvgLifespan);
    }

    @Test
    public void testGetAvgChildren() {
        int expectedAvgChildren = 0;
        int actualAvgChildren = simulation.getAvgChildren();
        Assertions.assertEquals(expectedAvgChildren, actualAvgChildren);
    }

    @Test
    public void testGetBestGenotype() {
        List<Integer> actualGenotype = simulation.getBestGenotype();
        Assertions.assertNotNull(actualGenotype);
    }
}
