package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

public class Animal implements IMapElement {
    private MapDirection orientation;
    private Vector2d position;
    private final IWorldMap map;
    private final List<IPositionChangeObserver> observers = new LinkedList<>();

    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection orientation) {
        this.map = map;
        this.position = initialPosition;
        this.orientation = orientation;
    }

    public Animal(IWorldMap map) {
        this(map, new Vector2d(2, 2), MapDirection.NORTH);
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String getImagePath() {
        return switch (this.getOrientation()) {
            case NORTH -> "oolab/src/main/resources/north.png";
            case EAST -> "oolab/src/main/resources/east.png";
            case SOUTH -> "oolab/src/main/resources/south.png";
            case WEST -> "oolab/src/main/resources/west.png";
        };
    }

    public void move(MoveDirection direction) {
        Vector2d newPosition;
        switch (direction) {
            case RIGHT -> this.orientation = orientation.next();
            case LEFT -> this.orientation = orientation.previous();
            case FORWARD -> {
                newPosition = position.add(orientation.toUnitVector());
                if (this.map.canMoveTo(newPosition)) {
                    positionChanged(this.position, newPosition);
                    this.position = newPosition;
                }
            }
            case BACKWARD -> {
                newPosition = position.subtract(orientation.toUnitVector());
                if (this.map.canMoveTo(newPosition)) {
                    positionChanged(this.position, newPosition);
                    this.position = newPosition;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    public String toString() {
        return switch (orientation) {
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }
}