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
        this.xOrder.add(position);
        this.yOrder.add(position);
    }

    private void removePosition(Vector2d position) {
        this.xOrder.remove(position);
        this.yOrder.remove(position);
    }

    public Vector2d getUpperRight() {
        Vector2d upper = this.yOrder.last();
        Vector2d right = this.xOrder.last();
        return upper.upperRight(right);
    }

    public Vector2d getLowerLeft() {
        Vector2d lower = this.yOrder.first();
        Vector2d left = this.xOrder.first();
        return lower.lowerLeft(left);
    }
}