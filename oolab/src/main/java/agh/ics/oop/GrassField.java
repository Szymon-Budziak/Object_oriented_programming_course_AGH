package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private HashMap<Vector2d, Grass> grassPoints = new LinkedHashMap<>();
    MapVisualizer map = new MapVisualizer(this);
    private final MapBoundary mapBoundary;

    public GrassField(int n) {
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
    public boolean canMoveTo(Vector2d position) {
        if (this.isOccupied(position)) {
            return (objectAt(position) instanceof Grass);
        }
        return true;
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

    @Override
    public Vector2d[] getAnimalsAndGrass() {
        Vector2d[] onlyAnimals = super.getAnimalsAndGrass();
        Vector2d[] grassAndAnimals = new Vector2d[grassPoints.size() + onlyAnimals.length];
        int index = 0;
        for (Vector2d key : grassPoints.keySet()) {
            grassAndAnimals[index] = key;
            index++;
        }
        for (int i = 0; i < onlyAnimals.length; i++) {
            grassAndAnimals[grassPoints.size() + i] = onlyAnimals[i];
        }
        return grassAndAnimals;
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
        return map.draw(getLowerLeft(), getUpperRight());
    }
}
