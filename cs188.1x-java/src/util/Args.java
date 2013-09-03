package util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author archie
 */
public class Args {
    private final List<Object> args;

    public Args() {
        args = new ArrayList<>();
    }

    public boolean isEmpty() {
        return args.isEmpty();
    }
    
    public int size() {
        return args.size();
    }
}
