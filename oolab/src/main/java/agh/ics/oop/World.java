package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        Application.launch(App.class, args);
//        try {
//            MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
//            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
//            IWorldMap map = new GrassField(2);
//            IEngine engine = new SimulationEngine(directions, map, positions);
//            engine.run();
//            System.out.println(map.toString());
//        } catch (IllegalArgumentException exception) {
//            System.out.println(exception.getMessage());
//        }
    }
}