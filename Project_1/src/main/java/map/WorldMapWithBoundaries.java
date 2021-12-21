package map;

import elements.Vector2d;

public class WorldMapWithBoundaries extends AbstractWorldMap {

    public WorldMapWithBoundaries(String mapHeight, String mapWidth, String dailyEnergyUsage,
                                  String grassProfit, String jungleRatio) {
        super(Integer.parseInt(mapHeight), Integer.parseInt(mapWidth), Integer.parseInt(dailyEnergyUsage),
                Integer.parseInt(grassProfit), Double.parseDouble(jungleRatio));

    }

    @Override
    public void removeDeadAnimals() {
        super.removeDeadAnimals();
    }

    @Override
    public void turnAndMoveAnimals() {

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
