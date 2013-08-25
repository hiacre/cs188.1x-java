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
            String help,
            final String metavar,
            final Object aDefault,
            final String action) {
        this.optionShort = optionShort;
        this.optionLong = optionLong;
        help = getDefault(help, aDefault);
    }
    
    private String getDefault(final String str, final Object defaultValue) {
        return str + " [Default: " + defaultValue.toString() + "]";
    }

    ParsedArgs parse_args(final List<String> argv) {
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
        
        public void put(final String option, final String value) {
            options.put(option, value);
        }

        void setNumQuiet(int i) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        void setNumIgnore(int i) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
