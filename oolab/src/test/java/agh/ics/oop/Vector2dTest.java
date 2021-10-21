package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    Vector2d vector1 = new Vector2d(1, -10);
    Vector2d vector2 = new Vector2d(2, 9);
    Vector2d vector3 = new Vector2d(-11, 27);
    Vector2d vector4 = new Vector2d(1, -10);
    Vector2d vector5 = new Vector2d(-11, 27);
    Vector2d vector6 = new Vector2d(2, 9);

    @Test
    public void testEquals() {
        assertEquals(vector1, vector4);
        assertEquals(vector2, vector6);
        assertEquals(vector3, vector5);
        assertNotEquals(vector1, vector6);
        assertNotEquals(vector2, vector3);
        assertNotEquals(vector4, vector5);
    }

    @Test
    public void testToString() {
        assertEquals("(-11, 27)", vector3.toString());
        assertEquals("(1, -10)", vector1.toString());
        assertEquals("(2, 9)", vector6.toString());
        assertNotEquals("(11,11)", vector5.toString());
        assertNotEquals("(-4, -4)", vector3.toString());
        assertNotEquals("(0, 0)", vector2.toString());
    }

    @Test
    public void testPrecedes() {
        assertTrue(vector1.precedes(vector2));
        assertTrue(vector4.precedes(vector1));
        assertTrue(vector5.precedes(vector3));
        assertFalse(vector2.precedes(vector1));
        assertFalse(vector3.precedes(vector1));
        assertFalse(vector6.precedes(vector5));
    }

    @Test
    public void testFollows() {
        assertTrue(vector1.follows(vector4));
        assertTrue(vector4.follows(vector1));
        assertTrue(vector5.follows(vector3));
        assertFalse(vector2.follows(vector3));
        assertFalse(vector3.follows(vector1));
        assertFalse(vector6.follows(vector5));
    }

    @Test
    public void testUpperRight() {
        assertEquals(new Vector2d(2, 9), vector1.upperRight(vector6));
        assertEquals(new Vector2d(1, 27), vector4.upperRight(vector5));
        assertEquals(new Vector2d(2, 9), vector2.upperRight(vector6));
        assertNotEquals(new Vector2d(100, -2), vector3.upperRight(vector4));
        assertNotEquals(new Vector2d(14, 0), vector6.upperRight(vector2));
        assertNotEquals(new Vector2d(6, 7), vector5.upperRight(vector6));
    }

    @Test
    public void testLowerLeft() {
        assertEquals(new Vector2d(-11, -10), vector1.lowerLeft(vector3));
        assertEquals(new Vector2d(1, -10), vector2.lowerLeft(vector4));
        assertEquals(new Vector2d(-11, 27), vector3.lowerLeft(vector5));
        assertNotEquals(new Vector2d(14, -1), vector3.lowerLeft(vector4));
        assertNotEquals(new Vector2d(6, -5), vector6.lowerLeft(vector2));
        assertNotEquals(new Vector2d(-1, -1), vector5.lowerLeft(vector6));
    }

    @Test
    public void testAdd() {
        assertEquals(new Vector2d(-10, 17), vector1.add(vector3));
        assertEquals(new Vector2d(3, -1), vector4.add(vector6));
        assertEquals(new Vector2d(-22, 54), vector3.add(vector5));
        assertNotEquals(new Vector2d(11, -12), vector1.add(vector2));
        assertNotEquals(new Vector2d(6, 9), vector3.add(vector5));
        assertNotEquals(new Vector2d(-12, -19), vector1.add(vector4));
    }

    @Test
    public void testSubtract() {
        assertEquals(new Vector2d(12, -37), vector1.subtract(vector3));
        assertEquals(new Vector2d(1, 19), vector2.subtract(vector4));
        assertEquals(new Vector2d(0, 0), vector5.subtract(vector3));
        assertNotEquals(new Vector2d(14, -1), vector1.subtract(vector5));
        assertNotEquals(new Vector2d(6, -5), vector3.subtract(vector2));
        assertNotEquals(new Vector2d(-1, -1), vector5.subtract(vector6));
    }

    @Test
    public void testOpposite() {
        assertEquals(new Vector2d(-1, 10), vector1.opposite());
        assertEquals(new Vector2d(11, -27), vector3.opposite());
        assertEquals(new Vector2d(-2, -9), vector6.opposite());
        assertNotEquals(new Vector2d(9, 2), vector2.opposite());
        assertNotEquals(new Vector2d(1, -10), vector4.opposite());
        assertNotEquals(new Vector2d(14, 18), vector5.opposite());
    }
}