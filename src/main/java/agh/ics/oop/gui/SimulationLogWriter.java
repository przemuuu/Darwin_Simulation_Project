package agh.ics.oop.gui;

import agh.ics.oop.model.maps.GlobeMap;
import agh.ics.oop.simulation.Simulation;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class SimulationLogWriter {
    private final GlobeMap map;
    public SimulationLogWriter() {
        this.map = null;
    }
    public SimulationLogWriter(GlobeMap map) {
        this.map = map;
        try(Writer writer = new FileWriter("src/main/resources/log" + this.map.hashCode() + ".csv",true)) {
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(new String[]{"Day", "Number of animals", "Number of plants", "Number of free fields", "Most popular genotype", "Average energy per animal", "Average life length for dead animals", "Average children per animal"});
            csvWriter.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void writeLine(Simulation simulation) {
        try(Writer writer = new FileWriter("src/main/resources/log" + this.map.hashCode() + ".csv",true)) {
            CSVWriter csvWriter = new CSVWriter(writer);
            String[] data = {String.valueOf(simulation.getDay()),
                    String.valueOf(simulation.getAnimalsCount()),
                    String.valueOf(this.map.getPlantsCount()),
                    String.valueOf(this.map.getFreeFieldsCount()),
                    String.valueOf(simulation.getBestGenotype()),
                    String.valueOf(simulation.getAvgEnergy()),
                    String.valueOf(simulation.getAvgLifespan()),
                    String.valueOf(simulation.getAvgChildren())};
            csvWriter.writeNext(data);
            csvWriter.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
