package common;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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

    @Test
    public void testGetXY() {
        final PositionGrid instance = new PositionGrid(1,2);
        assertEquals(1, instance.getX());
        assertEquals(2, instance.getY());
    }


    @Test
    public void testEquals() {
        final PositionGrid pos1 = new PositionGrid(3,3);
        final PositionGrid pos2 = new PositionGrid(3,3);
        final PositionGrid pos3 = null;
        final Position pos4 = Position.newInstance(3, 3);
        final PositionGrid pos5 = new PositionGrid(2, 3);
        final PositionGrid pos6 = new PositionGrid(3, 2);
        
        assertEquals(pos1, pos2);
        assertFalse(pos1.equals(pos3));
        assertFalse(pos1.equals(pos4));
        assertFalse(pos1.equals(pos5));
        assertFalse(pos1.equals(pos6));
    }

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
    @Test
    public void testToString() {
        final PositionGrid pos = new PositionGrid(4,5);
        assertEquals("(4,5)", pos.toString());
    }
}
