package map;

public class WorldMapWithoutBoundaries extends AbstractWorldMap {
    public WorldMapWithoutBoundaries(String mapHeight, String mapWidth,String dailyEnergyUsage,
                                     String grassProfit, String jungleRatio, String refreshTime) {
        super(Integer.parseInt(mapHeight), Integer.parseInt(mapWidth),Integer.parseInt(dailyEnergyUsage),
                Integer.parseInt(grassProfit), Double.parseDouble(jungleRatio), Integer.parseInt(refreshTime));
    }
}
