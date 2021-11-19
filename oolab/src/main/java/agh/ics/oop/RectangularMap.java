package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap implements IWorldMap {
    private final int width;
    private final int height;
    private final Vector2d lowerLeftCorner;
    private final Vector2d upperRightCorner;
    private final List<Animal> animals;

    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.lowerLeftCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.upperRightCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.animals = new ArrayList<>();
    }

    @Override
    public Vector2d upperRight() {
        return null;
    }

    @Override
    public Vector2d lowerLeft() {
        return null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeftCorner) && position.precedes(upperRightCorner) && !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        return null;
    }

    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(lowerLeftCorner, upperRightCorner);
    }
}