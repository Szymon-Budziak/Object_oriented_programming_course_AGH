package agh.ics.oop;

public class Animal {
    private MapDirection orientation;
    private Vector2d position;
    private IWorldMap map;


    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
        this.orientation = MapDirection.NORTH;
    }

    public Animal(IWorldMap map) {
        this(map, new Vector2d(2, 2));
    }

    public Animal() {
        this(new RectangularMap(5, 5));
    }


    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public String toString() {
        return switch (orientation) {
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }

    public void move(MoveDirection direction) {
        Vector2d new_position = position;
        switch (direction) {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> {
                if (position.precedes(new Vector2d(4, 4)) && position.follows(new Vector2d(0, 0))) {
                    new_position = position.add(orientation.toUnitVector());
                }
            }
            case BACKWARD -> {
                if (position.precedes(new Vector2d(4, 4)) && position.follows(new Vector2d(0, 0))) {
                    new_position = position.subtract(orientation.toUnitVector());
                }
            }
        }
        if (new_position.x < 5 && new_position.x > -1 && new_position.y < 5 && new_position.y > -1) {
            position = new_position;
        }
    }
}
