package agh.ics.oop;

import agh.ics.oop.gui.SimulationApp;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {

        System.out.print("system wystartował \n");

        Application.launch(SimulationApp.class);

        System.out.println("System zakończył działanie.");
        System.exit(0);
    }
}
