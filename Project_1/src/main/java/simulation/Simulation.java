package simulation;

import elements.Animal;
import gui.App;
import javafx.application.Platform;
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
                      int animalsAtTheBeginning, int startingEnergy, int timeDelay, String magiSimulation) {
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
//        if (this.animalsAtTheBeginning == 5 && magiSimulation.toLowerCase().equals("yes"))
//            this.mapWithBoundaries.placeMagicAnimals(this.startingEnergy);
//            this.mapWithoutBoundaries.placeMagicAnimals(this.startingEnergy);
    }

    // Simulation specific function - run
    @Override
    public void run() {
        while (this.mapWithBoundaries.getAnimals().size() > 0 || this.mapWithoutBoundaries.getAnimals().size() > 0) {
            Platform.runLater(() -> {
                this.app.renderMap(this.mapWithBoundaries, false);
                this.app.renderMap(this.mapWithoutBoundaries, false);
            });
            try {
                Thread.sleep(this.timeDelay);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            // Remove dead animals
            this.mapWithBoundaries.removeDeadAnimals();
            this.mapWithoutBoundaries.removeDeadAnimals();

            // Turn and move
            this.mapWithBoundaries.turnAndMoveAnimals();
            this.mapWithoutBoundaries.turnAndMoveAnimals();

            // Eat
            this.mapWithBoundaries.eatingTime();
            this.mapWithoutBoundaries.eatingTime();

            // Reproduction
            this.mapWithBoundaries.reproduction();
            this.mapWithoutBoundaries.reproduction();

            // Add new grasses
            this.mapWithBoundaries.addFreshGrass();
            this.mapWithoutBoundaries.addFreshGrass();

            // Change energy
            this.mapWithBoundaries.changeEnergy();
            this.mapWithoutBoundaries.changeEnergy();
        }
    }
}