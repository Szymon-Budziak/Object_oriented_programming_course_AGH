package elements;

import enumClasses.MapDirection;
import enumClasses.MoveDirection;
import interfaces.IMapElement;
import interfaces.IPositionChangeObserver;
import javafx.scene.paint.Color;
import map.AbstractWorldMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Animal implements IMapElement {
    private final AbstractWorldMap map;
    private int energy;
    private int startingEnergy;
    private MapDirection orientation;
    private Vector2d position;
    private Genes genes;
    private ArrayList<Animal> children;
    private int bornDate;
    private final List<IPositionChangeObserver> observers = new LinkedList<>();
    private int deathDate;
    private int age;

    // Constructor
    public Animal(AbstractWorldMap map, int energy, int era) {
        this.map = map;
        this.energy = energy;
        this.orientation = MapDirection.NORTH;
        this.orientation = this.orientation.setRandomOrientation();
        this.startingEnergy = energy;
        this.position = new Vector2d(2, 2);
        this.position = this.position.setRandomPosition(this.map.getMapLowerLeft(), this.map.getMapUpperRight());
        this.genes = new Genes();
        this.children = new ArrayList<>();
        this.bornDate = era;
        this.age = 0;
    }

    public Animal(AbstractWorldMap map, Vector2d position, int energy, Genes genes) {
        this.map = map;
        this.position = position;
        this.orientation = MapDirection.NORTH;
        this.orientation = this.orientation.setRandomOrientation();
        this.startingEnergy = energy;
        this.energy = energy;
        this.genes = genes;
        this.age = 0;
        this.children = new ArrayList<>();

    }

    // Getters
    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String getImagePath() {
        return "Project_1/src/main/resources/animal.png";
    }

    public boolean isDead() {
        return this.energy <= 0;
    }

    public int getBornDate() {
        return this.bornDate;
    }

    public int getEnergy() {
        return this.energy;
    }
    public int getStartingEnergy(){
        return this.startingEnergy;
    }

    // Setters
    public void setDeathDate(int deathDate) {
        this.deathDate = deathDate;
    }

    public void setEnergy(int energy) {
        this.energy += energy;
    }

    // Move
    public void move(MoveDirection direction) {
        this.age += 1;
        switch (direction) {
            case RIGHT -> this.orientation = orientation.next();
            case LEFT -> this.orientation = orientation.previous();
            case FORWARD -> {
                Vector2d newPosition = position.add(orientation.toUnitVector());
                if (this.map.canMoveTo(newPosition)) {
                    positionChanged(this, this.position, newPosition);
                    this.position = newPosition;
                }
            }
            case BACKWARD -> {
                Vector2d newPosition = position.subtract(orientation.toUnitVector());
                if (this.map.canMoveTo(newPosition)) {
                    positionChanged(this, this.position, newPosition);
                    this.position = newPosition;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public void rotate() {
        int numberOfRotations = this.genes.getRandomGene();
        for (int i = 0; i < numberOfRotations; i++) {
            this.move(MoveDirection.RIGHT);
        }
    }

    // Observers
    private void positionChanged(IMapElement element, Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(element, oldPosition, newPosition);
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    // Reproduction
    public Animal reproduction(Animal otherAnimal) {
        int childEnergy = (int) (0.25 * this.energy + 0.25 * otherAnimal.energy);
        this.energy = (int) (this.energy * 0.75);
        otherAnimal.energy = (int) (otherAnimal.energy * 0.75);
        Genes childGenes = new Genes(this.genes, otherAnimal.genes, this.energy, otherAnimal.energy);
        Animal child = new Animal(this.map, this.position, childEnergy, childGenes);
        this.children.add(child);
        otherAnimal.children.add(child);
        return child;
    }

    public Color energyInColor() {
        if (this.energy <= 0) return new Color(204, 0, 0, 1);
        else if (this.energy < 0.35 * this.startingEnergy) return new Color(255, 51, 51, 1);
        else if (this.energy < 0.70 * this.startingEnergy) return new Color(105, 255, 102, 1);
        else return new Color(0, 204, 0, 1);

    }

    //toString
    @Override
    public String toString() {
        return "Animal{" +
                "map=" + map +
                ", energy=" + energy +
                ", orientation=" + orientation +
                ", position=" + position +
                ", genes=" + genes +
                ", children=" + children +
                ", bornDate=" + bornDate +
                ", observers=" + observers +
                ", deathDate=" + deathDate +
                ", age=" + age +
                '}';
    }
}
