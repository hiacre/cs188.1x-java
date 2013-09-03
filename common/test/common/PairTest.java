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
public class PairTest {
    
    public PairTest() {
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
     * Test of getFirst method, of class Pair.
     */
    @Test
    public void testGetFirstAndSecond() {
        final Pair<Integer,Integer> instance = new Pair<>(2,3);
        assertEquals(instance.getFirst(), Integer.valueOf(2));
        assertEquals(instance.getSecond(), Integer.valueOf(3));
    }
}