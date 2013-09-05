package pacman;

import common.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author archie
 */
public class OptionParser {
    
    private final String usage;
    private final Map<String,Option> mapOptionsByShort = new HashMap<>();
    private final Map<String,Option> mapOptionsByLong = new HashMap<>();

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
        final Option option = new Option(optionShort, optionLong, dest, type, getDefault(helpText, aDefault), metavar, aDefault, action);
        this.mapOptionsByShort.put(optionShort, option);
        this.mapOptionsByLong.put(optionLong, option);
    }
    
    private String getDefault(final String str, final Object defaultValue) {
        return str + (defaultValue == null ? "" : " [Default: " + defaultValue.toString() + "]");
    }

    Pair<Map<String,String>,List<String>> parse_args(final List<String> argv) {
        final Map<String,String> useful = new HashMap<>();
        final List<String> junk = new ArrayList<>();
        for(String arg : argv) {
            // does this argument match an allowed option?
            if(mapOptionsByShort.containsKey(arg) || mapOptionsByLong.containsKey(arg)) {
                useful.put(arg,null);
            } else {
                junk.add(arg);
            }
        }
        return new Pair<>(useful, junk);
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
