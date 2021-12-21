package simulation;

import elements.Animal;
import gui.StartingApp;
import javafx.application.Platform;
import map.AbstractWorldMap;
import map.WorldMapWithBoundaries;

public class Simulation implements Runnable {
//    private WorldMapWithBoundaries mapWithBoundaries;
    AbstractWorldMap map;
    private StartingApp app;
    private int animalsAtTheBeginning;
    private int startingEnergy;
    private int timeDelay;

    // Constructor
    public Simulation(AbstractWorldMap map, StartingApp app,
                      int animalsAtTheBeginning, int startingEnergy, int timeDelay) {
        this.map = map;
        this.app = app;
        this.animalsAtTheBeginning = animalsAtTheBeginning;
        this.startingEnergy = startingEnergy;
        this.timeDelay = timeDelay;
        int mapWithBoundariesEra = this.map.getEra();
        for (int i = 0; i < this.animalsAtTheBeginning; i++) {
            Animal animalWithBoundaries = new Animal(this.map, this.startingEnergy, mapWithBoundariesEra);
            this.map.place(animalWithBoundaries);
        }
    }

    // run
    @Override
    public void run() {
        while (true) {
            Platform.runLater(() -> {
                this.app.renderMap(map);
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

            // Add new grass elements
            this.map.addFreshGrass();

            // Change energy
//        this.mapWithBoundaries.changeEnergy();

        }
    }
}
