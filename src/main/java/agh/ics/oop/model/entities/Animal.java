package agh.ics.oop.model.entities;

import agh.ics.oop.model.movement.MapDirection;
import agh.ics.oop.model.utilities.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;
    private final List<Integer> genotype;
    private int energy;
    private final int ENERGY_PER_MOVE;
    private int activeGeneIndex;
    public AnimalStatistics animalStatistics;

    public Animal(MapDirection orientation, Vector2d position, int initialEnergy, List<Integer> genotype, int activeGeneIndex, AnimalStatistics statistics, int moveEnergy) {
        this.orientation = orientation;
        this.energy = initialEnergy;
        this.genotype = genotype;
        this.position = position;
        this.activeGeneIndex = activeGeneIndex;
        this.animalStatistics = statistics;
        this.ENERGY_PER_MOVE = moveEnergy;
    }
    @Override
    public String toString() {
        return(orientation.toString());
    }
    @Override
    public Vector2d getPosition() {
        return (this.position);
    }
    private void rotateAccordingToGene() {
        int rotationSteps = genotype.get(activeGeneIndex);
        for (int i = 0; i < rotationSteps; i++) {
            assert this.orientation != null;
            this.orientation = this.orientation.next();
        }
    }
    public void move() {
        rotateAccordingToGene();
        Vector2d unitVector = this.orientation.toUnitVector();
        assert unitVector != null;
        this.position = this.position.add(unitVector);
        this.energy -= this.ENERGY_PER_MOVE;
        updateActiveGene();
    }
    private void rotateBack() {
        for (int i = 0; i < 4; i++) {
            assert this.orientation != null;
            this.orientation = this.orientation.next();
        }
    }
    public void moveBack() {
        rotateBack();
        Vector2d unitVector = this.orientation.toUnitVector();
        assert unitVector != null;
        this.position = this.position.add(unitVector);
    }
    public void moveAround(int x) {
        if(this.position.get_x()<0){
            this.position = new Vector2d(x,this.position.get_y());
        }else {
            this.position = new Vector2d(0,this.position.get_y());
        }
    }
    private void updateActiveGene() {
        this.activeGeneIndex = (this.activeGeneIndex + 1) % this.genotype.size();
    }
    public int getActiveGeneIndex() {
        return this.activeGeneIndex;
    }
    public void eatPlant(int n){
        this.energy = energy + n;
        this.animalStatistics.eatPlant();
    }
    public int getEnergy() {
        return this.energy;
    }
    public boolean isAlive() {
        return this.energy > 0;
    }
    public void energyMultiplicate(int n){
        this.energy = energy - n;
    }
    public List<Integer> getGenotype() {
        return this.genotype;
    }
    public void mutation(int option,int minNumberOfMutations,int maxNumberOfMutations){
        if(maxNumberOfMutations==0 || minNumberOfMutations>maxNumberOfMutations){
            return;
        }
        Random rand = new Random();
        int randomNum=rand.nextInt((maxNumberOfMutations - minNumberOfMutations) + 1) + minNumberOfMutations;
        if(option==0){
            List<Integer> numbers = new ArrayList<>();
            for (int i = 0; i < genotype.size(); i++) {
                numbers.add(i);
            }
            Collections.shuffle(numbers);
            List <Integer> drawnNumbers= numbers.subList(0, randomNum);
            for (Integer drawnNumber : drawnNumbers) {
                Random randNext = new Random();
                int randomNumNext = randNext.nextInt(8);
                genotype.set(drawnNumber, randomNumNext);
            }
        }else{
            for (int i=0;i<randomNum;i++){
                Random rand1 = new Random();
                int random1 = rand1.nextInt(genotype.size());
                Random rand2 = new Random();
                int random2 = rand2.nextInt(genotype.size());
                int storage = genotype.get(random1);
                genotype.set(random1,genotype.get(random2));
                genotype.set(random2,storage);
            }
        }
    }
}
