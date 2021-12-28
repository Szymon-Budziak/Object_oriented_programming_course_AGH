package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap implements IWorldMap {
    private final Vector2d lowerLeftCorner;
    private final Vector2d upperRightCorner;
    private MapVisualizer map = new MapVisualizer(this);

    public RectangularMap(int width, int height) {
        this.lowerLeftCorner = new Vector2d(0, 0);
        this.upperRightCorner = new Vector2d(width, height);
    }

    public Vector2d getUpperRight() {
        return this.upperRightCorner;
    }

    public Vector2d getLowerLeft() {
        return this.lowerLeftCorner;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.lowerLeftCorner) && position.precedes(this.upperRightCorner) && super.canMoveTo(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        return this.animals.get(position);
    }

    public String toString() {
        return this.map.draw(this.lowerLeftCorner, this.upperRightCorner);
    }
}