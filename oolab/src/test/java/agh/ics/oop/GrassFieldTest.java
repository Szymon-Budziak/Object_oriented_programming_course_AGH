package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {
    @Test
    public void grassFieldTest() {
        int grass = 10;
        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "l"});
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IWorldMap grassField = new GrassField(grass);
        IEngine engine = new SimulationEngine(directions, grassField, positions);
        engine.run();
        Random random = new Random();
        for (int i = 0; i < grass; i++) {
            int x = random.nextInt((int) Math.sqrt(10 * grass));
            int y = random.nextInt((int) Math.sqrt(10 * grass));
            assertTrue(grassField.canMoveTo(new Vector2d(x, y)));
        }
    }
}