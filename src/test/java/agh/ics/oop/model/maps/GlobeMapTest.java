package agh.ics.oop.model.maps;

import agh.ics.oop.gui.SimulationConfiguration;
import agh.ics.oop.model.entities.Animal;
import agh.ics.oop.model.entities.AnimalStatistics;
import agh.ics.oop.model.entities.Grass;
import agh.ics.oop.model.entities.WorldElement;
import agh.ics.oop.model.movement.MapDirection;
import agh.ics.oop.model.utilities.MapChangeListener;
import agh.ics.oop.model.utilities.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GlobeMapTest {

    private GlobeMap globeMap;

    @BeforeEach
    public void setUp() {
        SimulationConfiguration configuration = new SimulationConfiguration.Builder()
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

        globeMap = new GlobeMap(configuration);
    }

    @Test
    public void testGetFreeFieldsCount() {
        int expectedFreeFieldsCount = 100 - globeMap.getPlantsCount();
        int actualFreeFieldsCount = globeMap.getFreeFieldsCount();

        Assertions.assertEquals(expectedFreeFieldsCount, actualFreeFieldsCount);
    }

    @Test
    public void testPlace() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(5, 5), 100, new ArrayList<>(), 0, null, 5);
        globeMap.place(animal);

        WorldElement element = globeMap.objectAt(new Vector2d(5, 5));

        Assertions.assertEquals(animal, element);
    }

    @Test
    public void testRemoveAnimal() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(5, 5), 100, new ArrayList<>(), 0, null, 5);
        globeMap.place(animal);

        globeMap.removeAnimal(animal);

        WorldElement element = globeMap.objectAt(new Vector2d(5, 5));

        Assertions.assertNull(element);
    }

    @Test
    public void testGetPlantsCount() {
        int expectedPlantsCount = globeMap.getPlantsCount();
        int actualPlantsCount = globeMap.getPlantsCount();

        Assertions.assertEquals(expectedPlantsCount, actualPlantsCount);
    }

    @Test
    public void testEatPlants() {
        AnimalStatistics statistics = new AnimalStatistics(0);
        AnimalStatistics statistics2 = new AnimalStatistics(0);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(5, 5), 100, new ArrayList<>(), 0, statistics, 5);
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(5, 6), 100, new ArrayList<>(), 0, statistics2, 5);
        List<Animal> animals = new LinkedList<>();
        animals.add(animal1);
        animals.add(animal2);

        globeMap.place(animal1);
        globeMap.place(animal2);

        Grass grass = new Grass(new Vector2d(5, 5));
        globeMap.grasses.put(new Vector2d(5, 5), grass);
        globeMap.grassesList.add(grass);

        globeMap.eatPlants(animals);

        Assertions.assertEquals(110, animal1.getEnergy());
    }

    @Test
    public void testMultiplication() {
        List<Animal> animals = new ArrayList<>();
        List<Integer> genotype = Arrays.asList(1, 2, 3, 4);
        AnimalStatistics statistics = new AnimalStatistics(0);
        AnimalStatistics statistics2 = new AnimalStatistics(0);
        AnimalStatistics statistics3 = new AnimalStatistics(0);
        AnimalStatistics statistics4 = new AnimalStatistics(0);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(0, 0), 100, genotype, 0, statistics, 5);
        Animal animal2 = new Animal(MapDirection.NORTH, new Vector2d(0, 0), 100, genotype, 0, statistics2, 5);
        Animal animal3 = new Animal(MapDirection.NORTH, new Vector2d(1, 1), 100, genotype, 0, statistics3, 5);
        Animal animal4 = new Animal(MapDirection.NORTH, new Vector2d(1, 1), 100, genotype, 0, statistics4, 5);
        animals.add(animal1);
        animals.add(animal2);
        animals.add(animal3);
        animals.add(animal4);

        List<Animal> newAnimals = globeMap.multiplication(animals, 1);

        for (Animal newAnimal : newAnimals) {
            WorldElement element = globeMap.objectAt(newAnimal.getPosition());
            Assertions.assertEquals(newAnimal, element);
        }
    }
    @Test
    public void testSimulateAnimalsMove() {
        AnimalStatistics statistics = new AnimalStatistics(0);
        AnimalStatistics statistics2 = new AnimalStatistics(0);
        List<Integer> genotype = Arrays.asList(1, 2, 3, 4);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(5, 5), 100, genotype, 0, statistics, 5);
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(5, 6), 100, genotype, 0, statistics2, 5);
        List<Animal> animals = new LinkedList<>();
        animals.add(animal1);
        animals.add(animal2);
        MapChangeListener observer = new MapChangeListener() {
            @Override
            public void mapChanged(GlobeMap globeMap) {
                // Do nothing
            }
        };
        globeMap.place(animal1);
        globeMap.place(animal2);
        globeMap.setObserver(observer);
        globeMap.simulateAnimalsMove(animals);

        WorldElement element1 = globeMap.objectAt(new Vector2d(6, 5));
        WorldElement element2 = globeMap.objectAt(new Vector2d(4, 6));
        WorldElement element3 = globeMap.objectAt(new Vector2d(5, 5));

        Assertions.assertNull(element3);
        Assertions.assertEquals(animal1, element2);
        Assertions.assertEquals(animal2, element1);
    }
}
