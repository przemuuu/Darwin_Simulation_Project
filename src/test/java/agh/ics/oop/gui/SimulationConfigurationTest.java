package agh.ics.oop.gui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimulationConfigurationTest {

    @Test
    public void testBuilder() {
        SimulationConfiguration configuration = new SimulationConfiguration.Builder()
                .setConfigName("Test Configuration")
                .setMutationType(1)
                .setPlantsType(2)
                .setAnimalNumber(100)
                .setAnimalStartingEnergy(50)
                .setAnimalGenomeLength(10)
                .setMapWidth(1000)
                .setMapHeight(800)
                .setSpeedOfSimulation(5)
                .setPlantsNumber(200)
                .setPlantsGrowingPerDay(20)
                .setEnergyPerPlantEaten(10)
                .setReproductionEnergy(30)
                .setWellFedAnimal(80)
                .setMinMutation(1)
                .setMaxMutation(5)
                .setFertileRadius(3)
                .setFertilizationTime(10)
                .setMoveEnergy(2)
                .setLogWriting(true)
                .build();

        Assertions.assertEquals("Test Configuration", configuration.toString());
        Assertions.assertEquals(1, configuration.getMutationType());
        Assertions.assertEquals(2, configuration.getPlantsType());
        Assertions.assertEquals(100, configuration.getNumberOfAnimals());
        Assertions.assertEquals(50, configuration.getAnimalStartingEnergy());
        Assertions.assertEquals(10, configuration.getAnimalGenomeLength());
        Assertions.assertEquals(1000, configuration.getMapWidth());
        Assertions.assertEquals(800, configuration.getMapHeight());
        Assertions.assertEquals(5, configuration.getSpeedOfSimulation());
        Assertions.assertEquals(200, configuration.getPlantsNumber());
        Assertions.assertEquals(20, configuration.getPlantsGrowingPerDay());
        Assertions.assertEquals(10, configuration.getEnergyPerPlantEaten());
        Assertions.assertEquals(30, configuration.getReproductionEnergy());
        Assertions.assertEquals(80, configuration.getWellFedAnimal());
        Assertions.assertEquals(1, configuration.getMinMutation());
        Assertions.assertEquals(5, configuration.getMaxMutation());
        Assertions.assertEquals(3, configuration.getFertileRadius());
        Assertions.assertEquals(10, configuration.getFertilizationTime());
        Assertions.assertEquals(2, configuration.getMoveEnergy());
        Assertions.assertTrue(configuration.getLogWriting());
    }

    @Test
    public void testData() {
        SimulationConfiguration configuration = new SimulationConfiguration.Builder()
                .setConfigName("Test Configuration")
                .setMutationType(1)
                .setPlantsType(2)
                .setAnimalNumber(100)
                .setAnimalStartingEnergy(50)
                .setAnimalGenomeLength(10)
                .setMapWidth(1000)
                .setMapHeight(800)
                .setSpeedOfSimulation(5)
                .setPlantsNumber(200)
                .setPlantsGrowingPerDay(20)
                .setEnergyPerPlantEaten(10)
                .setReproductionEnergy(30)
                .setWellFedAnimal(80)
                .setMinMutation(1)
                .setMaxMutation(5)
                .setFertileRadius(3)
                .setFertilizationTime(10)
                .setMoveEnergy(2)
                .setLogWriting(true)
                .build();

        String[] expectedData = {
                "Test Configuration",
                "1",
                "2",
                "100",
                "50",
                "10",
                "1000",
                "800",
                "5",
                "200",
                "20",
                "10",
                "30",
                "80",
                "1",
                "5",
                "3",
                "10",
                "2"
        };

        Assertions.assertArrayEquals(expectedData, configuration.getData());
    }
}
