/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

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
        final Position p1 = PositionStandard.newInstance(0, 0);
        final Position p2 = PositionStandard.newInstance(3, 4);
        
        assertEquals(7, p1.manhattanDistance(p2));
    }
    
    @Test
    public void testNearestPoint1() {
        final Position p = PositionStandard.nearestPoint(1.0,1.0);
        
        assertEquals(1, p.getX());
        assertEquals(1, p.getY());
    }
    
    @Test
    public void testNearestPoint2() {
        final Position p = PositionStandard.nearestPoint(1.5, 1.5);
        
        assertEquals(2, p.getX());
        assertEquals(2, p.getY());
    }
}