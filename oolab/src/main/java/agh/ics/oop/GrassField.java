package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private final int clumpsOfGrass;
    public HashMap<Vector2d, Grass> grassPoints = new LinkedHashMap<>();
    private MapBoundary mapBoundary;

    public GrassField(int n) {
        this.clumpsOfGrass = n;
        this.mapBoundary = new MapBoundary();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int x, y;
            Vector2d position;
            do {
                x = random.nextInt((int) Math.sqrt(10 * n));
                y = random.nextInt((int) Math.sqrt(10 * n));
                position = new Vector2d(x, y);
            } while (objectAt(position) instanceof Grass);
            this.grassPoints.put(position, new Grass(position));
            mapBoundary.addPosition(position);
        }
    }

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        if (super.place(animal)) {
            mapBoundary.addPosition(animal.getPosition());
            animal.addObserver(mapBoundary);
            return true;
        } else
            throw new IllegalArgumentException(animal.getPosition() + " is not legal move specification");
    }

    @Override
    public Object objectAt(Vector2d position) {
        Animal animal = getAnimalAt(position);
        if (animal != null) {
            return animal;
        }
        Grass grass = getGrassAt(position);
        if (grass != null) {
            return grass;
        }
        return null;
    }

    private Grass getGrassAt(Vector2d position) {
        return grassPoints.get(position);
    }

    private Animal getAnimalAt(Vector2d position) {
        return animals.get(position);
    }

    public Vector2d getUpperRight() {
        return mapBoundary.getUpperRight();
    }

    public Vector2d getLowerLeft() {
        return mapBoundary.getLowerLeft();
    }

    @Override
    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(getLowerLeft(), getUpperRight());
    }
}
