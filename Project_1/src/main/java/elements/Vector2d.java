package elements;

import java.util.Objects;
import java.util.Random;

import static java.lang.Math.abs;

public class Vector2d {
    public final int x;
    public final int y;

    // Constructor
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Setters
    public Vector2d setRandomPosition(Vector2d mapLowerLeft, Vector2d mapUpperRight) {
        Random random = new Random();
        int totalHeight = abs(mapUpperRight.y - mapLowerLeft.y);
        int totalWidth = abs(mapUpperRight.x - mapLowerLeft.x);
        int randomY = random.nextInt(totalWidth);
        int randomX = random.nextInt(totalHeight);
        return new Vector2d(randomX, randomY);
    }

    // Functions
    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Vector2d) {
            return this.x == ((Vector2d) other).x && this.y == ((Vector2d) other).y;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    // toString
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
