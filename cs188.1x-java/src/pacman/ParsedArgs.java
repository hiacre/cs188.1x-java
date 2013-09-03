package pacman;

import java.util.HashMap;
import util.Args;
import util.Options;

/**
 *
 * @author archie
 */
public class ParsedArgs {

    Options getOptions() {
        return new Options(new HashMap<String,String>());
    }

    Args getArgs() {
        return new Args();
    }

}