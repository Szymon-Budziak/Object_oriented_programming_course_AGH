package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MapDirectionTest {
    @Test
    public void testNext() {
        assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
        assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
        assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
        assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
        assertNotEquals(MapDirection.WEST, null);
        assertNotEquals(MapDirection.NORTH, MapDirection.NORTH.next());
        assertNotEquals(MapDirection.SOUTH, null);
        assertNotEquals(MapDirection.EAST, MapDirection.SOUTH.next());
    }

    @Test
    public void testPrevious() {
        assertEquals(MapDirection.NORTH, MapDirection.EAST.previous());
        assertEquals(MapDirection.EAST, MapDirection.SOUTH.previous());
        assertEquals(MapDirection.SOUTH, MapDirection.WEST.previous());
        assertEquals(MapDirection.WEST, MapDirection.NORTH.previous());
        assertNotEquals(MapDirection.EAST, MapDirection.NORTH.previous());
        assertNotEquals(MapDirection.SOUTH, null);
        assertNotEquals(MapDirection.WEST, MapDirection.SOUTH.previous());
        assertNotEquals(MapDirection.NORTH, null);
    }
}
