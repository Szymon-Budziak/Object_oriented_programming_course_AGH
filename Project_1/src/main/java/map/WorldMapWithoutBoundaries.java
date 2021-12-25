package map;

import elements.Vector2d;

public class WorldMapWithoutBoundaries extends AbstractWorldMap {
    // Constructor
    public WorldMapWithoutBoundaries(String mapHeight, String mapWidth, String dailyEnergyUsage, String grassProfit, String jungleRatio) {
        super(Integer.parseInt(mapHeight), Integer.parseInt(mapWidth), Integer.parseInt(dailyEnergyUsage),
                Integer.parseInt(grassProfit), Double.parseDouble(jungleRatio));
    }

    // WorldMapWithoutBoundaries specific functions
    @Override
    public Vector2d teleport(Vector2d position) {
        return super.teleport(position);
    }

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

    @Override
    public Vector2d[] getAnimalsAndGrasses() {
        return super.getAnimalsAndGrasses();
    }
}
