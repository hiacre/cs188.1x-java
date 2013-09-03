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
public class UtilTest {
    
    public UtilTest() {
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
    public void testSample1() {
        final Counter<String> c = Counter.newInstance();
        
        c.put("1", 2);
        c.put("2", 2);
        c.put("3", 2);
        c.put("4", 4);
        final String key = Util.sample(c, 0.5);
        
        assertEquals("3", key);
    }
    
    @Test
    public void testSample2() {
        final Counter<String> c = Counter.newInstance();
        
        c.put("1", 2);
        c.put("2", 2);
        c.put("3", 2);
        c.put("4", 4);
        final String key = Util.sample(c, 1);
        
        assertEquals("4", key);
    }
}