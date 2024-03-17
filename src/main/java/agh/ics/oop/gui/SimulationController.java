package agh.ics.oop.gui;

import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;



public class SimulationController {
    private int counter = 1;
    private int mutationType; // 0 - pełna losowość , 1 - podmianka
    private int plantsType; // 0 - zalesione równiki, 1 - życiodajne truchła
    private int fertileRadius = 0;
    private int fertilizationTime = 0;
    private boolean logWriting = false;
    @FXML
    private TextField numberOfAnimals;
    @FXML
    private TextField heightMap;
    @FXML
    private TextField widthMap;
    @FXML
    private TextField energyForPlant;
    @FXML
    private TextField animalStartEnergy;
    @FXML
    private TextField genomeLength;
    @FXML
    private TextField everyDayGrowing;
    @FXML
    private TextField simulationSpeed;
    @FXML
    private TextField reproductionEnergy;
    @FXML
    private TextField energyToBreed;
    @FXML
    private TextField minMutation;
    @FXML
    private TextField maxMutation;
    @FXML
    private TextField moveEnergy;
    @FXML
    private VBox textData;
    @FXML
    private Button equator;
    @FXML
    private Button corpses;
    @FXML
    private Button randomness;
    @FXML
    private Button replacement;
    @FXML
    private Button saveToCSV;
    @FXML
    private TextField configName;
    @FXML
    private Button saveButton;
    @FXML
    private VBox fertilizationOptions;
    @FXML
    public TextField numberOfPlants;
    @FXML
    private ComboBox<SimulationConfiguration> configurations;

    public void initialize() throws IOException{
        initializeConfigurations();
        configurations.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                numberOfAnimals.setText(String.valueOf(newValue.getNumberOfAnimals()));
                heightMap.setText(String.valueOf(newValue.getMapHeight()));
                widthMap.setText(String.valueOf(newValue.getMapWidth()));
                energyForPlant.setText(String.valueOf(newValue.getEnergyPerPlantEaten()));
                animalStartEnergy.setText(String.valueOf(newValue.getAnimalStartingEnergy()));
                genomeLength.setText(String.valueOf(newValue.getAnimalGenomeLength()));
                reproductionEnergy.setText(String.valueOf(newValue.getReproductionEnergy()));
                energyToBreed.setText(String.valueOf(newValue.getWellFedAnimal()));
                everyDayGrowing.setText(String.valueOf(newValue.getPlantsGrowingPerDay()));
                simulationSpeed.setText(String.valueOf(newValue.getSpeedOfSimulation()));
                numberOfPlants.setText(String.valueOf(newValue.getPlantsNumber()));
                minMutation.setText(String.valueOf(newValue.getMinMutation()));
                maxMutation.setText(String.valueOf(newValue.getMaxMutation()));
                moveEnergy.setText(String.valueOf(newValue.getMoveEnergy()));
                fertilizationTime = newValue.getFertilizationTime();
                fertileRadius = newValue.getFertileRadius();
                if(newValue.getMutationType() == 0) {
                    mutationType = 0;
                    randomness.setStyle("-fx-background-color: #00ff00");
                    replacement.setStyle(null);
                } else {
                    mutationType = 1;
                    replacement.setStyle("-fx-background-color: #00ff00");
                    randomness.setStyle(null);
                }
                if(newValue.getPlantsType() == 0) {
                    plantsType = 0;
                    equator.setStyle("-fx-background-color: #00ff00");
                    corpses.setStyle(null);
                    setFertilizationOptionsVisible(false);
                } else {
                    plantsType = 1;
                    corpses.setStyle("-fx-background-color: #00ff00");
                    equator.setStyle(null);
                    setFertilizationOptionsVisible(true);
                }
            }
        });
        // dodanie podpisów po najechaniu na przyciski
        Tooltip tooltip = new Tooltip("Mutation changes a gene to any other gene.");
        tooltip.setShowDelay(javafx.util.Duration.millis(100));
        Tooltip.install(randomness, tooltip);
        tooltip = new Tooltip("Places of two genes are swapped.");
        tooltip.setShowDelay(javafx.util.Duration.millis(100));
        Tooltip.install(replacement, tooltip);
        tooltip = new Tooltip("Plants prefer to grow in fields where an animal has recently died.");
        tooltip.setShowDelay(javafx.util.Duration.millis(100));
        Tooltip.install(corpses, tooltip);
        tooltip = new Tooltip("Plants prefer to grow in fields closer to the equator.");
        tooltip.setShowDelay(javafx.util.Duration.millis(100));
        Tooltip.install(equator, tooltip);
        // ustawienie parametrów w zależności od przycisku
        randomness.setOnAction(event -> {
            mutationType = 0;
            randomness.setStyle("-fx-background-color: #00ff00");
            replacement.setStyle(null);
        });
        replacement.setOnAction(event -> {
            mutationType = 1;
            replacement.setStyle("-fx-background-color: #00ff00");
            randomness.setStyle(null);
        });
        corpses.setOnAction(event -> {
            plantsType = 1;
            corpses.setStyle("-fx-background-color: #00ff00");
            equator.setStyle(null);
            setFertilizationOptionsVisible(true);
        });
        equator.setOnAction(event -> {
            plantsType = 0;
            equator.setStyle("-fx-background-color: #00ff00");
            corpses.setStyle(null);
            setFertilizationOptionsVisible(false);
        });
        saveToCSV.setOnAction(event -> {
           if(!logWriting) {
               logWriting = true;
               saveToCSV.setText("ON");
               saveToCSV.setStyle("-fx-background-color: #00ff00");
           } else {
               logWriting = false;
               saveToCSV.setText("OFF");
               saveToCSV.setStyle(null);
           }
        });
    }
    public void setFertilizationOptionsVisible(boolean visible) {
        if(visible) {
            fertilizationOptions.getChildren().clear();
            ToolBar radiusOptions = new ToolBar();
            ToolBar timeOptions = new ToolBar();
            Label fertileRadiusLabel = new Label("Fertile radius: " + fertileRadius + "    ");
            Slider fertileRadiusSlider = new Slider(0, 5, fertileRadius);
            fertileRadiusSlider.setShowTickLabels(true);
            fertileRadiusSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                fertileRadiusSlider.setValue(newValue.intValue());
                fertileRadius = newValue.intValue();
                fertileRadiusLabel.setText("Fertile radius:   " + newValue.intValue() + "    ");
            });
            Label fertilizationTimeLabel = new Label("Fertilization time in days: " + fertilizationTime + "    ");
            Slider fertilizationTimeSlider = new Slider(0, 20, fertilizationTime);
            fertilizationTimeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                fertilizationTimeSlider.setValue(newValue.intValue());
                fertilizationTime = newValue.intValue();
                fertilizationTimeLabel.setText("Fertilization time in days:   " + newValue.intValue() + "    ");
            });
            fertilizationTimeSlider.setShowTickLabels(true);
            radiusOptions.getItems().addAll(fertileRadiusLabel, fertileRadiusSlider);
            timeOptions.getItems().addAll(fertilizationTimeLabel, fertilizationTimeSlider);
            fertilizationOptions.getChildren().addAll(radiusOptions, timeOptions);
        } else {
            fertilizationOptions.getChildren().clear();
        }
    }
    public void initializeConfigurations() throws IOException {
        ObservableList<SimulationConfiguration> configurationList = FXCollections.observableArrayList(DefaultConfigurations.getConfigurations());
        this.configurations.setItems(configurationList);
    }
    @FXML
    public void onSimulationStartClicked() {
        if(verifyData()) {
            try {
                SimulationApp app = new SimulationApp();
                SimulationConfiguration activeConfiguration = new SimulationConfiguration.Builder()
                        .setMutationType(mutationType)
                        .setPlantsType(plantsType)
                        .setAnimalNumber(Integer.parseInt(numberOfAnimals.getText()))
                        .setAnimalStartingEnergy(Integer.parseInt(animalStartEnergy.getText()))
                        .setAnimalGenomeLength(Integer.parseInt(genomeLength.getText()))
                        .setMapWidth(Integer.parseInt(widthMap.getText()))
                        .setMapHeight(Integer.parseInt(heightMap.getText()))
                        .setSpeedOfSimulation(Integer.parseInt(simulationSpeed.getText()))
                        .setPlantsNumber(Integer.parseInt(numberOfPlants.getText()))
                        .setPlantsGrowingPerDay(Integer.parseInt(everyDayGrowing.getText()))
                        .setEnergyPerPlantEaten(Integer.parseInt(energyForPlant.getText()))
                        .setWellFedAnimal(Integer.parseInt(energyToBreed.getText()))
                        .setReproductionEnergy(Integer.parseInt(reproductionEnergy.getText()))
                        .setMinMutation(Integer.parseInt(minMutation.getText()))
                        .setMaxMutation(Integer.parseInt(maxMutation.getText()))
                        .setMoveEnergy(Integer.parseInt(moveEnergy.getText()))
                        .setFertileRadius(fertileRadius)
                        .setFertilizationTime(fertilizationTime)
                        .setLogWriting(logWriting)
                        .build();
                app.initializeSimulation(activeConfiguration, counter);
                counter++;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    @FXML
    public void onSaveClicked() throws IOException{
        if(verifyData()) {
            SimulationConfiguration activeConfiguration = new SimulationConfiguration.Builder()
                    .setConfigName(configName.getText())
                    .setMutationType(mutationType)
                    .setPlantsType(plantsType)
                    .setAnimalNumber(Integer.parseInt(numberOfAnimals.getText()))
                    .setAnimalStartingEnergy(Integer.parseInt(animalStartEnergy.getText()))
                    .setAnimalGenomeLength(Integer.parseInt(genomeLength.getText()))
                    .setMapWidth(Integer.parseInt(widthMap.getText()))
                    .setMapHeight(Integer.parseInt(heightMap.getText()))
                    .setSpeedOfSimulation(Integer.parseInt(simulationSpeed.getText()))
                    .setPlantsNumber(Integer.parseInt(numberOfPlants.getText()))
                    .setPlantsGrowingPerDay(Integer.parseInt(everyDayGrowing.getText()))
                    .setEnergyPerPlantEaten(Integer.parseInt(energyForPlant.getText()))
                    .setWellFedAnimal(Integer.parseInt(energyToBreed.getText()))
                    .setReproductionEnergy(Integer.parseInt(reproductionEnergy.getText()))
                    .setMinMutation(Integer.parseInt(minMutation.getText()))
                    .setMaxMutation(Integer.parseInt(maxMutation.getText()))
                    .setFertileRadius(fertileRadius)
                    .setFertilizationTime(fertilizationTime)
                    .setMoveEnergy(Integer.parseInt(moveEnergy.getText()))
                    .build();
            try(Writer writer = new FileWriter("src/main/resources/configurations.csv",true)) {
                CSVWriter csvWriter = new CSVWriter(writer);
                String[] data = activeConfiguration.getData();
                csvWriter.writeNext(data);
                csvWriter.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            initializeConfigurations();
            textData.getChildren().forEach(node -> {
                if(node instanceof ToolBar) {
                    ((ToolBar) node).getItems().forEach(item -> {
                        if(item instanceof TextField) {
                            ((TextField) item).setText("");
                        }
                    });
                }
            });
            corpses.setStyle(null);
            equator.setStyle(null);
            randomness.setStyle(null);
            replacement.setStyle(null);
            setFertilizationOptionsVisible(false);
            configName.setText("Enter title");
            saveButton.setText("Saved!");
        }
    }
    private boolean verifyData() {
        StringBuilder alertMessage = new StringBuilder();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wrong data!");
        alert.setHeaderText("You need to change shown fields to continue:");
        if (numberOfAnimals.getText().isEmpty()) {
            alertMessage.append("Number of starting animals can't be empty \n");
        } else if (Integer.parseInt(numberOfAnimals.getText()) < 1 || Integer.parseInt(numberOfAnimals.getText()) > 10000) {
            alertMessage.append("Number of starting animals must be between 1 and 10000 \n");
        } else if (Integer.parseInt(numberOfAnimals.getText()) > Integer.parseInt(widthMap.getText()) * Integer.parseInt(heightMap.getText())) {
            alertMessage.append("Number of starting animals can't be greater than number of fields on map \n");
        }
        if (heightMap.getText().isEmpty()) {
            alertMessage.append("Map height can't be empty \n");
        } else if (Integer.parseInt(heightMap.getText()) < 2 || Integer.parseInt(heightMap.getText()) > 100) {
            alertMessage.append("Map height must be between 2 and 100 \n");
        }
        if (widthMap.getText().isEmpty()) {
            alertMessage.append("Map width can't be empty \n");
        } else if (Integer.parseInt(widthMap.getText()) < 2 || Integer.parseInt(widthMap.getText()) > 100) {
            alertMessage.append("Map width must be between 2 and 100 \n");
        }
        if (numberOfPlants.getText().isEmpty()) {
            alertMessage.append("Number of starting plants can't be empty \n");
        } else if (Integer.parseInt(numberOfPlants.getText()) > Integer.parseInt(widthMap.getText()) * Integer.parseInt(heightMap.getText())) {
            alertMessage.append("Number of starting plants can't be greater than number of fields on map \n");
        } else if (Integer.parseInt(numberOfPlants.getText()) < 1 || Integer.parseInt(numberOfPlants.getText()) > 10000) {
            alertMessage.append("Number of starting plants must be between 1 and 10000 \n");
        }
        if (energyForPlant.getText().isEmpty()) {
            alertMessage.append("Energy restored per plant eaten can't be empty \n");
        } else if (Integer.parseInt(energyForPlant.getText()) < 1 || Integer.parseInt(energyForPlant.getText()) > 1000) {
            alertMessage.append("Energy restored per plant eaten must be between 1 and 1000 \n");
        }
        if (everyDayGrowing.getText().isEmpty()) {
            alertMessage.append("Number of plants growing everyday can't be empty \n");
        } else if (Integer.parseInt(everyDayGrowing.getText()) > Integer.parseInt(widthMap.getText()) * Integer.parseInt(heightMap.getText())) {
            alertMessage.append("Number of plants growing everyday can't be greater than number of fields on map \n");
        } else if (Integer.parseInt(everyDayGrowing.getText()) < 1 || Integer.parseInt(everyDayGrowing.getText()) > 10000) {
            alertMessage.append("Number of plants growing everyday must be between 1 and 10000 \n");
        }
        if (animalStartEnergy.getText().isEmpty()) {
            alertMessage.append("Animal starting energy can't be empty \n");
        } else if (Integer.parseInt(animalStartEnergy.getText()) < 2 || Integer.parseInt(animalStartEnergy.getText()) > 1000) {
            alertMessage.append("Animal starting energy must be between 2 and 1000 \n");
        }
        if (moveEnergy.getText().isEmpty()) {
            alertMessage.append("Energy used per move can't be empty \n");
        } else if (Integer.parseInt(moveEnergy.getText()) < 0 || Integer.parseInt(moveEnergy.getText()) > 1000) {
            alertMessage.append("Energy used per move must be between 0 and 1000 \n");
        }
        if (reproductionEnergy.getText().isEmpty()) {
            alertMessage.append("Energy used in reproduction can't be empty \n");
        } else if (Integer.parseInt(reproductionEnergy.getText()) < 1 || Integer.parseInt(reproductionEnergy.getText()) > 1000) {
            alertMessage.append("Energy used in reproduction must be between 1 and 1000 \n");
        }
        if (energyToBreed.getText().isEmpty()) {
            alertMessage.append("Energy needed to breed can't be empty \n");
        } else if (Integer.parseInt(energyToBreed.getText()) < Integer.parseInt(reproductionEnergy.getText())) {
            alertMessage.append("Energy needed to breed can't be lower than energy used in reproduction \n");
        } else if (Integer.parseInt(energyToBreed.getText()) < 1 || Integer.parseInt(energyToBreed.getText()) > 1000) {
            alertMessage.append("Energy needed to breed must be between 1 and 1000 \n");
        }
        if (genomeLength.getText().isEmpty()) {
            alertMessage.append("Genome length can't be empty \n");
        } else if (Integer.parseInt(genomeLength.getText()) < 1 || Integer.parseInt(genomeLength.getText()) > 100) {
            alertMessage.append("Genome length must be between 1 and 100 \n");
        }
        if (simulationSpeed.getText().isEmpty()) {
            alertMessage.append("Simulation speed can't be empty \n");
        } else if (Integer.parseInt(simulationSpeed.getText()) < 1 || Integer.parseInt(simulationSpeed.getText()) > 1000) {
            alertMessage.append("Simulation speed must be between 1 and 10000 \n");
        }
        if (minMutation.getText().isEmpty()) {
            alertMessage.append("Minimum number of mutations can't be empty \n");
        } else if (Integer.parseInt(minMutation.getText()) > Integer.parseInt(genomeLength.getText())) {
            alertMessage.append("Minimum number of mutations can't be greater than genome length \n");
        } else if (Integer.parseInt(minMutation.getText()) < 0 || Integer.parseInt(minMutation.getText()) > 100) {
            alertMessage.append("Minimum number of mutations must be between 0 and 100 \n");
        }
        if (maxMutation.getText().isEmpty()) {
            alertMessage.append("Maximum number of mutations can't be empty \n");
        } else if (Integer.parseInt(maxMutation.getText()) > Integer.parseInt(genomeLength.getText())) {
            alertMessage.append("Maximum number of mutations can't be greater than genome length \n");
        } else if(Integer.parseInt(maxMutation.getText()) < Integer.parseInt(minMutation.getText())) {
            alertMessage.append("Maximum number of mutations can't be lower than minimum number of mutations \n");
        } else if(Integer.parseInt(maxMutation.getText()) < 0 || Integer.parseInt(maxMutation.getText()) > 100) {
            alertMessage.append("Maximum number of mutations must be between 0 and 100 \n");
        }
        alert.setContentText(alertMessage.toString());
        if(alertMessage.isEmpty()) {
            return true;
        }
        alert.showAndWait();
        return false;
    }
}
