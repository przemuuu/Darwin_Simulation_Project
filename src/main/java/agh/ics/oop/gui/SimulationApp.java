package agh.ics.oop.gui;

import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.model.maps.GlobeMap;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;


public class SimulationApp extends Application {
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("controller.fxml"));
        BorderPane viewRoot = loader.load();
        configureStage(primaryStage, viewRoot, 0);
        primaryStage.show();
    }
    private void configureStage(Stage primaryStage, BorderPane viewRoot, int counter) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        if(counter == 0) {
            primaryStage.setTitle("Simulation controller");
        } else {
            primaryStage.setTitle("Simulation " + counter);
        }
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
    public void initializeSimulation(SimulationConfiguration configuration, int counter) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter newPresenter = loader.getController();
        Stage newStage = new Stage();
        newStage.setOnCloseRequest(event -> newPresenter.forceStop());
        configureStage(newStage, viewRoot, counter);
        GlobeMap newMap = new GlobeMap(configuration);
        newMap.setObserver(newPresenter);
        Simulation simulation = new Simulation(configuration, newMap);
        newPresenter.setData(simulation, newMap);
        if(configuration.getLogWriting()) newPresenter.useLogWriter();
        newStage.show();
    }
}
