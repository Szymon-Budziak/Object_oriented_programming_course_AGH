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
    int moveDelay = 1000;

    public SimulationEngine(MoveDirection[] directions, GrassField map, Vector2d[] positions, App app) {
        this.map = map;
        this.directions = List.of(directions);
        this.animals = new ArrayList<>();
        this.app = app;
        for (Vector2d position : positions) {
            Animal animal = new Animal(map, position);
            animals.add(animal);
            map.place(animal);
        }
    }

    public Animal getAnimal(int i) {
        return animals.get(i);
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            app.renderMap(map);
        });
        try {
            Thread.sleep(moveDelay);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < directions.size(); i++) {
            Animal currentAnimal = animals.get(i % animals.size());
            currentAnimal.move(directions.get(i));
            Platform.runLater(() -> {
                app.renderMap(map);
            });
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
