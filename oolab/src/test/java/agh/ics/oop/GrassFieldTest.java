package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//public class GrassFieldTest {
//    @Test
//    public void grassFieldTest() {
//        int grass = 10;
//        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "l"});
//        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
//        IWorldMap grassField = new GrassField(grass);
//        IEngine engine = new SimulationEngine(directions, grassField, positions);
//        engine.run();
//        assertTrue(grassField.canMoveTo(new Vector2d(10, 12)));
//        assertTrue(grassField.canMoveTo(new Vector2d(6, 12)));
//        assertTrue(grassField.canMoveTo(new Vector2d(40, 17)));
//        assertTrue(grassField.canMoveTo(new Vector2d(100, 180)));
//    }
//}