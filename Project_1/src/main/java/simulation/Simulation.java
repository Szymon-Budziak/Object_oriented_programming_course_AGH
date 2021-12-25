package simulation;

import elements.Animal;
import gui.App;
import javafx.application.Platform;
import map.AbstractWorldMap;
import map.WorldMapWithBoundaries;
import map.WorldMapWithoutBoundaries;

public class Simulation implements Runnable {
    private final App app;
    private final WorldMapWithBoundaries mapWithBoundaries;
    private final WorldMapWithoutBoundaries mapWithoutBoundaries;
    private final int animalsAtTheBeginning;
    private final int startingEnergy;
    private final int timeDelay;
    private int mapWithBoundariesEra;
    private int mapWithoutBoundariesEra;

    // Constructor
    public Simulation(WorldMapWithBoundaries mapWithBoundaries, WorldMapWithoutBoundaries mapWithoutBoundaries, App app,
                      int animalsAtTheBeginning, int startingEnergy, int timeDelay) {
        this.mapWithBoundaries = mapWithBoundaries;
        this.mapWithoutBoundaries = mapWithoutBoundaries;
        this.app = app;
        this.animalsAtTheBeginning = animalsAtTheBeginning;
        this.startingEnergy = startingEnergy;
        this.timeDelay = timeDelay;
        this.mapWithBoundariesEra = this.mapWithBoundaries.getEra();
        this.mapWithoutBoundariesEra = this.mapWithoutBoundaries.getEra();
        int i = 0;
        while (i < this.animalsAtTheBeginning) {
            Animal animalWithBoundaries = new Animal(this.mapWithBoundaries, this.startingEnergy, this.mapWithBoundariesEra);
            if (!this.mapWithBoundaries.isOccupied(animalWithBoundaries.getPosition())) {
                this.mapWithBoundaries.place(animalWithBoundaries);
                i++;
            }
        }
        i = 0;
        while (i < this.animalsAtTheBeginning) {
            Animal animalWithoutBoundaries = new Animal(this.mapWithoutBoundaries, this.startingEnergy, this.mapWithoutBoundariesEra);
            if (!this.mapWithoutBoundaries.isOccupied(animalWithoutBoundaries.getPosition())) {
                this.mapWithoutBoundaries.place(animalWithoutBoundaries);
                i++;
            }
        }
    }

    // Simulation specific function - run
    @Override
    public void run() {
        while (this.mapWithBoundaries.getAnimals().size() > 0 || this.mapWithoutBoundaries.getAnimals().size() > 0) {
            try {
                Thread.sleep(this.timeDelay);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            // Map With Boundaries
            if (this.mapWithBoundaries.getAnimals().size() > 0) {
                fullDayOfAnimalsLive(this.mapWithBoundaries);
            } else
                Platform.runLater(() -> {
                    this.app.renderMap(this.mapWithBoundaries, true);
                });
            if (this.mapWithoutBoundaries.getAnimals().size() > 0) {
                fullDayOfAnimalsLive(this.mapWithoutBoundaries);
            } else
                Platform.runLater(() -> {
                    this.app.renderMap(this.mapWithoutBoundaries, true);
                });
        }
    }

    private void fullDayOfAnimalsLive(AbstractWorldMap map) {
        Platform.runLater(() -> {
            this.app.renderMap(map, false);
        });
        // Remove dead animals
        map.removeDeadAnimals();

        // Turn and move
        map.turnAndMoveAnimals();

        // Eat
        map.eatingTime();

        // Reproduction
        map.reproduction();

        // Add new grasses
        map.addFreshGrass();

        // Change energy
        map.changeEnergy();
    }
}