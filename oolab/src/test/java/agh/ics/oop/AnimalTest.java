package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//public class AnimalTest {
//    Animal animal1 = new Animal();
//
//    @Test
//    public void orientationTest() {
//        animal1.move(MoveDirection.RIGHT);
//        assertEquals(MapDirection.EAST, animal1.getOrientation());
//        animal1.move(MoveDirection.FORWARD);
//        animal1.move(MoveDirection.LEFT);
//        assertEquals(MapDirection.NORTH, animal1.getOrientation());
//        animal1.move(MoveDirection.LEFT);
//        animal1.move(MoveDirection.LEFT);
//        assertEquals(MapDirection.SOUTH, animal1.getOrientation());
//        animal1.move(MoveDirection.RIGHT);
//        animal1.move(MoveDirection.BACKWARD);
//        assertEquals(MapDirection.WEST, animal1.getOrientation());
//    }
//
//    Animal animal2 = new Animal();
//
//    @Test
//    public void positionTest() {
//        animal2.move(MoveDirection.FORWARD);
//        assertEquals(new Vector2d(2, 3), animal2.getPosition());
//        animal2.move(MoveDirection.LEFT);
//        animal2.move(MoveDirection.BACKWARD);
//        assertEquals(new Vector2d(3, 3), animal2.getPosition());
//        animal2.move(MoveDirection.LEFT);
//        animal2.move(MoveDirection.FORWARD);
//        animal2.move(MoveDirection.FORWARD);
//        assertEquals(new Vector2d(3, 1), animal2.getPosition());
//        animal2.move(MoveDirection.RIGHT);
//        animal2.move(MoveDirection.FORWARD);
//        animal2.move(MoveDirection.FORWARD);
//        assertEquals(new Vector2d(1, 1), animal2.getPosition());
//    }
//
//    Animal animal3 = new Animal();
//    Animal animal4 = new Animal();
//
//    @Test
//    public void outsideTheMapTest() {
//        animal3.move(MoveDirection.FORWARD);
//        animal3.move(MoveDirection.FORWARD);
//        animal3.move(MoveDirection.FORWARD);
//        assertEquals(new Vector2d(2, 5), animal3.getPosition());
//        animal3.move(MoveDirection.RIGHT);
//        animal3.move(MoveDirection.FORWARD);
//        animal3.move(MoveDirection.FORWARD);
//        animal3.move(MoveDirection.FORWARD);
//        assertEquals(new Vector2d(5, 5), animal3.getPosition());
//        animal4.move(MoveDirection.BACKWARD);
//        animal4.move(MoveDirection.BACKWARD);
//        animal4.move(MoveDirection.BACKWARD);
//        assertEquals(new Vector2d(2, 0), animal4.getPosition());
//        animal4.move(MoveDirection.LEFT);
//        animal4.move(MoveDirection.BACKWARD);
//        animal4.move(MoveDirection.BACKWARD);
//        animal4.move(MoveDirection.BACKWARD);
//        assertEquals(new Vector2d(5, 0), animal4.getPosition());
//    }
//
//    public boolean searchForValue(String input) {
//        switch (input) {
//            case "f", "forward", "b", "backward", "r", "right", "l", "left" -> {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Test
//    public void correctInputTest() {
//        String[] correctDirections = {"f", "forward", "r", "l", "backward"};
//        String[] wrongDirections = {"a", "back", "turn right", "le", "front"};
//        for (String s : correctDirections) {
//            assertTrue(searchForValue(s));
//        }
//        for (String s : wrongDirections) {
//            assertFalse(searchForValue(s));
//        }
//    }
//
//    @Test
//    public void correctMovementTest() {
//        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
//        IWorldMap map = new RectangularMap(10, 5);
//        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
//        SimulationEngine engine = new SimulationEngine(directions, map, positions);
//        engine.run();
//        assertTrue(engine.getAnimal(0).isAt(new Vector2d(3, 0)));
//        assertFalse(engine.getAnimal(0).isAt(new Vector2d(4, 0)));
//        assertTrue(engine.getAnimal(1).isAt(new Vector2d(2, 5)));
//        assertFalse(engine.getAnimal(1).isAt(new Vector2d(0, 2)));
//    }
//}