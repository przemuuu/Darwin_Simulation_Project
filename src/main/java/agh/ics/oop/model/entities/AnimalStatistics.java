package agh.ics.oop.model.entities;

public class AnimalStatistics {
    private final int birthDate;
    private int childrenNumber = 0;
    private int descendantsNumber = 0;
    private int eatenPlantsNumber = 0;
    private AnimalStatistics firstParent = null;
    private AnimalStatistics secondParent = null;
    private int recentDescendant = -1;
    private int deathDate = -1;
    public AnimalStatistics(int birthDate) {
        this.birthDate = birthDate;
    }
    public AnimalStatistics(AnimalStatistics firstParent, AnimalStatistics secondParent, int birthDate) {
        this.birthDate = birthDate;
        this.firstParent = firstParent;
        this.secondParent = secondParent;
        this.firstParent.newChild(this.hashCode());
        this.secondParent.newChild(this.hashCode());
    }
    public void animalDied(int dateOfDeath) {
        this.deathDate = dateOfDeath;
    }
    public void eatPlant() {
        this.eatenPlantsNumber++;
    }
    public void newChild(int childHash) {
        this.childrenNumber++;
        newDescendant(childHash);
    }
    public void newDescendant(int descendantHash) {
        if(this.recentDescendant == descendantHash) {
            return;
        }
        this.recentDescendant = descendantHash;
        this.descendantsNumber++;
        if(this.firstParent!=null) {
            this.firstParent.newDescendant(descendantHash);
        }
        if(this.secondParent!=null) {
            this.secondParent.newDescendant(descendantHash);
        }
    }
    public int getChildrenNumber() {
        return this.childrenNumber;
    }
    public int getDescendantsNumber() {
        return this.descendantsNumber;
    }
    public int getBirthDate() {
        return this.birthDate;
    }
    public String getDeathDate() {
        if(this.deathDate == -1) {
            return "Animal is still alive";
        }
        return String.valueOf(this.deathDate);
    }
    public int getDeathDateInt() {
        return this.deathDate;
    }
    public int getEatenPlantsNumber() {
        return this.eatenPlantsNumber;
    }
}
