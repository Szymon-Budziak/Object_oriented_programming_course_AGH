package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable {
    private final GrassField map;
    private final List<MoveDirection> directions;
    private final List<Animal> animals;
    private final App app;
    private int moveDelay = 1000;

    public SimulationEngine(MoveDirection[] directions, GrassField map, Vector2d[] positions, App app, MapDirection orientation) {
        this.map = map;
        this.directions = List.of(directions);
        this.animals = new ArrayList<>();
        this.app = app;
        for (Vector2d position : positions) {
            Animal animal = new Animal(map, position, orientation);
            this.animals.add(animal);
            map.place(animal);
        }
    }

    public Animal getAnimal(int i) {
        return this.animals.get(i);
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            this.app.renderMap(this.map);
        });
        try {
            Thread.sleep(this.moveDelay);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < this.directions.size(); i++) {
            Animal currentAnimal = this.animals.get(i % this.animals.size());
            currentAnimal.move(this.directions.get(i));
            Platform.runLater(() -> {
                this.app.renderMap(this.map);
            });
            try {
                Thread.sleep(this.moveDelay);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
