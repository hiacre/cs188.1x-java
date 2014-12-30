package util;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CounterTest {
    
    private static final double delta = 0.0001;
    
    public CounterTest() {
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
    public void testGet() {
        final Counter<String> c = new Counter<>();
        double value = c.get("test");
        assertEquals(0, value, delta);
    }
    
    @Test
    public void testPut() {
        final Counter<String> c = new Counter<>();
        final String key = "key";
        c.put(key, c.get(key) + 1);
        assertEquals(1, c.get(key), delta);
    }
    
    @Test
    public void testPut2() {
        final Counter<String> c = new Counter<>();
        final String key = "key";
        final int firstPutValue = 3;
        final Double prevResult1 = c.put(key, firstPutValue);
        final Double prevResult2 = c.put(key, 7);
        
        assertNotNull(prevResult1);
        assertEquals(Counter.getDefaultValue(), prevResult1, delta);
        assertNotNull(prevResult2);
        assertEquals(firstPutValue, prevResult2, delta);
    }
    
    @Test
    public void testIncrement() {
        final Counter<String> c = new Counter<>();
        final int count = 3;
        final double result = c.increment("key", count);
        
        assertNotNull(result);
        assertEquals((double)count, result, delta);
    }
    
    @Test
    public void testSubtract() {
        final Counter<String> c1 = new Counter<>();
        final Counter<String> c2 = new Counter<>();
        
        c1.put("1", 1);
        c1.put("2", 2);
        c2.put("2", 4);
        c2.put("3", 3);
        final Counter<String> result = c1.subtract(c2);
        
        assertEquals( 1, result.get("1"), delta);
        assertEquals(-2, result.get("2"), delta);
        assertEquals(-3, result.get("3"), delta);
    }
    
    @Test
    public void testAdd() {
        final Counter<String> c1 = new Counter<>();
        final Counter<String> c2 = new Counter<>();
        
        c1.put("1", 1);
        c1.put("2", 2);
        c2.put("2", 4);
        c2.put("3", 3);
        final Counter<String> result = c1.add(c2);
        
        assertEquals(1, result.get("1"), delta);
        assertEquals(6, result.get("2"), delta);
        assertEquals(3, result.get("3"), delta);
    }
    
    @Test
    public void testDotProduct() {
        final Counter<String> c1 = new Counter<>();
        final Counter<String> c2 = new Counter<>();
        
        c1.put("1", 1);
        c1.put("2", 2);
        c1.put("3", 3);
        c1.put("4", 4);
        c2.put("3", 3);
        c2.put("4", 4);
        c2.put("5", 5);
        c2.put("6", 6);
        c2.put("7", 7);
        final double result1 = c1.dotProduct(c2);
        final double result2 = c2.dotProduct(c1);
        
        assertEquals(25, result1, delta);
        assertEquals(25, result2, delta);
    }
    
    @Test
    public void testTotalCount() {
        final Counter<String> c = new Counter<>();
        
        c.put("1", 1);
        c.put("2", 2);
        c.put("3", 3);
        c.put("4", 4);
        
        assertEquals(10, c.getTotalCount(), delta);
    }
    
    @Test
    public void testIncrementAll() {
        final Counter<String> c = new Counter<>();
        
        c.put("1", 1);
        c.put("2", 2);
        c.put("3", 3);
        c.put("4", 4);
        
        c.incrementAll(Arrays.asList("3","4"), 3);
        assertEquals(1, c.get("1"), delta);
        assertEquals(2, c.get("2"), delta);
        assertEquals(6, c.get("3"), delta);
        assertEquals(7, c.get("4"), delta);
    }
    
    @Test
    public void testNormalized() {
        final Counter<String> c = new Counter<>();
        
        c.put("1", 1);
        c.put("2", 2);
        c.put("3", 3);
        c.put("4", 4);
        
        final Counter<String> n = c.getNormalized();
        
        assertEquals((float)0.1, (float)n.get("1"), delta);
        assertEquals((float)0.2, (float)n.get("2"), delta);
        assertEquals((float)0.3, (float)n.get("3"), delta);
        assertEquals((float)0.4, (float)n.get("4"), delta);
    }
    
    @Test
    public void testArgMax() {
        final Counter<String> c = new Counter<>();
        
        c.put("1", 1);
        c.put("2", 2);
        c.put("3", 3);
        c.put("4", 4);
        
        final String key = c.getArgMax();
        
        assertEquals("4", key);
    }
    
    @Test
    public void testArgMaxEmptyCounter() {
        final Counter<String> c = new Counter<>();
        
        boolean success = false;
        try {
            final String key = c.getArgMax();
        } catch(IllegalStateException ex) {
            success = true;
        }
        if(!success) {
            fail("getArgMax() failed to throw IllegalStateException on empty Counter");
        }
    }
}