package map;

public class WorldMapWithBoundaries extends AbstractWorldMap {

    public WorldMapWithBoundaries(String mapHeight, String mapWidth, String dailyEnergyUsage,
                                  String grassProfit, String jungleRatio, String refreshTime) {
        super(Integer.parseInt(mapHeight), Integer.parseInt(mapWidth), Integer.parseInt(dailyEnergyUsage),
                Integer.parseInt(grassProfit), Double.parseDouble(jungleRatio), Integer.parseInt(refreshTime));

    }

    @Override
    public void removeDeadAnimals() {
        ;
    }

    @Override
    public void turnAndMoveAnimals() {
        ;
    }

    @Override
    public void eatingTime() {
        ;
    }

    @Override
    public void reproduction() {
        ;
    }

    @Override
    public void addFreshGras() {
        ;
    }
}
