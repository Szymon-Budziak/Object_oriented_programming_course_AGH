package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {
    @Test
    public void placeGrassTest() {
        int grass = 10;
        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        GrassField grassField = new GrassField(grass, positions);
        System.out.println(grassField.grassPoints);
        IEngine engine = new SimulationEngine(directions, grassField, positions);
        engine.run();
        for (int i = 0; i < grass; i++) {
            System.out.println(grassField.getGrassAt(i).getPosition());
        }
    }

    @Test
    public void correctMovementTest() {
        int grass = 10;
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        GrassField grassField = new GrassField(grass, positions);
        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        IEngine engine = new SimulationEngine(directions, grassField, positions);
        engine.run();
        for (int i = 0; i < grass; i++) {
            assertFalse(grassField.canMoveTo(grassField.getGrassAt(i).getPosition()));
        }
    }
}