package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap implements IWorldMap {
    private final int width;
    private final int height;
    private final Vector2d lowerLeftCorner;
    private final Vector2d upperRightCorner;

    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.lowerLeftCorner = new Vector2d(0, 0);
        this.upperRightCorner = new Vector2d(width, height);
    }

    public Vector2d getUpperRight() {
        return upperRightCorner;
    }

    public Vector2d getLowerLeft() {
        return lowerLeftCorner;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeftCorner) && position.precedes(upperRightCorner) && super.canMoveTo(position);
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