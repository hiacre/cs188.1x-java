package util;

import common.Position;
import common.PositionGrid;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author archie
 */
public class PointStandardTest {
    
    private static final double delta = 0.001;
    
    public PointStandardTest() {
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


    /**
     * Test of manhattanDistance method, of class PointStandard.
     */
    @Test
    public void testManhattanDistance() {
        final Position p1 = Position.newInstance(0, 0);
        final Position p2 = Position.newInstance(3, 4);
        
        assertEquals(7, p1.manhattanDistance(p2), delta);
    }
    
    @Test
    public void testNearestPoint1() {
        final PositionGrid p = Position.nearestPoint(1.0,1.0);
        
        assertEquals(1, p.getX());
        assertEquals(1, p.getY());
    }
    
    @Test
    public void testNearestPoint2() {
        final PositionGrid p = Position.nearestPoint(1.5, 1.5);
        
        assertEquals(2, p.getX());
        assertEquals(2, p.getY());
    }
}