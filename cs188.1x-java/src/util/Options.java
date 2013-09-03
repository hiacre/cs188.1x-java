package util;

import java.util.Map;

/**
 *
 * @author archie
 */
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
    
    public int size() {
        return options.size();
    }
}
