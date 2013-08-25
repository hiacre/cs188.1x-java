package util;

import java.util.Arrays;
import java.util.Map;
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
public class CounterTest {
    
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
        final Counter<String> c = CounterStandard.newInstance();
        int value = c.get("test");
        assertEquals(0, value);
    }
    
    @Test
    public void testPut() {
        final Counter<String> c = CounterStandard.newInstance();
        final String key = "key";
        c.put(key, c.get(key) + 1);
        assertEquals(1, c.get(key));
    }
    
    @Test
    public void testPut2() {
        final Counter<String> c = CounterStandard.newInstance();
        final String key = "key";
        final int firstPutValue = 3;
        final Integer prevResult1 = c.put(key, firstPutValue);
        final Integer prevResult2 = c.put(key, 7);
        
        assertNotNull(prevResult1);
        assertEquals(CounterStandard.getDefaultValue(), prevResult1.intValue());
        assertNotNull(prevResult2);
        assertEquals(firstPutValue, prevResult2.intValue());
    }
    
    @Test
    public void testIncrement() {
        final Counter<String> c = CounterStandard.newInstance();
        final int count = 3;
        final int result = c.increment("key", count);
        
        assertNotNull(result);
        assertEquals(count, result);
    }
    
    @Test
    public void testSubtract() {
        final Counter<String> c1 = CounterStandard.newInstance();
        final Counter<String> c2 = CounterStandard.newInstance();
        
        c1.put("1", 1);
        c1.put("2", 2);
        c2.put("2", 4);
        c2.put("3", 3);
        final Counter<String> result = c1.subtract(c2);
        
        assertEquals( 1, result.get("1"));
        assertEquals(-2, result.get("2"));
        assertEquals(-3, result.get("3"));
    }
    
    @Test
    public void testAdd() {
        final Counter<String> c1 = CounterStandard.newInstance();
        final Counter<String> c2 = CounterStandard.newInstance();
        
        c1.put("1", 1);
        c1.put("2", 2);
        c2.put("2", 4);
        c2.put("3", 3);
        final Counter<String> result = c1.add(c2);
        
        assertEquals(1, result.get("1"));
        assertEquals(6, result.get("2"));
        assertEquals(3, result.get("3"));
    }
    
    @Test
    public void testDotProduct() {
        final Counter<String> c1 = CounterStandard.newInstance();
        final Counter<String> c2 = CounterStandard.newInstance();
        
        
        c1.put("1", 1);
        c1.put("2", 2);
        c1.put("3", 3);
        c1.put("4", 4);
        c2.put("3", 3);
        c2.put("4", 4);
        c2.put("5", 5);
        c2.put("6", 6);
        c2.put("7", 7);
        final int result1 = c1.dotProduct(c2);
        final int result2 = c2.dotProduct(c1);
        
        assertEquals(25, result1);
        assertEquals(25, result2);
    }
    
    @Test
    public void testTotalCount() {
        final Counter<String> c = CounterStandard.newInstance();
        
        c.put("1", 1);
        c.put("2", 2);
        c.put("3", 3);
        c.put("4", 4);
        
        assertEquals(10, c.getTotalCount());
    }
    
    @Test
    public void testIncrementAll() {
        final Counter<String> c = CounterStandard.newInstance();
        
        c.put("1", 1);
        c.put("2", 2);
        c.put("3", 3);
        c.put("4", 4);
        
        c.incrementAll(Arrays.asList("3","4"), 3);
        assertEquals(1, c.get("1"));
        assertEquals(2, c.get("2"));
        assertEquals(6, c.get("3"));
        assertEquals(7, c.get("4"));
    }
    
    @Test
    public void testNormalized() {
        final Counter<String> c = CounterStandard.newInstance();
        
        c.put("1", 1);
        c.put("2", 2);
        c.put("3", 3);
        c.put("4", 4);
        
        final Map<String, Float> n = c.getNormalized();
        
        assertEquals((float)0.1, (float)n.get("1"), 0.001);
        assertEquals((float)0.2, (float)n.get("2"), 0.001);
        assertEquals((float)0.3, (float)n.get("3"), 0.001);
        assertEquals((float)0.4, (float)n.get("4"), 0.001);
    }
    
    @Test
    public void testArgMax() {
        final Counter<String> c = CounterStandard.newInstance();
        
        c.put("1", 1);
        c.put("2", 2);
        c.put("3", 3);
        c.put("4", 4);
        
        final String key = c.getArgMax();
        
        assertEquals("4", key);
    }
    
    @Test
    public void testArgMaxEmptyCounter() {
        final Counter<String> c = CounterStandard.newInstance();
        
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