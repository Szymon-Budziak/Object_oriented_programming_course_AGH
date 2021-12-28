package simulation;

import elements.Animal;
import gui.App;
import javafx.application.Platform;
import map.AbstractWorldMap;

public class Simulation implements Runnable {
    private final App app;
    private final int animalsAtTheBeginning;
    private final int startingEnergy;
    private final int timeDelay;
    private AbstractWorldMap map;
    private int era;

    // Constructor
    public Simulation(AbstractWorldMap map, App app, int animalsAtTheBeginning, int startingEnergy, int timeDelay, String magiSimulation) {
        this.map = map;
        this.app = app;
        this.animalsAtTheBeginning = animalsAtTheBeginning;
        this.startingEnergy = startingEnergy;
        this.timeDelay = timeDelay;
        this.era = this.map.getEra();
        int i = 0;
        while (i < this.animalsAtTheBeginning) {
            Animal animal = new Animal(this.map, this.startingEnergy, this.era);
            if (!this.map.isOccupied(animal.getPosition())) {
                this.map.place(animal);
                i++;
            }
        }
        if (this.animalsAtTheBeginning == 5 && magiSimulation.toLowerCase().equals("yes")) {
            this.map.placeMagicAnimals(this.startingEnergy);
        }
    }

    // Simulation specific function - run
    @Override
    public void run() {
        while (this.map.getAnimals().size() > 0) {
            Platform.runLater(() -> {
                this.app.renderMap(this.map, false);
            });
            try {
                Thread.sleep(this.timeDelay);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            // Remove dead animals
            this.map.removeDeadAnimals();

            // Turn and move
            this.map.turnAndMoveAnimals();

            // Eat
            this.map.eatingTime();

            // Reproduction
            this.map.reproduction();

            // Add new grasses
            this.map.addFreshGrass();

            // Change energy
            this.map.changeEnergy();
        }
    }
}