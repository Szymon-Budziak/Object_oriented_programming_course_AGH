package agh.ics.oop;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    private final TreeSet<Vector2d> xOrder = new TreeSet<>(Comparator.comparingInt(p -> p.x));
    private final TreeSet<Vector2d> yOrder = new TreeSet<>(Comparator.comparingInt(p -> p.y));

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        xOrder.remove(oldPosition);
        xOrder.add(newPosition);
        yOrder.remove(oldPosition);
        yOrder.add(newPosition);
    }

    public void addPosition(Vector2d position) {
        xOrder.add(position);
        yOrder.add(position);
    }

    public void removePosition(Vector2d position) {
        xOrder.remove(position);
        yOrder.remove(position);
    }

    public Vector2d getUpperRight() {
        return xOrder.last().upperRight(yOrder.last());
    }

    public Vector2d getLowerLeft() {
        return xOrder.first().lowerLeft(yOrder.first());
    }
}
