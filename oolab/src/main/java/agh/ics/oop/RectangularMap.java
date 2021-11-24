package agh.ics.oop;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class RectangularMap extends AbstractWorldMap implements IWorldMap {
    private final int width;
    private final int height;
    private final Vector2d lowerLeftCorner;
    private final Vector2d upperRightCorner;
    private HashMap<Vector2d, Animal> animals = new LinkedHashMap<>();

    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.lowerLeftCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.upperRightCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.animals = new LinkedHashMap<>();
    }

    @Override
    public Vector2d upperRight() {
        return upperRightCorner;
    }

    @Override
    public Vector2d lowerLeft() {
        return lowerLeftCorner;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeftCorner) && position.precedes(upperRightCorner) && !isOccupied(position);
    }


    @Override
    public Object objectAt(Vector2d position) {
        return animals.get(position);
    }

    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(lowerLeftCorner, upperRightCorner);
    }
}