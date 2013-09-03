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
        return str + (defaultValue == null ? "" : " [Default: " + defaultValue.toString() + "]");
    }

    ParsedArgs parse_args(final List<String> argv) {
        return new ParsedArgs();
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
}
