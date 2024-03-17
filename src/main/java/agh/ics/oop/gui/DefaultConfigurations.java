package agh.ics.oop.gui;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultConfigurations {
    public static List<SimulationConfiguration> getConfigurations() throws IOException {
        List<SimulationConfiguration> configurations = new ArrayList<>();
        CSVReader reader = new CSVReaderBuilder(new FileReader("src/main/resources/configurations.csv")).build();
        String[] nextLine;
        while((nextLine = reader.readNext()) != null) {
            SimulationConfiguration config = new SimulationConfiguration.Builder()
                    .setConfigName(nextLine[0])
                    .setMutationType(Integer.parseInt(nextLine[1]))
                    .setPlantsType(Integer.parseInt(nextLine[2]))
                    .setAnimalNumber(Integer.parseInt(nextLine[3]))
                    .setAnimalStartingEnergy(Integer.parseInt(nextLine[4]))
                    .setAnimalGenomeLength(Integer.parseInt(nextLine[5]))
                    .setMapWidth(Integer.parseInt(nextLine[6]))
                    .setMapHeight(Integer.parseInt(nextLine[7]))
                    .setSpeedOfSimulation(Integer.parseInt(nextLine[8]))
                    .setPlantsNumber(Integer.parseInt(nextLine[9]))
                    .setPlantsGrowingPerDay(Integer.parseInt(nextLine[10]))
                    .setEnergyPerPlantEaten(Integer.parseInt(nextLine[11]))
                    .setReproductionEnergy(Integer.parseInt(nextLine[12]))
                    .setWellFedAnimal(Integer.parseInt(nextLine[13]))
                    .setMinMutation(Integer.parseInt(nextLine[14]))
                    .setMaxMutation(Integer.parseInt(nextLine[15]))
                    .setFertileRadius(Integer.parseInt(nextLine[16]))
                    .setFertilizationTime(Integer.parseInt(nextLine[17]))
                    .setMoveEnergy(Integer.parseInt(nextLine[18]))
                    .build();
            configurations.add(config);
        }
        return configurations;
    }
}
