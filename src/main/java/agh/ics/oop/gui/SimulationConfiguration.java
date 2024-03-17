package agh.ics.oop.gui;

public class SimulationConfiguration {
    private String configName;
    private int mutationType;
    private int plantsType;
    private int animalNumber;
    private int animalStartingEnergy;
    private int animalGenomeLength;
    private int mapWidth;
    private int mapHeight;
    private int speedOfSimulation;
    private int plantsNumber;
    private int plantsGrowingPerDay;
    private int energyPerPlantEaten;
    private int reproductionEnergy;
    private int wellFedAnimal;
    private int minMutation;
    private int maxMutation;
    private int fertilizationTime;
    private int fertileRadius;
    private int moveEnergy;
    private boolean logWriting;
    public String toString() {
        return configName;
    }
    public int getMutationType() {
        return mutationType;
    }
    public int getPlantsType() {
        return plantsType;
    }
    public int getNumberOfAnimals() {
        return animalNumber;
    }
    public int getAnimalStartingEnergy() {
        return animalStartingEnergy;
    }
    public int getAnimalGenomeLength() {
        return animalGenomeLength;
    }
    public int getMapWidth() {
        return mapWidth;
    }
    public int getMapHeight() {
        return mapHeight;
    }
    public int getSpeedOfSimulation() {
        return speedOfSimulation;
    }
    public int getPlantsNumber() {
        return plantsNumber;
    }
    public int getPlantsGrowingPerDay() {
        return plantsGrowingPerDay;
    }
    public int getEnergyPerPlantEaten() {
        return energyPerPlantEaten;
    }
    public int getMinMutation() {
        return minMutation;
    }
    public int getMaxMutation() {
        return maxMutation;
    }
    public int getMoveEnergy() {
        return moveEnergy;
    }
    public int getReproductionEnergy() {
        return reproductionEnergy;
    }
    public int getWellFedAnimal() {
        return wellFedAnimal;
    }
    public int getFertilizationTime() {
        return fertilizationTime;
    }
    public int getFertileRadius() {
        return fertileRadius;
    }
    public boolean getLogWriting() {
        return logWriting;
    }
    public String[] getData(){
        return new String[]{toString(),
                String.valueOf(getMutationType()),
                String.valueOf(getPlantsType()),
                String.valueOf(getNumberOfAnimals()),
                String.valueOf(getAnimalStartingEnergy()),
                String.valueOf(getAnimalGenomeLength()),
                String.valueOf(getMapWidth()),
                String.valueOf(getMapHeight()),
                String.valueOf(getSpeedOfSimulation()),
                String.valueOf(getPlantsNumber()),
                String.valueOf(getPlantsGrowingPerDay()),
                String.valueOf(getEnergyPerPlantEaten()),
                String.valueOf(getReproductionEnergy()),
                String.valueOf(getWellFedAnimal()),
                String.valueOf(getMinMutation()),
                String.valueOf(getMaxMutation()),
                String.valueOf(getFertileRadius()),
                String.valueOf(getFertilizationTime()),
                String.valueOf(getMoveEnergy())};
    }
    public static class Builder {
        private String configName;
        private int mutationType;
        private int plantsType;
        private int animalNumber;
        private int animalStartingEnergy;
        private int animalGenomeLength;
        private int mapWidth;
        private int mapHeight;
        private int speedOfSimulation;
        private int plantsNumber;
        private int plantsGrowingPerDay;
        private int energyPerPlantEaten;
        private int reproductionEnergy;
        private int wellFedAnimal;
        private int minMutation;
        private int maxMutation;
        private int fertilizationTime;
        private int fertileRadius;
        private int moveEnergy;
        private boolean logWriting;
        public Builder setMutationType(int type) {
            this.mutationType = type;
            return this;
        }
        public Builder setPlantsType(int type) {
            this.plantsType = type;
            return this;
        }
        public Builder setConfigName(String name) {
            this.configName = name;
            return this;
        }
        public Builder setAnimalNumber(int animals) {
            this.animalNumber = animals;
            return this;
        }
        public Builder setAnimalStartingEnergy(int energy) {
            this.animalStartingEnergy = energy;
            return this;
        }
        public Builder setAnimalGenomeLength(int length) {
            this.animalGenomeLength = length;
            return this;
        }
        public Builder setMapWidth(int width) {
            this.mapWidth = width;
            return this;
        }
        public Builder setMapHeight(int height) {
            this.mapHeight = height;
            return this;
        }
        public Builder setSpeedOfSimulation(int speed) {
            this.speedOfSimulation = speed;
            return this;
        }
        public Builder setPlantsNumber(int plants) {
            this.plantsNumber = plants;
            return this;
        }
        public Builder setPlantsGrowingPerDay(int plantsGrowing) {
            this.plantsGrowingPerDay = plantsGrowing;
            return this;
        }
        public Builder setEnergyPerPlantEaten(int energyGained) {
            this.energyPerPlantEaten = energyGained;
            return this;
        }
        public Builder setReproductionEnergy(int reproductionEnergy) {
            this.reproductionEnergy = reproductionEnergy;
            return this;
        }
        public Builder setWellFedAnimal(int wellFedAnimal) {
            this.wellFedAnimal = wellFedAnimal;
            return this;
        }
        public Builder setMinMutation(int minMutation) {
            this.minMutation = minMutation;
            return this;
        }
        public Builder setMaxMutation(int maxMutation) {
            this.maxMutation = maxMutation;
            return this;
        }
        public Builder setFertileRadius(int fertileRadius) {
            this.fertileRadius = fertileRadius;
            return this;
        }
        public Builder setFertilizationTime(int fertilizationTime) {
            this.fertilizationTime = fertilizationTime;
            return this;
        }
        public Builder setMoveEnergy(int moveEnergy) {
            this.moveEnergy = moveEnergy;
            return this;
        }
        public Builder setLogWriting(boolean logWriting) {
            this.logWriting = logWriting;
            return this;
        }
        public SimulationConfiguration build() {
            SimulationConfiguration configuration = new SimulationConfiguration();
            configuration.configName = this.configName;
            configuration.mutationType = this.mutationType;
            configuration.plantsType = this.plantsType;
            configuration.animalNumber = this.animalNumber;
            configuration.animalStartingEnergy = this.animalStartingEnergy;
            configuration.animalGenomeLength = this.animalGenomeLength;
            configuration.mapWidth = this.mapWidth;
            configuration.mapHeight = this.mapHeight;
            configuration.speedOfSimulation = this.speedOfSimulation;
            configuration.plantsNumber = this.plantsNumber;
            configuration.plantsGrowingPerDay = this.plantsGrowingPerDay;
            configuration.energyPerPlantEaten = this.energyPerPlantEaten;
            configuration.reproductionEnergy = this.reproductionEnergy;
            configuration.wellFedAnimal = this.wellFedAnimal;
            configuration.minMutation = this.minMutation;
            configuration.maxMutation = this.maxMutation;
            configuration.fertileRadius = this.fertileRadius;
            configuration.fertilizationTime = this.fertilizationTime;
            configuration.moveEnergy = this.moveEnergy;
            configuration.logWriting = this.logWriting;
            return configuration;
        }
    }
}
