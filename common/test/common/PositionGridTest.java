/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author archiem
 */
public class PositionGridTest {
    
    public PositionGridTest() {
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
     * Test of getX method, of class PositionGrid.
     */
    @Test
    public void testGetXY() {
        final PositionGrid instance = new PositionGrid(1,2);
        assertEquals(1, instance.getX());
        assertEquals(2, instance.getY());
    }


    /**
     * Test of equals method, of class PositionGrid.
     */
    @Test
    public void testEquals() {
        final PositionGrid pos1 = new PositionGrid(3,3);
        final PositionGrid pos2 = new PositionGrid(3,3);
        assertEquals(pos1, pos2);
    }

    /**
     * Test of hashCode method, of class PositionGrid.
     */
    @Test
    public void testHashCodeSame() {
        final PositionGrid pos1 = new PositionGrid(3,3);
        final PositionGrid pos2 = new PositionGrid(3,3);
        assertEquals(pos1.hashCode(), pos2.hashCode());
    }
    @Test
    public void testHashCodeDifferent() {
        final PositionGrid pos1 = new PositionGrid(3,3);
        final PositionGrid pos2 = new PositionGrid(3,3);
        assertTrue(pos1.hashCode() == pos2.hashCode());
    }
}
