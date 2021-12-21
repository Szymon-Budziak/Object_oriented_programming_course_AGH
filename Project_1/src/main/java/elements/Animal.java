package elements;

import enumClasses.MapDirection;
import enumClasses.MoveDirection;
import interfaces.IMapElement;
import interfaces.IPositionChangeObserver;
import interfaces.IWorldMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Animal implements IMapElement {
    private int energy;
    private MapDirection orientation;
    private Vector2d position;
    private Genes genes;
    private final IWorldMap map;
    private final int startingEnergy;
    private final List<IPositionChangeObserver> observers = new LinkedList<>();
    private ArrayList<Animal> children;

    // Constructor
    public Animal(IWorldMap map, int energy) {
        this.map = map;
        this.startingEnergy = energy;
    }

    // Getters
    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }

    public int getEnergy() {
        return this.energy;
    }

    public Genes getGenes() {
        return this.genes;
    }

    @Override
    public String getImagePath() {
        return "Project_1/src/main/resources/animal.png";
    }

    public boolean isDead() {
        return this.energy <= 0;
    }

    // Move
    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> this.orientation = orientation.next();
            case LEFT -> this.orientation = orientation.previous();
            case FORWARD -> {
                Vector2d newPosition = position.add(orientation.toUnitVector());
                if (this.map.canMoveTo(newPosition)) {
                    positionChanged(this.position, newPosition);
                    this.position = newPosition;
                }
            }
            case BACKWARD -> {
                Vector2d newPosition = position.subtract(orientation.toUnitVector());
                if (this.map.canMoveTo(newPosition)) {
                    positionChanged(this.position, newPosition);
                    this.position = newPosition;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    // Observers
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    // Copulation
    public Animal copulation(Animal otherAnimal) {
        int childEnergy = (int) (0.25 * this.energy + 0.25 * otherAnimal.energy);
        this.energy = (int) (this.energy * 0.75);
        otherAnimal.energy = (int) (otherAnimal.energy * 0.75);
        Animal child = new Animal(this.map, this.position, childEnergy);
        child.genes = new Genes(this.genes, otherAnimal.genes, this.energy, otherAnimal.energy);
        this.children.add(child);
        otherAnimal.children.add(child);
        return child;
    }

    //toString


}
