package map;

import elements.Animal;
import elements.Grass;
import elements.Vector2d;
import interfaces.IMapElement;
import interfaces.IPositionChangeObserver;
import interfaces.IWorldMap;

import java.util.*;

import static java.lang.Math.abs;

public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected int era;
    protected final int mapHeight;
    protected final int mapWidth;
    protected final int dailyEnergyUsage;
    protected final int grassProfit;
    protected final double jungleRatio;
    protected HashMap<Vector2d, LinkedList<Animal>> animals = new LinkedHashMap<>();
    protected HashMap<Vector2d, Grass> grasses = new LinkedHashMap<>();
    protected List<Animal> animalsList = new LinkedList<>();
    protected List<Grass> grassList = new LinkedList<>();
    protected HashMap<Animal, Integer> deadAnimals = new LinkedHashMap<>();
    private int jungleHeight;
    private int jungleWidth;
    private final Vector2d mapLowerLeft;
    private final Vector2d jungleLowerLeft;
    private final Vector2d mapUpperRight;
    private final Vector2d jungleUpperRight;
    private final ArrayList<Vector2d> freeAnimalPositions;
    private final ArrayList<Vector2d> freeGrassPositions;

    // Constructor
    public AbstractWorldMap(int mapHeight, int mapWidth, int dailyEnergyUsage, int grassProfit,
                            double jungleRatio) {
        this.era = 1;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.dailyEnergyUsage = dailyEnergyUsage;
        this.grassProfit = grassProfit;
        this.jungleRatio = jungleRatio;
        this.mapLowerLeft = new Vector2d(0, 0);
        this.mapUpperRight = new Vector2d(this.mapWidth - 1, this.mapHeight - 1);

        // Jungle set up
        this.jungleHeight = (int) (this.mapHeight * this.jungleRatio);
        this.jungleWidth = (int) (this.mapWidth * this.jungleRatio);
        int jungleLowerLeftX = 0;
        int jungleLowerLeftY = 0;
        int jungleUpperRightX = this.mapWidth - 1;
        int jungleUpperRightY = this.mapHeight - 1;

        for (int i = 0; i < (this.mapWidth - this.jungleWidth); i++) {
            if (i % 2 == 0)
                jungleLowerLeftX++;
            else
                jungleUpperRightX--;
        }
        for (int i = 0; i < (this.mapHeight - this.jungleHeight); i++) {
            if (i % 2 == 0)
                jungleLowerLeftY++;
            else
                jungleUpperRightY--;
        }
        this.jungleLowerLeft = new Vector2d(jungleLowerLeftX, jungleLowerLeftY);
        this.jungleUpperRight = new Vector2d(jungleUpperRightX, jungleUpperRightY);

        this.freeAnimalPositions = new ArrayList<>();
        this.freeGrassPositions = new ArrayList<>();
        for (int i = this.mapLowerLeft.x; i < this.mapUpperRight.x; i++) {
            for (int j = this.mapLowerLeft.y; i < this.mapUpperRight.y; i++) {
                this.freeAnimalPositions.add(new Vector2d(i, j));
                this.freeGrassPositions.add(new Vector2d(i, j));
            }
        }


    }

    @Override
    public void positionChanged(IMapElement element, Vector2d oldPosition, Vector2d newPosition) {
        removeAnimalFromHashMap((Animal) element, oldPosition);
        addAnimalToHashMap((Animal) element, newPosition);
    }

    // TODO canMoveTo to be like can outside bounds
    @Override
    public boolean canMoveTo(Vector2d position) {
//        Vector2d newPosition = convertToMapWithoutBoundaries(position);
//        LinkedList<Animal> animalsOnPositions = this.animals.get(position);
//        if (animalsOnPositions == null) return true;
//
//        return true;
        ;
        return true;
    }

    @Override
    public void place(IMapElement element) {
        Vector2d position = convertToMapWithoutBoundaries(element.getPosition());
        if (element instanceof Animal) {
            addAnimalToHashMap((Animal) element, position);
            this.animalsList.add((Animal) element);
            ((Animal) element).addObserver(this);
        } else {
            if (grasses.get(position) == null) {
                this.grasses.put(position, (Grass) element);
            }
            this.grassList.add((Grass) element);
        }
    }

    private void addAnimalToHashMap(Animal animal, Vector2d position) {
        LinkedList<Animal> animalsOnPositions = this.animals.get(position);
        if (animalsOnPositions == null) {
            LinkedList<Animal> newList = new LinkedList<>();
            newList.add(animal);
            this.animals.put(position, newList);
        } else {
            animalsOnPositions.add(animal);
        }
    }

    public void removeAnimalFromHashMap(Animal animal, Vector2d position) {
        LinkedList<Animal> animalsOnPositions = animals.get(position);
        if (animalsOnPositions != null) {
            animalsOnPositions.remove(animal);
            if (animalsOnPositions.size() == 0)
                this.animals.remove(position);
        } else
            throw new IllegalArgumentException(position + " is invalid.");
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        Vector2d newPosition = convertToMapWithoutBoundaries(position);
        LinkedList<Animal> positions = this.animals.get(newPosition);
        if (positions == null || positions.size() == 0) {
            return this.grasses.get(position);
        } else
            return positions.getFirst();
    }

    private Vector2d convertToMapWithoutBoundaries(Vector2d oldPosition) {
        int newY;
        int newX;
        if (oldPosition.y < this.mapLowerLeft.y) {
            newY = (this.mapHeight - abs(oldPosition.y % this.mapHeight)) % this.mapHeight;
        } else {
            newY = abs(oldPosition.y % this.mapHeight);
        }
        if (oldPosition.x < this.mapLowerLeft.x) {
            newX = (this.mapWidth - abs(oldPosition.x % this.mapWidth)) % this.mapWidth;
        } else {
            newX = abs(oldPosition.x % this.mapWidth);
        }
        return new Vector2d(newX, newY);
    }

    // Map functions
    public void removeDeadAnimals() {
        this.era += 1;
        for (int i = 0; i < this.animalsList.size(); i++) {
            Animal animal = this.animalsList.get(i);
            if (animal.isDead()) {
                removeAnimalFromHashMap(animal, animal.getPosition());
                animal.removeObserver(this);
                animalsList.remove(animal);
            }
        }
//        Map<Vector2d, LinkedList<Animal>> animalsToRemove = new HashMap<>();
//        this.animals.forEach((key, value) -> {
//            for (Animal animal : value) {
//                if (animal.isDead()) {
//                    animal.setDeathDate(this.era);
//                    this.deadAnimals.put(animal, this.era - animal.getBornDate());
//                    if (animalsToRemove.containsKey(key)) {
//                        animalsToRemove.get(key).add(animal);
//                    } else {
//                        LinkedList<Animal> newList = new LinkedList<>();
//                        newList.add(animal);
//                        animalsToRemove.put(key, newList);
//                    }
//                }
//            }
//        });
//        animalsToRemove.forEach((key, value) -> {
//            for (Animal animal : value) {
//                this.animals.get(key).remove(animal);
//            }
//        });
    }

    // TODO Change animalsList to hashmap
    public void turnAndMoveAnimals() {
        for (Animal animal : this.animalsList) {
            animal.rotate();
        }
    }

    public void eatingTime() {
        List<Grass> grassesToRemove = new LinkedList<>();
        for (Grass tuft : this.grasses.values()) {
            LinkedList<Animal> animalsOnPositions = this.animals.get(tuft.getPosition());
            if (animalsOnPositions != null && animalsOnPositions.size() > 0) {
                for (Animal animal : animalsOnPositions) {
                    animal.setEnergy(this.grassProfit / animalsOnPositions.size());
                    grassesToRemove.add(tuft);
                }
            }
        }
        for (Grass tuft : grassesToRemove) {
            this.grasses.remove(tuft.getPosition());
            this.grassList.remove(tuft);
        }
    }

    public void reproduction() {
        Animal father = null;
        Animal mother = null;
        int theStrongest = 0;
        int secondStrongest = 0;
        for (LinkedList<Animal> animals : this.animals.values()) {
            if (animals != null && animals.size() >= 2) {
                for (Animal animal : animals) {
                    if (animal.getEnergy() > theStrongest) {
                        theStrongest = animal.getEnergy();
                        father = animal;
                    } else if (animal.getEnergy() > secondStrongest) {
                        secondStrongest = animal.getEnergy();
                        mother = animal;
                    }
                }
                if (father != null && mother != null) {
                    if (father.getEnergy() > father.getStartingEnergy() && mother.getEnergy() > mother.getStartingEnergy()) {
                        Animal child = father.reproduction(mother);
                        place(child);
                    }

                }
            }

        }
    }

    public void addFreshGrass() {
        boolean inJungle = false;
        boolean inStep = false;
        ArrayList<Vector2d> copiedGrass = (ArrayList<Vector2d>) this.freeGrassPositions.clone();
        while (copiedGrass.size() > 0) {
            int random = (int) (Math.random() * copiedGrass.size());
            Vector2d position = copiedGrass.get(random);
            copiedGrass.remove(position);
            if (position.follows(this.jungleLowerLeft) && position.precedes(this.jungleUpperRight)) {
                if (!inJungle) {
                    Grass newGrass = new Grass(position);
                    this.grasses.put(position, newGrass);
                    inJungle = true;
                    this.freeGrassPositions.remove(position);
                }
            } else if (!inStep) {
                Grass newGrass = new Grass(position);
                this.grasses.put(position, newGrass);
                inStep = true;
                this.freeGrassPositions.remove(position);
            }

        }

    }

    protected void changeEnergy() {
        ;
    }

    // Getters
    public Vector2d getMapLowerLeft() {
        return this.mapLowerLeft;
    }

    public Vector2d getMapUpperRight() {
        return this.mapUpperRight;
    }

    public Vector2d getJungleLowerLeft() {
        return this.jungleLowerLeft;
    }

    public Vector2d getJungleUpperRight() {
        return this.jungleUpperRight;
    }

    public Vector2d[] getAnimalsAndGrasses() {
        Vector2d[] animalsAndGrasses = new Vector2d[this.animals.size() + this.grasses.size()];
        int index = 0;
        for (Vector2d key : this.animals.keySet()) {
            animalsAndGrasses[index] = key;
            index++;
        }
        for (Vector2d key : this.grasses.keySet()) {
            animalsAndGrasses[index] = key;
            index++;
        }
        return animalsAndGrasses;
    }

    public int getEra() {
        return this.era;
    }
}
