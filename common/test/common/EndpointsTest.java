package common;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EndpointsTest {
    
    public EndpointsTest() {
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
    public void testGetFirstAndSecond() {
        Endpoints instance = new Endpoints(3.1, 4.2);
        assertEquals(3.1, instance.getFirst(), 0);
        assertEquals(4.2, instance.getSecond(), 0);
    }
}
