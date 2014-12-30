package common;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PositionTest {
    
    public PositionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testNearestPoint() {
        final PositionGrid result = Position.nearestPoint(1.4, 2.5);
        final PositionGrid expResult = new PositionGrid(1, 3);
        assertEquals(expResult, result);
    }

    @Test
    public void testNearestPoint_Position() {
        final PositionGrid result = Position.nearestPoint(Position.newInstance(1.5, 3.4));
        final PositionGrid expResult = new PositionGrid(2, 3);
        assertEquals(expResult, result);
    }


    @Test
    public void testManhattanDistance_Position() {
        final Position pos = Position.newInstance(1, 1);
        final Position otherPos = Position.newInstance(2, 3);
        double expResult = 3;
        double result = pos.manhattanDistance(otherPos);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testManhattanDistance_PositionGrid() {
        final Position pos = Position.newInstance(1, 1);
        final PositionGrid otherPos = new PositionGrid(2, 3);
        double expResult = 3;
        double result = pos.manhattanDistance(otherPos);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testGetRoundedXY() {
        final Position pos = Position.newInstance(2.4, 3.5);
        assertEquals(2, pos.getRoundedX());
        assertEquals(4, pos.getRoundedY());
    }

    @Test
    public void testGetFloorXY() {
        final Position pos = Position.newInstance(2.4, 3.5);
        assertEquals(2, pos.getFloorX());
        assertEquals(3, pos.getFloorY());
    }
}
