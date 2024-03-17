package agh.ics.oop.simulation;

import agh.ics.oop.gui.SimulationConfiguration;
import agh.ics.oop.model.entities.Animal;
import agh.ics.oop.model.entities.AnimalStatistics;
import agh.ics.oop.model.maps.GlobeMap;
import agh.ics.oop.model.movement.MapDirection;
import agh.ics.oop.model.utilities.Vector2d;

import java.util.*;

public class Simulation implements Runnable{
    protected final List<Animal> animals = new ArrayList<>();
    protected final List<Animal> deadAnimals = new ArrayList<>();
    private int day = 0;
    private final GlobeMap map;
    private final int SIMULATION_SPEED;
    private boolean isPaused = false;
    private boolean stopSimulation = false;
    private final Random random = new Random();
    //Tworzenie symulacji, czyli dodawanie losowo zwierząt, roślin itp.
    public Simulation(SimulationConfiguration configuration, GlobeMap map) {
        Random random = new Random();
        this.map = map;
        this.SIMULATION_SPEED = configuration.getSpeedOfSimulation();
        for (int i = 0; i < configuration.getNumberOfAnimals(); i++) {
            // Generowanie losowej pozycji
            int x = random.nextInt(configuration.getMapWidth());
            int y = random.nextInt(configuration.getMapHeight());
            Vector2d position = new Vector2d(x, y);

            // Generowanie losowego kierunku
            MapDirection direction = MapDirection.values()[random.nextInt(MapDirection.values().length)];

            // Generowanie losowego genotypu
            List<Integer> genotype = generateRandomGenotype(configuration.getAnimalGenomeLength());
            // Generowanie losowo, od którego indexu genotypu startujemy

            int activegeneindex = random.nextInt(genotype.size());
            // Tworzenie zwierzęcia
            Animal animal = new Animal(direction, position, configuration.getAnimalStartingEnergy(), genotype, activegeneindex, new AnimalStatistics(0), configuration.getMoveEnergy());

            map.place(animal);
            animals.add(animal);
        }
        map.initializeGrass();
    }
    public int getAnimalsCount() {
        return animals.size();
    }
    public int getDay() {
        return this.day;
    }
    public int getAvgEnergy() {
        if(animals.isEmpty()) return 0;
        int sum = 0;
        for (Animal animal : animals) {
            sum += animal.getEnergy();
        }
        return sum / animals.size();
    }
    private List<Integer> generateRandomGenotype(int length) {
        List<Integer> genotype = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            genotype.add(random.nextInt(7));
        }
        return genotype;
    }
    //Symulacja
    public void run() {
        map.mapChanged();
        while (areAnimalsAlive()) {
            if(stopSimulation) break;
            try {
                //while(isPaused) Thread.sleep(10);
                Thread.sleep(SIMULATION_SPEED); // Opóźnienie
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            if(!isPaused) {
                removeDeadAnimals();
                List<Animal> newAnimals = map.multiplication(animals,this.day);
                map.simulateAnimalsMove(animals);
                map.eatPlants(animals);
                map.growEveryday();
                if(!newAnimals.isEmpty()) {
                    animals.addAll(newAnimals);
                }
                this.day++;
            }
        }
        removeDeadAnimals();
        map.mapChanged();
    }
    private boolean areAnimalsAlive() {
        if(animals.isEmpty()) return false;
        for (Animal animal : animals) {
            if (animal.getEnergy() > 0) {
                return true;
            }
        }
        return false;
    }
    public int getAvgLifespan() {
        if(deadAnimals.isEmpty()) return 0;
        int sum = 0;
        for (Animal animal : deadAnimals) {
            sum += animal.animalStatistics.getDeathDateInt() - animal.animalStatistics.getBirthDate();
        }
        return sum / deadAnimals.size();
    }
    public int getAvgChildren() {
        int sum = 0;
        for (Animal animal : deadAnimals) {
            sum += animal.animalStatistics.getChildrenNumber();
        }
        for (Animal animal : animals) {
            sum += animal.animalStatistics.getChildrenNumber();
        }
        return sum / (deadAnimals.size()+animals.size());
    }
    public List<Integer> getBestGenotype() {
        HashMap<List<Integer>, Integer> genotypes = new HashMap<>();
        for (Animal animal : animals) {
            if (!genotypes.containsKey(animal.getGenotype())) {
                genotypes.put(animal.getGenotype(), 1);
            } else {
                genotypes.put(animal.getGenotype(), genotypes.get(animal.getGenotype()) + 1);
            }
        }
        int times = 0;
        List<Integer> bestGenotype = null;
        for (Map.Entry<List<Integer>, Integer> entry : genotypes.entrySet()) {
            if (entry.getValue() > times) {
                times = entry.getValue();
                bestGenotype = entry.getKey();
            }
        }
        return bestGenotype;
    }
    private void removeDeadAnimals() {
        if(animals.isEmpty()) return;
        for(int i = 0; i < animals.size(); i++) {
            if(!animals.get(i).isAlive()) {
                map.addFertilizedField(animals.get(i).getPosition());
                map.removeAnimal(animals.get(i));
                animals.get(i).animalStatistics.animalDied(day);
                deadAnimals.add(animals.get(i));
                animals.remove(animals.get(i));
                i--;
            }
        }
    }
    public void pauseSimulation() {
        isPaused = true;
    }
    public void resumeSimulation() {
        isPaused = false;
    }
    public void stopSimulation() {
        stopSimulation = true;
    }
}
