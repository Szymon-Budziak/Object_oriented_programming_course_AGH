package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    Animal animalOrientation = new Animal();

    @Test
    public void orientationTest() {
        animalOrientation.move(MoveDirection.RIGHT);
        assertEquals("Animal position is: Wschód and (2, 2)", animalOrientation.toString());
        animalOrientation.move(MoveDirection.FORWARD);
        assertEquals("Animal position is: Wschód and (3, 2)", animalOrientation.toString());
        animalOrientation.move(MoveDirection.LEFT);
        assertNotEquals("Animal position is: Zachód and (4, 2)", animalOrientation.toString());
        animalOrientation.move(MoveDirection.LEFT);
        animalOrientation.move(MoveDirection.BACKWARD);
        assertNotEquals("Animal position is: Północ and (1, 1)", animalOrientation.toString());
    }

    Animal animalPosition = new Animal();

    @Test
    public void positionTest() {
        animalPosition.move(MoveDirection.LEFT);
        assertEquals("Animal position is: Zachód and (2, 2)", animalPosition.toString());
        animalPosition.move(MoveDirection.BACKWARD);
        animalPosition.move(MoveDirection.BACKWARD);
        assertEquals("Animal position is: Zachód and (4, 2)", animalPosition.toString());
        animalPosition.move(MoveDirection.BACKWARD);
        assertNotEquals("Animal position is: Zachód and (5, 2)", animalPosition.toString());
        animalPosition.move(MoveDirection.LEFT);
        animalPosition.move(MoveDirection.BACKWARD);
        assertNotEquals("Animal position is: Wschód and (4, 4)", animalPosition.toString());
    }

    Animal animalOutsideTheMap = new Animal();

    @Test
    public void outsideTheMapTest() {
        animalPosition.move(MoveDirection.FORWARD);
        animalPosition.move(MoveDirection.FORWARD);
        animalPosition.move(MoveDirection.FORWARD);
        assertEquals("Animal position is: Północ and (2, 4)", animalPosition.toString());
        animalPosition.move(MoveDirection.RIGHT);
        assertEquals("Animal position is: Wschód and (2, 4)", animalPosition.toString());
        animalPosition.move(MoveDirection.LEFT);
        animalPosition.move(MoveDirection.FORWARD);
        assertNotEquals("Animal position is: Północ and (2, 3)", animalPosition.toString());
        animalPosition.move(MoveDirection.BACKWARD);
        assertNotEquals("Animal position is: Północ and (2, 4)", animalPosition.toString());
    }

    public boolean searchForValue(String input) {
        switch (input) {
            case "f", "forward", "b", "backward", "r", "right", "l", "left" -> {
                return true;
            }
        }
        return false;
    }

    @Test
    public void correctInputTest() {
        String[] correctDirections = {"f", "forward", "r", "l", "backward"};
        String[] wrongDirections = {"a", "back", "turn right", "le", "front"};
        for (String s : correctDirections) {
            assertTrue(searchForValue(s));
        }
        for (String s : wrongDirections) {
            assertFalse(searchForValue(s));
        }
    }
}