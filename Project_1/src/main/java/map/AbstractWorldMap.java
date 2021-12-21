package map;

import elements.Animal;
import elements.Grass;
import elements.Vector2d;
import interfaces.IPositionChangeObserver;
import interfaces.IWorldMap;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected int day;
    protected final int mapHeight;
    protected final int mapWidth;
    protected final int dailyEnergyUsage;
    protected final int grassProfit;
    protected final double jungleRatio;
    protected final int refreshTime;
    protected HashMap<Vector2d, Animal> animals = new LinkedHashMap<>();
    protected HashMap<Vector2d, Grass> grass = new LinkedHashMap<>();
    protected HashMap<Vector2d, Animal> deadAnimals = new LinkedHashMap<>();
    private int jungleHeight;
    private int jungleWidth;
    private final Vector2d mapLowerLeft;
    private final Vector2d jungleLowerLeft;
    private final Vector2d mapUpperRight;
    private final Vector2d jungleUpperRight;

    // Constructor
    public AbstractWorldMap(int mapHeight, int mapWidth, int dailyEnergyUsage, int grassProfit,
                            double jungleRatio, int refreshTime) {
        this.day = 0;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.dailyEnergyUsage = dailyEnergyUsage;
        this.grassProfit = grassProfit;
        this.jungleRatio = jungleRatio;
        this.refreshTime = refreshTime;
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

    }

    @Override
    public boolean place(Animal animal) {
        if (!this.canMoveTo(animal.getPosition())) {
            throw new IllegalArgumentException(animal.getPosition() + " is not legal move specification");
        }
        animals.put(animal.getPosition(), animal);
        animal.addObserver(this);
        return true;
    }

    protected void removeDeadAnimals() {

    }

    protected void turnAndMoveAnimals() {
    }

    protected void eatingTime() {

    }
    protected void reproduction(){

    }
    protected void addFreshGras(){

    }


    // TODO: Functions to implement
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }
}
