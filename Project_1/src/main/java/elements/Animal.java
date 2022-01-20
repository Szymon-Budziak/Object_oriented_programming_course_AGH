package elements;

import enumClasses.MapDirection;
import enumClasses.MoveDirection;
import interfaces.IMapElement;
import interfaces.IPositionChangeObserver;
import map.AbstractWorldMap;
import map.WorldMapWithBoundaries;
import map.WorldMapWithoutBoundaries;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

public class Animal implements IMapElement {
    private final AbstractWorldMap map;
    private int energy;
    private final int startingEnergy;
    private MapDirection orientation;
    private Vector2d position;
    private final Genes genes;
    private int numberOfChildren;
    private final int bornDate;
    private int deathDate;
    private final LinkedList<Animal> children;
    private final List<IPositionChangeObserver> observers = new LinkedList<>();

    // Constructors
    public Animal(AbstractWorldMap map, int energy, int era) {
        this.map = map;
        this.energy = energy;
        this.orientation = MapDirection.NORTH;
        this.orientation = this.orientation.setRandomOrientation();
        this.startingEnergy = energy;
        this.position = new Vector2d(2, 2);
        this.position = this.position.setRandomPosition(this.map.getMapLowerLeft(), this.map.getMapUpperRight());
        this.genes = new Genes();
        this.numberOfChildren = 0;
        this.children = new LinkedList<Animal>();
        this.bornDate = era;
        this.deathDate = 0;
    }

    public Animal(AbstractWorldMap map, Vector2d position, int energy, Genes genes, int era) {
        this.map = map;
        this.position = position;
        this.orientation = MapDirection.NORTH;
        this.orientation = this.orientation.setRandomOrientation();
        this.startingEnergy = energy;
        this.energy = energy;
        this.genes = genes;
        this.numberOfChildren = 0;
        this.children = new LinkedList<Animal>();
        this.bornDate = era;
        this.deathDate = 0;
    }

    public Animal(AbstractWorldMap map, Animal otherAnimal, int energy, int era) {
        this.map = map;
        this.position = new Vector2d(2, 2);
        this.position = this.position.setRandomPosition(this.map.getMapLowerLeft(), this.map.getMapUpperRight());
        this.orientation = otherAnimal.getOrientation();
        this.startingEnergy = energy;
        this.energy = energy;
        this.genes = otherAnimal.getAnimalGenes();
        this.numberOfChildren = otherAnimal.getNumberOfChildren();
        this.children = otherAnimal.getChildren();
        this.bornDate = era;
        this.deathDate = 0;
    }

    // Getters
    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String getImagePath() {
        if (this.energy >= 0.75 * this.startingEnergy)
            return "src/main/resources/green_animal.png";
        else if (this.energy >= 0.5 * this.startingEnergy)
            return "src/main/resources/light_green_animal.png";
        else if (this.energy >= 0.25 * this.startingEnergy)
            return "src/main/resources/orange_animal.png";
        else
            return "src/main/resources/red_animal.png";
    }

    public boolean isDead() {
        return this.energy <= 0;
    }

    public int getEnergy() {
        return this.energy;
    }

    public int getStartingEnergy() {
        return this.startingEnergy;
    }

    public int getNumberOfChildren() {
        return this.numberOfChildren;
    }

    public LinkedList<Animal> getChildren() {
        return this.children;
    }

    public Genes getAnimalGenes() {
        return this.genes;
    }

    public int getAge() {
        return abs(this.deathDate - this.bornDate);
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    // Setters
    public void increaseEnergy(int energy) {
        this.energy += energy;
    }

    public void reduceEnergy(int energy) {
        this.energy -= energy;
    }

    public void setDeathDate(int era) {
        this.deathDate = era - this.bornDate;
    }

    // Animal specific functions - rotate and move
    public void rotate() {
        int numberOfRotations = this.genes.getRandomGene();
        if (numberOfRotations == 0)
            this.move(MoveDirection.FORWARD);
        else if (numberOfRotations == 4)
            this.move((MoveDirection.BACKWARD));
        else {
            for (int i = 0; i < numberOfRotations; i++) {
                this.move(MoveDirection.RIGHT);
            }
        }
    }

    private void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> this.orientation = orientation.next();
            case LEFT -> this.orientation = orientation.previous();
            case FORWARD -> {
                Vector2d newPosition = position.add(orientation.toUnitVector());
                if (this.map instanceof WorldMapWithBoundaries && this.map.canMoveTo(newPosition)) {
                    positionChanged(this, this.position, newPosition);
                    this.position = newPosition;
                } else if (this.map instanceof WorldMapWithoutBoundaries) {
                    newPosition = this.map.teleport(newPosition);
                    positionChanged(this, this.position, newPosition);
                    this.position = newPosition;
                }
            }
            case BACKWARD -> {
                Vector2d newPosition = position.subtract(orientation.toUnitVector());
                if (this.map instanceof WorldMapWithBoundaries && this.map.canMoveTo(newPosition)) {
                    positionChanged(this, this.position, newPosition);
                    this.position = newPosition;
                } else if (this.map instanceof WorldMapWithoutBoundaries) {
                    newPosition = this.map.teleport(newPosition);
                    positionChanged(this, this.position, newPosition);
                    this.position = newPosition;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    // Animal specific function - observers
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

    // Animal specific function - reproduction
    public Animal reproduction(Animal otherAnimal) {
        int childEnergy = (int) (0.25 * this.energy + 0.25 * otherAnimal.energy);
        this.energy = (int) (this.energy * 0.75);
        otherAnimal.energy = (int) (otherAnimal.energy * 0.75);
        Genes childGenes = new Genes(this.genes, otherAnimal.genes, this.energy, otherAnimal.energy);
        Animal child = new Animal(this.map, this.position, childEnergy, childGenes, this.map.getEra());
        this.numberOfChildren++;
        this.children.add(child);
        otherAnimal.numberOfChildren++;
        otherAnimal.children.add(child);
        return child;
    }
}