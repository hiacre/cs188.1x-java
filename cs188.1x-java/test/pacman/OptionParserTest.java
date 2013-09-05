package pacman;

import common.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class OptionParserTest {
    
    public OptionParserTest() {
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
     * Test of parse_args method, of class OptionParser.
     */
    /** Does it parse no arguments correctly? */
    @Test
    public void testParseArgs() {
        final OptionParser parser = new OptionParser("usage");
        parser.add_option("-s", "--long_option", "dest", "type", "helpText", "metavar", "aDefault", "action");
        final List<String> argv = new ArrayList<>();
        final Pair<Map<String,String>,List<String>> parsedArgs = parser.parse_args(argv);
        assertTrue(parsedArgs.getFirst().containsKey("-s"));
    }
    @Test
    public void testParseArgs2() {
        final OptionParser parser = new OptionParser("usage");
        parser.add_option("-s", "--long_option", "dest", "type", "helpText", "metavar", "aDefault", "action");
        final List<String> argv = Arrays.asList("-s");
        final Pair<Map<String,String>,List<String>> parsedArgs = parser.parse_args(argv);
        final Map<String,String> options = parsedArgs.getFirst();
        final List<String> junk = parsedArgs.getSecond();
        assertEquals(1, options.size());
        assertEquals(0, junk.size());
    }
    @Test
    public void testParseArgs3() {
        final OptionParser parser = new OptionParser("usage");
        parser.add_option("-s", "--long_option", "dest", "type", "helpText", "metavar", "aDefault", "action");
        final List<String> argv = Arrays.asList("--long_option");
        final Pair<Map<String,String>,List<String>> parsedArgs = parser.parse_args(argv);
        final Map<String,String> options = parsedArgs.getFirst();
        final List<String> junk = parsedArgs.getSecond();
        assertEquals(1, options.size());
        assertEquals(0, junk.size());
    }
    @Test
    public void testParseArgs4() {
        final OptionParser parser = new OptionParser("usage");
        parser.add_option("-s", "--long_option", "dest", "type", "helpText", "metavar", "aDefault", "action");
        final List<String> argv = Arrays.asList("-x");
        final Pair<Map<String,String>,List<String>> parsedArgs = parser.parse_args(argv);
        final Map<String,String> options = parsedArgs.getFirst();
        final List<String> junk = parsedArgs.getSecond();
        assertEquals(0, options.size());
        assertEquals(1, junk.size());
    }
    
}
