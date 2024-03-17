package agh.ics.oop.model.maps;

import agh.ics.oop.gui.SimulationConfiguration;
import agh.ics.oop.model.entities.*;
import agh.ics.oop.model.maps.plants.ForestedEquator;
import agh.ics.oop.model.maps.plants.GrassGenerator;
import agh.ics.oop.model.maps.plants.LifegivingCorpses;
import agh.ics.oop.model.maps.plants.PlantsType;
import agh.ics.oop.model.movement.MapDirection;
import agh.ics.oop.model.utilities.Boundary;
import agh.ics.oop.model.utilities.MapChangeListener;
import agh.ics.oop.model.utilities.RandomGrassPositionGenerator;
import agh.ics.oop.model.utilities.Vector2d;

import java.util.*;

public class GlobeMap {
    private final Vector2d lowerLeft;
    protected final Vector2d upperRight;
    private MapChangeListener observer = null;
    protected final Map<Vector2d, LinkedList<Animal>> animals = new HashMap<>();
    protected Map<Vector2d, Grass> grasses = new HashMap<>();
    protected final List<Grass> grassesList = new ArrayList<>();
    protected Map<Vector2d, FertileField> fertileFields = new HashMap<>();
    private final int ENERGY_PER_PLANT;
    private final int WELL_FED_ANIMAL;
    private final int REPRODUCTION_ENERGY;
    private final int MUTATION_TYPE;
    private final int MIN_MUTATIONS;
    private final int MAX_MUTATION;
    private final int PLANTS_TYPE;
    private final int PLANTS_PER_DAY;
    private final int FERTILIZE_RADIUS;
    private final int FERTILIZATION_TIME;
    private final int ENERGY_PER_MOVE;
    private final int STARTING_PLANTS;
    private PlantsType typeOfPlants;
    public GlobeMap(SimulationConfiguration configuration) {
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(configuration.getMapWidth()-1,configuration.getMapHeight()-1);
        this.ENERGY_PER_PLANT = configuration.getEnergyPerPlantEaten();
        this.WELL_FED_ANIMAL = configuration.getWellFedAnimal();
        this.REPRODUCTION_ENERGY = configuration.getReproductionEnergy();
        this.MUTATION_TYPE = configuration.getMutationType();
        this.MIN_MUTATIONS = configuration.getMinMutation();
        this.MAX_MUTATION = configuration.getMaxMutation();
        this.PLANTS_TYPE = configuration.getPlantsType();
        this.PLANTS_PER_DAY = configuration.getPlantsGrowingPerDay();
        this.FERTILIZE_RADIUS = configuration.getFertileRadius();
        this.FERTILIZATION_TIME = configuration.getFertilizationTime();
        this.STARTING_PLANTS = configuration.getPlantsNumber();
        this.ENERGY_PER_MOVE = configuration.getMoveEnergy();
        switch(this.PLANTS_TYPE) {
            case 0:
                this.typeOfPlants = new ForestedEquator(this.upperRight);
                this.fertileFields = typeOfPlants.generateFertilePositions();
                break;
            case 1:
                this.typeOfPlants = new LifegivingCorpses(this.upperRight, this.FERTILIZE_RADIUS, this.FERTILIZATION_TIME);
                break;
        }
    }
    public void setObserver(MapChangeListener observer) {
        this.observer = observer;
    }
    public int getFreeFieldsCount() {
        return (this.upperRight.get_x()+1)*(this.upperRight.get_y()+1)-animals.size();
    }
    public int getPlantsCount() {
        return grasses.size();
    }
    public void place(Animal animal){
        if(!animals.containsKey(animal.getPosition())) {
            LinkedList<Animal> animalList = new LinkedList<>();
            animalList.add(animal);
            animals.put(animal.getPosition(),animalList);
        } else {
            animals.get(animal.getPosition()).add(animal);
        }
    }
    public void removeAnimal(Animal animal){
        if(animals.get(animal.getPosition()).size() == 1) {
            animals.remove(animal.getPosition());
        } else {
            animals.get(animal.getPosition()).remove(animal);
        }
    }
    public boolean isThereGenotype(Vector2d position, List<Integer> genotype) {
        for(Animal animal : animals.get(position)) {
            if(animal.getGenotype().equals(genotype)) return true;
        }
        return false;
    }
    public void simulateAnimalsMove(List<Animal> list){
        if(list.isEmpty()) return;
        List<Animal> toRemove = new ArrayList<>();
        for (Animal animal : list) {
            if (animal.isAlive()) {
                move(animal);
            }else {
                removeAnimal(animal);
                toRemove.add(animal);
            }
        }
        list.removeAll(toRemove);
        mapChanged();
    }
    public void move(Animal animal) {
        removeAnimal(animal);
        animal.move();
        Vector2d newPosition = animal.getPosition();
        if (newPosition.get_y()<0 || newPosition.get_y()>this.upperRight.get_y()){
            animal.moveBack();
        }
        if ((newPosition.get_x()<0 || newPosition.get_x()>this.upperRight.get_x())&&(newPosition.get_y()>=0 &&newPosition.get_y()<=upperRight.get_y())){
            animal.moveAround(upperRight.get_x());
        }
        place(animal);
    }
    public void mapChanged( ) {
        observer.mapChanged(this);
    }
    public WorldElement objectAt(Vector2d targetPosition) {
        if(animals.containsKey(targetPosition)) {
            return animals.get(targetPosition).get(0);
        } else if(grasses.get(targetPosition)!=null) {
            return (grasses.get(targetPosition));
        } else {
            return null;
        }
    }
    public int getWellFedEnergy() {
        return this.WELL_FED_ANIMAL;
    }
    public Animal getTopAnimal(Vector2d position) {
        if(animals.containsKey(position)) {
            return animals.get(position).getFirst();
        } else {
            return null;
        }
    }
    public Boundary getCurrentBounds() {
        return new Boundary(this.lowerLeft,this.upperRight);
    }
    public void eatPlants(List<Animal> list){
        List<Grass> toRemove = new ArrayList<>();
        for (Grass grass : grassesList){
            Animal strongestanimal = null;
            int strongestenergy=0;
            for (Animal animal : list) {
                if (grass.getPosition().equals(animal.getPosition()) && animal.getEnergy()>strongestenergy){
                    strongestanimal = animal;
                    strongestenergy = animal.getEnergy();
                }
            }
            if(strongestenergy>0){
                strongestanimal.eatPlant(this.ENERGY_PER_PLANT);
                toRemove.add(grass);
                grasses.remove(grass.getPosition());
            }
        }
        grassesList.removeAll(toRemove);
    }
    public List<Animal> multiplication(List<Animal> list, int day){
        List<Animal> toAdd = new ArrayList<>();
        for(int x=0;x<upperRight.get_x();x++){
            for(int y=0;y<upperRight.get_y();y++) {
                Vector2d thisposition = new Vector2d(x,y);
                List<Animal> filteredAnimals  = list.stream()
                        .filter(animal -> animal.getPosition().equals(thisposition))
                        .filter(animal -> animal.getEnergy() >= WELL_FED_ANIMAL).sorted(Comparator.comparingInt(Animal::getEnergy).reversed())
                        .toList();

                for(int i=1;i<filteredAnimals.size();i=i+2){
                    Animal firstAnimal = filteredAnimals.get(i-1);
                    Animal secondAnimal = filteredAnimals.get(i);
                    int biggerEnergy = firstAnimal.getEnergy();
                    int smallerEnergy = secondAnimal.getEnergy();
                    double percentage= (double) biggerEnergy / (smallerEnergy+biggerEnergy);

                    List<Integer> firstGenotype = firstAnimal.getGenotype();
                    List<Integer> secondGenotype = secondAnimal.getGenotype();
                    int numberOfFirstGenotype = (int) Math.ceil(percentage*firstGenotype.size());
                    int numberOfSecondGenotype = firstGenotype.size()-numberOfFirstGenotype;
                    Random random = new Random();
                    boolean left = random.nextBoolean();
                    List <Integer> newGenotype;
                    if (left) {
                        newGenotype=mergeLists(firstGenotype,secondGenotype,numberOfFirstGenotype,numberOfSecondGenotype);
                    }else{
                        newGenotype=mergeLists(secondGenotype,firstGenotype,numberOfSecondGenotype,numberOfFirstGenotype);
                    }
                    MapDirection direction = MapDirection.values()[random.nextInt(MapDirection.values().length)];
                    int activegeneindex = random.nextInt(newGenotype.size());

                    AnimalStatistics statistics = new AnimalStatistics(firstAnimal.animalStatistics,secondAnimal.animalStatistics,day);
                    Animal newAnimal = new Animal(direction,thisposition,2*REPRODUCTION_ENERGY,newGenotype,activegeneindex,statistics,this.ENERGY_PER_MOVE);

                    place(newAnimal);
                    toAdd.add(newAnimal);
                    firstAnimal.energyMultiplicate(REPRODUCTION_ENERGY);
                    secondAnimal.energyMultiplicate(REPRODUCTION_ENERGY);
                    newAnimal.mutation(MUTATION_TYPE,MIN_MUTATIONS,MAX_MUTATION);
                }
            }
        }
        return toAdd;
    }
    public static List<Integer> mergeLists(List<Integer> list1, List<Integer> list2, int n, int m) {
        List<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < Math.min(n, list1.size()); i++) {
            resultList.add(list1.get(i));
        }
        if (list2.size() > m) {
            for (int i = list2.size() - m; i < list2.size(); i++) {
                resultList.add(list2.get(i));
            }
        } else {
            resultList.addAll(list2);
        }
        return resultList;
    }
    public void initializeGrass() {
        RandomGrassPositionGenerator grassGenerator = new RandomGrassPositionGenerator(this.upperRight.get_x(), this.upperRight.get_y(), this.STARTING_PLANTS, this.fertileFields, this.grasses);
        List<Vector2d> freshGrass = grassGenerator.generateRandomPositions();
        for(Vector2d position : freshGrass) {
            this.grasses.put(position, new Grass(position));
            if(this.grasses.size()==this.STARTING_PLANTS) break;
        }
    }
    public void fertileFields(){
        this.fertileFields = typeOfPlants.generateFertilePositions();
        this.typeOfPlants.fertileFieldNextDay();
    }
    public void addFertilizedField(Vector2d position) {
        this.typeOfPlants.addFertileField(position);
    }
    public void growEveryday(){
        fertileFields();
        GrassGenerator grassGenerator = new GrassGenerator(this.upperRight, this.grasses, this.fertileFields);
        this.grasses = grassGenerator.growGrass(this.PLANTS_PER_DAY);
        this.grassesList.addAll(this.grasses.values());
    }
    public boolean isFertile(Vector2d position) {
        return this.fertileFields.containsKey(position);
    }
}
