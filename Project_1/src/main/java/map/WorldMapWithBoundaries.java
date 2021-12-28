package map;

import elements.Animal;
import elements.Grass;
import elements.Vector2d;

import java.util.List;

public class WorldMapWithBoundaries extends AbstractWorldMap {
    // Constructor
    public WorldMapWithBoundaries(String mapHeight, String mapWidth, String dailyEnergyUsage, String grassProfit, String jungleRatio) {
        super(Integer.parseInt(mapHeight), Integer.parseInt(mapWidth), Integer.parseInt(dailyEnergyUsage),
                Integer.parseInt(grassProfit), Double.parseDouble(jungleRatio));
    }

    // WorldMapWithBoundaries specific functions
    @Override
    public void removeDeadAnimals() {
        super.removeDeadAnimals();
    }

    @Override
    public void turnAndMoveAnimals() {
        super.turnAndMoveAnimals();
    }

    @Override
    public void eatingTime() {
        super.eatingTime();
    }

    @Override
    public void reproduction() {
        super.reproduction();
    }

    @Override
    public void addFreshGrass() {
        super.addFreshGrass();
    }

    @Override
    public void changeEnergy() {
        super.changeEnergy();
    }

    // Getters
    @Override
    public Vector2d getMapLowerLeft() {
        return super.getMapLowerLeft();
    }

    @Override
    public Vector2d getMapUpperRight() {
        return super.getMapUpperRight();
    }

    @Override
    public Vector2d getJungleLowerLeft() {
        return super.getJungleLowerLeft();
    }

    @Override
    public Vector2d getJungleUpperRight() {
        return super.getJungleUpperRight();
    }

    // Getters for plot
    @Override
    public int getEra() {
        return super.getEra();
    }

    @Override
    public List<Animal> getAnimals() {
        return super.getAnimals();
    }

    @Override
    public List<Grass> getGrass() {
        return super.getGrass();
    }

    @Override
    public int[] getDominantGenotype() {
        return super.getDominantGenotype();
    }

    @Override
    public double getAverageAnimalsEnergy() {
        return super.getAverageAnimalsEnergy();
    }

    @Override
    public double getAverageDeadAnimalsLife() {
        return super.getAverageDeadAnimalsLife();
    }

    @Override
    public double getAverageNumberOfChildren() {
        return super.getAverageNumberOfChildren();
    }

    @Override
    public List getAnimalInfo(Animal animal) {
        return super.getAnimalInfo(animal);
    }

    @Override
    public void placeMagicAnimals(int energy) {
        super.placeMagicAnimals(energy);
    }
}