package agh.ics.oop.presenter;

import agh.ics.oop.gui.SimulationLogWriter;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.SimulationEngine;
import agh.ics.oop.model.entities.Animal;
import agh.ics.oop.model.maps.GlobeMap;
import agh.ics.oop.model.utilities.Boundary;
import agh.ics.oop.model.utilities.MapChangeListener;
import agh.ics.oop.model.utilities.Vector2d;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimulationPresenter implements MapChangeListener {
    private double CELL_WIDTH;
    private double CELL_HEIGHT;
    private GlobeMap worldMap;
    private Animal trackedAnimal = null;
    private Boundary bounds;
    private boolean highlight = false;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label day;
    @FXML
    private Label aliveAnimals;
    @FXML
    private Label alivePlants;
    @FXML
    private Label freeFields;
    @FXML
    private Label avgEnergy;
    @FXML
    private Label bestGenotype1;
    @FXML
    private Label avgLifespan;
    @FXML
    private Label avgChildren;
    @FXML
    private Button pause;
    @FXML
    private VBox animalStats;
    @FXML
    private Label animalSelected;
    @FXML
    private LineChart<Number,Number> simulationChart;
    private boolean writeLog = false;
    private SimulationLogWriter logWriter = new SimulationLogWriter();
    private final XYChart.Series<Number,Number> plantSeries = new XYChart.Series<>();
    private final XYChart.Series<Number,Number> animalSeries = new XYChart.Series<>();
    private Simulation simulation;
    public void initialize() {
        animalSeries.setName("Animals");
        plantSeries.setName("Plants");
        simulationChart.getData().addAll(animalSeries, plantSeries);
        animalSeries.getNode().setStyle("-fx-stroke: #f6c13e;");
        plantSeries.getNode().setStyle("-fx-stroke: #4caf50;");
    }
    public void updateChart() {
        animalSeries.getData().add(new XYChart.Data<>(simulation.getDay(), simulation.getAnimalsCount()));
        plantSeries.getData().add(new XYChart.Data<>(simulation.getDay(), worldMap.getPlantsCount()));
    }
    public void setData(Simulation simulation, GlobeMap map) {
        this.worldMap = map;
        this.simulation = simulation;
        this.CELL_WIDTH = (double) 1050/(worldMap.getCurrentBounds().upperRight().get_x()+2);
        this.CELL_HEIGHT = (double) 950/(worldMap.getCurrentBounds().upperRight().get_y()+2);
        List<Simulation> simulations = new ArrayList<>();
        simulations.add(simulation);
        SimulationEngine simulationEngine = new SimulationEngine(simulations);
        simulationEngine.runAsync();
    }
    public void useLogWriter() {
        this.writeLog = true;
        this.logWriter = new SimulationLogWriter(worldMap);
    }
    public void forceStop() {
        simulation.stopSimulation();
    }
    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
    public void drawMap(GlobeMap worldMap) {
        bounds = worldMap.getCurrentBounds();
        clearGrid();
        Label header = new Label();
        header.setText(" y\\x ");
        mapGrid.add(header,0,0);
        GridPane.setHalignment(header, HPos.CENTER);
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        for(int i=bounds.upperRight().get_y();i>=bounds.lowerLeft().get_y();i--) {
            Label height = new Label();
            height.setText(""+i);
            mapGrid.add(height,0,bounds.upperRight().get_y()-i+1);
            GridPane.setHalignment(height, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
        for(int j=bounds.lowerLeft().get_x();j<=bounds.upperRight().get_x();j++) {
            Label width = new Label();
            width.setText(""+j);
            mapGrid.add(width,j-bounds.lowerLeft().get_x()+1,0);
            GridPane.setHalignment(width, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
        for(int i=bounds.lowerLeft().get_x();i<=bounds.upperRight().get_x();i++) {
            for(int j=bounds.upperRight().get_y();j>=bounds.lowerLeft().get_y();j--) {
                Vector2d position = new Vector2d(i, j);



                //adding fertile fields
                Rectangle fertilized = new Rectangle(CELL_WIDTH,CELL_HEIGHT);
                if(trackedAnimal != null && Objects.equals(trackedAnimal.getPosition(), position)) {
                    fertilized.setFill(Color.ORCHID);
                } else if(worldMap.isFertile(position)) {
                    fertilized.setFill(Color.GREEN);
                } else {
                    fertilized.setFill(Color.LIGHTGREEN);
                }
                fertilized.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    trackedAnimal = null;
                    drawMap(worldMap);
                    animalStats.getChildren().clear();
                    trackAnimal();
                });
                mapGrid.add(fertilized,i-bounds.lowerLeft().get_x()+1,bounds.upperRight().get_y()-j+1);

                //showing animal, grass or empty field
                if(worldMap.objectAt(position) != null) {
                    if(Objects.equals(worldMap.objectAt(position).toString(), "*")) {
                        ImageView grass = new ImageView("images/grass.png");
                        grass.fitHeightProperty().bind(fertilized.heightProperty());
                        grass.fitWidthProperty().bind(fertilized.widthProperty());
                        grass.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                            trackedAnimal = null;
                            drawMap(worldMap);
                            animalStats.getChildren().clear();
                            trackAnimal();
                        });
                        mapGrid.add(grass,i-bounds.lowerLeft().get_x()+1,bounds.upperRight().get_y()-j+1);
                        GridPane.setHalignment(grass, HPos.CENTER);
                    } else {
                        ImageView animal;
                        Animal animalToDraw = worldMap.getTopAnimal(position);
                        double animalColor = (double) animalToDraw.getEnergy() / worldMap.getWellFedEnergy();
                        if(highlight && worldMap.isThereGenotype(position,simulation.getBestGenotype())) {
                            animal = new ImageView("images/goldenAnimal.png");
                        } else if(animalColor < 0.2){
                            animal = new ImageView("images/animalVeryLow.png");
                        } else if(animalColor < 0.4) {
                            animal = new ImageView("images/animalLow.png");
                        } else if(animalColor < 0.6) {
                            animal = new ImageView("images/animalMedium.png");
                        } else if(animalColor < 0.8) {
                            animal = new ImageView("images/animalHigh.png");
                        } else {
                            animal = new ImageView("images/animalVeryHigh.png");
                        }
                        animal.fitHeightProperty().bind(fertilized.heightProperty());
                        animal.fitWidthProperty().bind(fertilized.widthProperty());
                        animal.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                            trackedAnimal = worldMap.getTopAnimal(position);
                            drawMap(worldMap);
                            trackAnimal();
                        });
                        mapGrid.add(animal,i-bounds.lowerLeft().get_x()+1,bounds.upperRight().get_y()-j+1);
                        GridPane.setHalignment(animal, HPos.CENTER);
                    }
                } else {
                    Label label = new Label();
                    label.setText("");
                    label.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        trackedAnimal = null;
                        drawMap(worldMap);
                        animalStats.getChildren().clear();
                        trackAnimal();
                    });
                    mapGrid.add(label,i-bounds.lowerLeft().get_x()+1,bounds.upperRight().get_y()-j+1);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                mapGrid.setGridLinesVisible(true);
            }
        }
    }
    public void trackAnimal() {
        animalStats.getChildren().clear();
        if(trackedAnimal != null) {
            Label animalPosition = new Label("Position: "+trackedAnimal.getPosition().toString());
            Label animalGenotype = new Label("Genotype: "+trackedAnimal.getGenotype().toString());
            Label animalActiveGene = new Label("Active gene: "+trackedAnimal.getActiveGeneIndex());
            Label animalEnergy = new Label("Energy: "+trackedAnimal.getEnergy());
            Label animalPlantsEaten = new Label("Plants eaten: "+trackedAnimal.animalStatistics.getEatenPlantsNumber());
            Label animalChildren = new Label("Children: "+trackedAnimal.animalStatistics.getChildrenNumber());
            Label animalDescendants = new Label("Descendants: "+trackedAnimal.animalStatistics.getDescendantsNumber());
            Label animalBirthDate = new Label("Birth date: "+trackedAnimal.animalStatistics.getBirthDate());
            int daysAlive;
            if(trackedAnimal.animalStatistics.getDeathDateInt() == -1) {
                daysAlive = simulation.getDay() - trackedAnimal.animalStatistics.getBirthDate();
            } else {
                daysAlive = trackedAnimal.animalStatistics.getDeathDateInt() - trackedAnimal.animalStatistics.getBirthDate();
            }
            Label animalDaysAlive = new Label("Days alive: "+daysAlive);
            Label animalDeathDate = new Label("Death date: "+trackedAnimal.animalStatistics.getDeathDate());
            animalStats.getChildren().addAll(animalPosition, animalGenotype, animalActiveGene, animalEnergy, animalPlantsEaten, animalChildren, animalDescendants, animalBirthDate, animalDaysAlive, animalDeathDate);
            animalSelected.setText("");
        } else {
            animalSelected.setText("No animal selected");
        }
    }
    @Override
    public void mapChanged(GlobeMap map) {
        Platform.runLater(() -> {
            drawMap(worldMap);
            updateChart();
            trackAnimal();
            if(writeLog) logWriter.writeLine(this.simulation);
            day.setText(""+simulation.getDay());
            aliveAnimals.setText(""+simulation.getAnimalsCount());
            alivePlants.setText(""+worldMap.getPlantsCount());
            freeFields.setText(""+worldMap.getFreeFieldsCount());
            avgEnergy.setText(""+simulation.getAvgEnergy());
            bestGenotype1.setText(""+simulation.getBestGenotype());
            avgLifespan.setText(""+simulation.getAvgLifespan());
            avgChildren.setText(""+simulation.getAvgChildren());
        });
    }
    @FXML
    public void onPauseClicked() {
        if(pause.getText().equals("Pause simulation")) {
            highlight = true;
            drawMap(worldMap);
            simulation.pauseSimulation();
            pause.setText("Resume simulation");
        } else {
            highlight = false;
            simulation.resumeSimulation();
            pause.setText("Pause simulation");
        }
    }
    @FXML
    public void onStopClicked() {
        highlight = true;
        simulation.stopSimulation();
    }
}
