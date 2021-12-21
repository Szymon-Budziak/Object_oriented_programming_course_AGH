package elements;

import interfaces.IMapElement;

public class Grass implements IMapElement {
    private final Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    // Getters
    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String getImagePath() {
        return "Project_1/src/main/resources/palm.png";
    }

    // toString
    @Override
    public String toString() {
        return "*";
    }
}
