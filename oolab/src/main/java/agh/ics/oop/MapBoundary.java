package agh.ics.oop;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    private final TreeSet<Vector2d> xOrder = new TreeSet<>(Comparator.comparingInt(p -> p.x));
    private final TreeSet<Vector2d> yOrder = new TreeSet<>(Comparator.comparingInt(p -> p.y));

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        removePosition(oldPosition);
        addPosition(newPosition);
    }

    public void addPosition(Vector2d position) {
        xOrder.add(position);
        yOrder.add(position);
    }

    private void removePosition(Vector2d position) {
        xOrder.remove(position);
        yOrder.remove(position);
    }

    public Vector2d getUpperRight() {
        Vector2d upper = yOrder.last();
        Vector2d right = xOrder.last();
        return upper.upperRight(right);
    }

    public Vector2d getLowerLeft() {
        Vector2d lower = yOrder.first();
        Vector2d left = xOrder.first();
        return lower.lowerLeft(left);
    }
}
