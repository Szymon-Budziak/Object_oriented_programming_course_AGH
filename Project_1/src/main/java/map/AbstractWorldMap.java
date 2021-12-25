package map;

import elements.Animal;
import elements.Grass;
import elements.Vector2d;
import interfaces.IMapElement;
import interfaces.IPositionChangeObserver;
import interfaces.IWorldMap;

import java.util.*;

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
    private final ArrayList<Vector2d> freeGrassPositions;

    // Constructor
    public AbstractWorldMap(int mapHeight, int mapWidth, int dailyEnergyUsage, int grassProfit, double jungleRatio) {
        this.era = 1;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.dailyEnergyUsage = dailyEnergyUsage;
        this.grassProfit = grassProfit;
        this.jungleRatio = jungleRatio;
        this.mapLowerLeft = new Vector2d(0, 0);
        this.mapUpperRight = new Vector2d(this.mapWidth - 1, this.mapHeight - 1);

        // Jungle set up
        this.jungleHeight = (int) Math.round(this.mapHeight * this.jungleRatio);
        this.jungleWidth = (int) Math.round(this.mapWidth * this.jungleRatio);
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

        this.freeGrassPositions = new ArrayList<>();
        for (int i = this.mapLowerLeft.getX(); i <= this.mapUpperRight.getX(); i++) {
            for (int j = this.mapLowerLeft.getY(); j <= this.mapUpperRight.getY(); j++) {
                this.freeGrassPositions.add(new Vector2d(i, j));
            }
        }
    }

    // AbstractWorldMap specific functions
    @Override
    public void positionChanged(IMapElement element, Vector2d oldPosition, Vector2d newPosition) {
        removeAnimalFromHashMap((Animal) element, oldPosition);
        addAnimalToHashMap((Animal) element, newPosition);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.mapLowerLeft) && position.precedes(this.mapUpperRight);
    }

    @Override
    public void place(IMapElement element) {
        Vector2d position = element.getPosition();
        if (element instanceof Animal) {
            addAnimalToHashMap((Animal) element, position);
            this.animalsList.add((Animal) element);
            ((Animal) element).addObserver(this);
        } else {
            if (this.grasses.get(position) == null) {
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

    private void removeAnimalFromHashMap(Animal animal, Vector2d position) {
        LinkedList<Animal> animalsOnPositions = this.animals.get(position);
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
        LinkedList<Animal> positions = this.animals.get(position);
        if (positions == null || positions.size() == 0) {
            return this.grasses.get(position);
        } else
            return positions.getFirst();
    }

    public Vector2d teleport(Vector2d position) {
        if (position.getX() < this.mapLowerLeft.getX()) {
            if (position.getY() < this.mapLowerLeft.getY())
                position = new Vector2d(this.mapUpperRight.getX(), this.mapUpperRight.getY());
            else if (position.getY() > this.mapUpperRight.getY())
                position = new Vector2d(this.mapUpperRight.getX(), this.mapLowerLeft.getY());
            else
                position = new Vector2d(this.mapUpperRight.getX(), position.getY());
        } else if (position.getX() > this.mapUpperRight.getX()) {
            if (position.getY() < this.mapLowerLeft.getY())
                position = new Vector2d(this.mapLowerLeft.getX(), this.mapUpperRight.getY());
            else if (position.getY() > this.mapUpperRight.getY())
                position = new Vector2d(this.mapLowerLeft.getX(), this.mapLowerLeft.getY());
            else
                position = new Vector2d(this.mapLowerLeft.getX(), position.getY());
        } else if (position.getY() > this.mapUpperRight.getY())
            position = new Vector2d(position.getX(), this.mapLowerLeft.getY());
        else if (position.getY() < this.mapLowerLeft.getY())
            position = new Vector2d(position.getX(), this.mapUpperRight.getY());
        return position;
    }

    // Map functions
    public void removeDeadAnimals() {
        for (int i = 0; i < this.animalsList.size(); i++) {
            Animal animal = this.animalsList.get(i);
            if (animal.isDead()) {
                removeAnimalFromHashMap(animal, animal.getPosition());
                animal.removeObserver(this);
                this.animalsList.remove(animal);
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
//        })
    }

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
                    animal.increaseEnergy(Math.round(this.grassProfit / animalsOnPositions.size()));
                    grassesToRemove.add(tuft);
                }
            }
        }
        for (Grass tuft : grassesToRemove) {
            this.grasses.remove(tuft.getPosition());
            this.grassList.remove(tuft);
            this.freeGrassPositions.add(tuft.getPosition());
        }
    }

    public void reproduction() {
        Animal father = null;
        Animal mother = null;
        int theStrongest;
        int secondStrongest;
        for (LinkedList<Animal> animals : this.animals.values()) {
            if (animals != null && animals.size() >= 2) {
                theStrongest = 0;
                secondStrongest = 0;
                for (Animal animal : animals) {
                    animal.getEnergy();
                    if (animal.getEnergy() > theStrongest) {
                        theStrongest = animal.getEnergy();
                        father = animal;
                    } else if (animal.getEnergy() > secondStrongest) {
                        secondStrongest = animal.getEnergy();
                        mother = animal;
                    }
                }
                if (father != null && mother != null) {
                    if (father.getEnergy() > father.getStartingEnergy() / 2 && mother.getEnergy() > mother.getStartingEnergy() / 2) {
                        Animal child = father.reproduction(mother);
                        this.place(child);
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
            if (inJungle && inStep) {
                break;
            }
            int random = (int) (Math.random() * copiedGrass.size());
            Vector2d position = copiedGrass.get(random);
            copiedGrass.remove(position);
            if (position.follows(this.jungleLowerLeft) && position.precedes(this.jungleUpperRight)) {
                if (!inJungle && !this.isOccupied(position)) {
                    Grass newGrass = new Grass(position);
                    this.grasses.put(position, newGrass);
                    inJungle = true;
                    this.freeGrassPositions.remove(position);
                }
            } else if (!inStep && !this.isOccupied(position)) {
                Grass newGrass = new Grass(position);
                this.grasses.put(position, newGrass);
                inStep = true;
                this.freeGrassPositions.remove(position);
            }
        }
    }

    public void changeEnergy() {
        this.era += 1;
        for (Animal animal : this.animalsList) {
            animal.reduceEnergy(this.dailyEnergyUsage);
        }
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
        Vector2d[] grassesAndAnimals = new Vector2d[this.animals.size() + this.grasses.size()];
        int index = 0;
        for (Vector2d key : this.grasses.keySet()) {
            grassesAndAnimals[index] = key;
            index++;
        }
        for (Vector2d key : this.animals.keySet()) {
            grassesAndAnimals[index] = key;
            index++;
        }
        return grassesAndAnimals;
    }

    public List<Animal> getAnimals() {
        return this.animalsList;
    }

    public int getEra() {
        return this.era;
    }
}
