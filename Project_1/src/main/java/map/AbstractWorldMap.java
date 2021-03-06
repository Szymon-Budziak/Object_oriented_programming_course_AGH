package map;

import elements.Animal;
import elements.Genes;
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
    protected HashMap<Animal, Integer> deadAnimals = new LinkedHashMap<>();
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
        int jungleHeight = (int) Math.round(this.mapHeight * this.jungleRatio);
        int jungleWidth = (int) Math.round(this.mapWidth * this.jungleRatio);
        int jungleLowerLeftX = 0;
        int jungleLowerLeftY = 0;
        int jungleUpperRightX = this.mapWidth - 1;
        int jungleUpperRightY = this.mapHeight - 1;

        for (int i = 0; i < (this.mapWidth - jungleWidth); i++) {
            if (i % 2 == 0)
                jungleLowerLeftX++;
            else
                jungleUpperRightX--;
        }
        for (int i = 0; i < (this.mapHeight - jungleHeight); i++) {
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

    // AbstractWorldMap specific functions: positionChanged, canMoveTo, place, addAnimalToHashMap, addAnimalToHashMap,
    //                                      removeAnimalFromHashMap, isOccupied, objectAt
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

    // WorldMapWithoutBoundaries specific function - teleport
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

    // Function for magic simulation
    public void placeMagicAnimals(int energy) {
        int i = 0;
        while (i < 5) {
            Animal otherAnimal = this.animalsList.get(i);
            Animal newAnimal = new Animal(this, otherAnimal, energy, this.era);
            if (!this.isOccupied(newAnimal.getPosition())) {
                this.place(newAnimal);
                i++;
            }
        }
    }

    // Simulation specific functions: removeDeadAnimals, turnAndMoveAnimals, eatingTime, reproduction, addFreshGrass, changeEnergy
    public void removeDeadAnimals() {
        for (int i = 0; i < this.animalsList.size(); i++) {
            Animal animal = this.animalsList.get(i);
            if (animal.isDead()) {
                removeAnimalFromHashMap(animal, animal.getPosition());
                animal.removeObserver(this);
                this.animalsList.remove(animal);
                animal.setDeathDate(this.era);
            }
        }
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

    // Getters for plot
    public int getEra() {
        return this.era;
    }

    public List<Animal> getAnimals() {
        LinkedList<Animal> animalsList = new LinkedList<>();
        for (LinkedList<Animal> animals : this.animals.values()) {
            animalsList.addAll(animals);
        }
        return animalsList;
    }

    public List<Grass> getGrass() {
        LinkedList<Grass> grassesList = new LinkedList<>();
        grassesList.addAll(this.grasses.values());
        return grassesList;
    }

    public Genes getDominantGenotype() {
        HashMap<Genes, Integer> dominantGenotypeCounter = new HashMap<>();
        Genes mostDominantGenotype = null;
        int mostDominantCount = 0;
        int count;
        for (Animal animal : this.animalsList) {
            Genes animalGenotype = animal.getAnimalGenes();
            if (dominantGenotypeCounter.containsKey(animalGenotype)) {
                count = dominantGenotypeCounter.remove(animalGenotype);
                dominantGenotypeCounter.put(animalGenotype, count + 1);
                if (count + 1 > mostDominantCount) {
                    mostDominantGenotype = animalGenotype;
                    mostDominantCount = count + 1;
                }
            } else {
                dominantGenotypeCounter.put(animalGenotype, 1);
                if (mostDominantGenotype == null) {
                    mostDominantGenotype = animalGenotype;
                    mostDominantCount = 1;
                }
            }
        }
        return mostDominantGenotype;
    }

    public double getAverageAnimalsEnergy() {
        double count = 0;
        double numberOfAnimals = 0;
        for (LinkedList<Animal> animals : this.animals.values()) {
            for (Animal animal : animals) {
                count += animal.getEnergy();
                numberOfAnimals++;
            }
        }
        return count / numberOfAnimals;
    }

    public double getAverageDeadAnimalsLife() {
        if (this.deadAnimals == null || this.deadAnimals.size() == 0)
            return 0;
        else {
            double count = 0;
            for (int age : this.deadAnimals.values()) {
                count += age;
            }
            return count / this.deadAnimals.size();
        }
    }

    public double getAverageNumberOfChildren() {
        double count = 0;
        double numberOfAnimals = 0;
        for (LinkedList<Animal> animals : this.animals.values()) {
            for (Animal animal : animals) {
                count += animal.getNumberOfChildren();
                numberOfAnimals++;
            }
        }
        return count / numberOfAnimals;
    }
}