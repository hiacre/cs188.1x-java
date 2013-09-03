package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import util.Args;
import util.Options;
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
    @Test
    public void testParseArgs() {
        final OptionParser parser = Pacman.makeParser();
        final List<String> argv = new ArrayList<>();
        final ParsedArgs parsedArgs = parser.parse_args(argv);
    }
    @Test
    public void testParseArgs2() {
        final OptionParser parser = new OptionParser("usage");
        parser.add_option("-s", "--long_option", "dest", "type", "helpText", "metavar", "aDefault", "action");
        final List<String> argv = Arrays.asList("-s", "--long_option=foo", "-x");
        final ParsedArgs parsedArgs = parser.parse_args(argv);
        Args args = parsedArgs.getArgs();
        Options options = parsedArgs.getOptions();
        assertEquals(2, args.size());
        assertEquals(1, options.size());
    }
}
