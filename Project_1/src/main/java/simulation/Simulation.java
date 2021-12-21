package simulation;

import elements.Animal;
import gui.StartingApp;
import javafx.application.Platform;
import map.WorldMapWithBoundaries;
import map.WorldMapWithoutBoundaries;

public class Simulation implements Runnable {
    private WorldMapWithBoundaries mapWithBoundaries;
    private WorldMapWithoutBoundaries mapWithoutBoundaries;
    private StartingApp app;
    private int animalsAtTheBeginningTextField;
    private int startingEnergy;
    private int timeDelay;

    // Constructor
    public Simulation(WorldMapWithBoundaries mapWithBoundaries, WorldMapWithoutBoundaries mapWithoutBoundaries, StartingApp app,
                      int animalsAtTheBeginningTextField, int startingEnergy, int timeDelay) {
        this.mapWithBoundaries = mapWithBoundaries;
        this.mapWithoutBoundaries = mapWithoutBoundaries;
        this.app = app;
        this.animalsAtTheBeginningTextField = animalsAtTheBeginningTextField;
        this.startingEnergy = startingEnergy;
        this.timeDelay = timeDelay;
        for (int i = 0; i < this.animalsAtTheBeginningTextField; i++) {
            Animal animalOnBoundaries = new Animal(this.mapWithBoundaries, this.startingEnergy);
            this.mapWithBoundaries.place(animalOnBoundaries);

            Animal animalWithoutBoundaries = new Animal(this.mapWithoutBoundaries, this.startingEnergy);
            this.mapWithoutBoundaries.place(animalWithoutBoundaries);
        }
    }

    // run
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(this.timeDelay);
            } catch (InterruptedException exception) {
                System.out.println(exception.getMessage());
            }
            // Remove dead animals
            this.mapWithBoundaries.removeDeadAnimals();

            // Turn and move
            this.mapWithBoundaries.turnAndMoveAnimals();

            // Eat
            this.mapWithBoundaries.eatingTime();

            // Reproduction
            this.mapWithBoundaries.reproduction();

            // Add new grass elements
            this.mapWithBoundaries.addFreshGras();

            // Create new map
            Platform.runLater(() -> {
                this.app.renderMap(this.mapWithBoundaries);
            });
        }
    }
}
