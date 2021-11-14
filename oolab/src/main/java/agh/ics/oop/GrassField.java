package agh.ics.oop;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class GrassField extends AbstractWorldMap {
    private final int clumpsOfGrass;
    private final Vector2d lowerLeftCorner;
    private final Vector2d upperRightCorner;
    public List<Grass> grassPoints = new ArrayList<Grass>();
    public List<Animal> animals = new ArrayList<Animal>();

    public GrassField(int n, Vector2d[] positions) {
        this.clumpsOfGrass = n;
        this.lowerLeftCorner = new Vector2d(0, 0);
        this.upperRightCorner = new Vector2d((int) Math.sqrt(10 * n) + 1, (int) Math.sqrt(10 * n) + 1);
        Random random = new Random();
        int grassCount = 0;
        while (grassCount < this.clumpsOfGrass) {
            int x = random.nextInt((int) Math.sqrt(10 * n));
            int y = random.nextInt((int) Math.sqrt(10 * n));
            if (!isOccupied(new Vector2d(x, y))) {
                int count = 0;
                for (Vector2d position : positions) {
                    if (!Objects.equals(position, new Vector2d(x, y))) {
                        count++;
                    }
                }
                if (count == positions.length) {
                    this.grassPoints.add(new Grass(new Vector2d(x, y)));
                    grassCount++;
                }
            }
        }
    }

    public Grass getGrassAt(int index) {
        return grassPoints.get(index);
    }

    public Vector2d getUpperRight() {
        return upperRightCorner;
    }

    public Vector2d getLowerLeft() {
        return lowerLeftCorner;
    }

    @Override
    public Vector2d upperRight() {
        return null;
    }

    @Override
    public Vector2d lowerLeft() {
        return null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.x >= 0 && position.y >= 0 && !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            animals.add(animal);
            return true;
        } else {
            Object object = objectAt(animal.getPosition());
            if (object instanceof Grass) {
                for (Grass grass : grassPoints) {
                    if (grass.getPosition().x == animal.getPosition().x && grass.getPosition().y == animal.getPosition().y) {
                        grassPoints.remove(grass);
                        break;
                    }
                }
                animals.add(animal);
            } else return false;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return true;
            }
        }
        for (Grass grass : grassPoints) {
            if (grass.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        for (Grass grass : grassPoints) {
            if (grass.getPosition().equals(position)) {
                return grass;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(lowerLeftCorner, upperRightCorner);
    }
}
