package pacman;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author archie
 */
public class OptionParser {
    
    private final String usage;
    private final List<Option> options = new ArrayList<>();

    public OptionParser(final String usage) {
        this.usage = usage;
    }
    
    public void add_option(
            final String optionShort,
            final String optionLong,
            final String dest,
            final String type,
            final String helpText,
            final String metavar,
            final Object aDefault,
            final String action) {
        Option option = new Option(optionShort, optionLong, dest, type, getDefault(helpText, aDefault), metavar, aDefault, action);
        this.options.add(option);
    }
    
    private String getDefault(final String str, final Object defaultValue) {
        return str + " [Default: " + defaultValue.toString() + "]";
    }

    ParsedArgs parse_args(final List<String> argv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static class Option {

        private final String optionShort;
        private final String optionLong;
        private final String dest;
        private final String type;
        private final String help;
        private final String metavar;
        private final Object aDefault;
        private final String action;
                        
        private Option(
                final String optionShort,
                final String optionLong,
                final String dest,
                final String type,
                final String help,
                final String metavar,
                final Object aDefault,
                final String action) {
            this.optionShort = optionShort;
            this.optionLong = optionLong;
            this.dest = dest;
            this.type = type;
            this.help = help;
            this.metavar = metavar;
            this.aDefault = aDefault;
            this.action = action;
        }

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
