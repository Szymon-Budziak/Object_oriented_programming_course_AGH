package elements;

import interfaces.IMapElement;

public class Grass implements IMapElement {
    private final Vector2d position;

    // Constructor
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
        return "Project_1/src/main/resources/grass.png";
    }
}
