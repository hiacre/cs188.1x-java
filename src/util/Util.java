/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author archie
 */
public class Util {

    private Util() {
        throw new RuntimeException("This is a utility class and should not be instantiated.");
    }
    
    public static String sampleFromCounter(final Counter<String> counter) {
        return sample(counter);
    }
        
    /**
     * Finds the normalized distribution from a counter, and loops over the
     * counter's values totaling them, until the total exceeds the given limit.
     * Return the key associated with the value that caused the total to exceed
     * the limit.
     */
    public static String sample(final Counter<String> c, final double choice) {
        
        final Map<String, Float> dist = c.getNormalized();
        
        float total = 0;
        String lastKey = null;
        for(String key : c.getSortedKeys()) {
            total += dist.get(key);
            lastKey = key;
            if(total > choice) {
                return key;
            }
        }
        return lastKey;
    }
    
    /**
     * Gets a sample from a Counter, based upon a randomly chosen limit.
     */
    public static String sample(final Counter<String> c) {
        return sample(c, new Random().nextDouble());
    }
    
    public static String chooseFromDistribution(final Counter<String> c) {
        return sample(c);
    }

    public static <E> List<E> makeList(final int size, final E element) {
        final List<E> list = new ArrayList<>();
        for(int i=0; i<size; i++) {
            list.add(element);
        }
        return list;
    }
}
