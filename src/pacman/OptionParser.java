/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author archie
 */
class OptionParser {
    
    private final String usage;
    private final String optionShort;
    private final String optionLong;

    public OptionParser(final String usage) {
        this.usage = usage;
    }
    
    public void add_option(
            final String optionShort,
            final String optionLong,
            final String dest,
            final String type,
            final String help,
            final String metavar,
            final String aDefault,
            final String action) {
        this.optionShort = optionShort;
        this.optionLong = optionLong;
    }

    ParsedArgs parse_args(String[] argv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public class ParsedArgs {

        Options getOptions() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        Args getArgs() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public class Options {
        private final Map<String, String> options;
        
        public Options(Map<String, String> options) {
            this.options = options;
        }
        
        public boolean contains(final String option) {
            return options.containsKey(option);
        }
        
        public String get(final String option) {
            return options.get(option);
        }
    }
    
    public class Args {
        private final List<Object> args;
        
        public Args() {
            args = new ArrayList<>();
        }
        
        public boolean isEmpty() {
            return args.isEmpty();
        }
    }
}
