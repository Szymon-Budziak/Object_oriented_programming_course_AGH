package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        try {
            MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
            IWorldMap map = new GrassField(10);
            IEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
            System.out.println(map.toString());
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}