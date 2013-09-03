/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.Arrays;
import java.util.List;
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
public class PacmanTest {
    
    public PacmanTest() {
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
     * Test of main method, of class Pacman.
     */
    @Test
    public void testMain() {
//        System.out.println("main");
//        String[] args = new String[] {};
//        Pacman.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    @Test
    public void testReadCommand() {
        final List<String> argv = Arrays.asList();
        
        final Command cmd = Pacman.readCommand(argv);
        
        assertTrue(cmd.getDisplay() instanceof PacmanGraphicsNonText);
    }
}