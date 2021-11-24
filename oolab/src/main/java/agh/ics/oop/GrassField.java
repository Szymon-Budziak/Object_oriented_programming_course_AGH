package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private final int clumpsOfGrass;
    private Vector2d lowerLeftCorner;
    private Vector2d upperRightCorner;
    public HashMap<Vector2d, Grass> grassPoints = new LinkedHashMap<>();
    public HashMap<Vector2d, Animal> animals = new LinkedHashMap<>();

    public GrassField(int n) {
        this.clumpsOfGrass = n;
        this.lowerLeftCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.upperRightCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Random random = new Random();
        while (grassPoints.size() < this.clumpsOfGrass) {
            int x = random.nextInt((int) Math.sqrt(10 * n));
            int y = random.nextInt((int) Math.sqrt(10 * n));
            Vector2d position = new Vector2d(x, y);
            if (getGrassAt(position) != null) {
                continue;
            }
            this.grassPoints.put(position, new Grass(position));
        }
    }

    private Grass getGrassAt(Vector2d position) {
        return grassPoints.get(position);
    }

    private Animal getAnimalAt(Vector2d position) {
        return animals.get(position);
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
        return !isOccupied(position) || objectAt(position) instanceof Grass;
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
    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(lowerLeftCorner, upperRightCorner);
    }
}
