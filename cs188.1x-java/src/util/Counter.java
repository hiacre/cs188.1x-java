package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author archie
 */
public class Counter<K extends Comparable<K>> {
    
    final Map<K, Double> map;
    private final static double defaultValue = 0;

    public Counter() {
        this.map = new HashMap<>();
    }
    
    public static double getDefaultValue() {
        return defaultValue;
    }
    
    /** Gets the counter for the given key */
    public double get(K key) {
        if(map.containsKey(key)) {
            return map.get(key);
        }
        return defaultValue;
    }

    /** Puts the counter label with the given count */
    public double put(K key, double value) {
        final Double previousValue = map.put(key, value);
        if(previousValue == null) {
            return defaultValue;
        } else {
            return previousValue;
        }
    }

    /** Increments all elements of keys by the same amount */
    public void incrementAll(List<K> keys, double count) {
        for(K key : keys) {
            map.put(key, map.get(key) + count);
        }
    }

    /** Returns the key with the highest counter */
    public K getArgMax() {
        Entry<K, Double> maxEntry = null;
        for(Entry<K, Double> entry : map.entrySet()) {
            if(maxEntry == null) {
                maxEntry = entry;
            } else if(entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        if(maxEntry == null) {
            throw new IllegalStateException("Error to call argMax on an empty Counter");
        }
        return maxEntry.getKey();
    }

    /** Returns a list of keys sorted by their counter value.  Keys with
     * highest value will appear first.
     */
    public List<K> getSortedKeys() {
        List<K> result = new ArrayList<>();
        result.addAll(map.keySet());
        Collections.sort(result, new Comparator<K>() {
            @Override
            public int compare(K o1, K o2) {
                return o1.compareTo(o2);
            }
        });
        return result;
    }

    /** Gets the sum of counts for all keys. */
    public double getTotalCount() {
        double total = 0;
        for(double i : map.values()) {
            total += i;
        }
        return total;
    }

    /** Returns a Counter containing the same keys, but with values changed
     * such that the total count is 1.  The ratio of counts for all keys
     * will remain the same.  Note that normalizing an empty Counter will
     * result in an IllegalStateException.
     */
    public Counter<K> getNormalized() throws IllegalStateException {
        double totalCount = getTotalCount();
        
        final Counter<K> result = new Counter<>();
        for(Entry<K, Double> entry : map.entrySet()) {
            result.put(entry.getKey(), entry.getValue() / totalCount);
        }
        return result;
    }

    /** Returns the dot product of the two counters by treating each counter
     * as a vector.  Each unique label is a vector element. */
    public double dotProduct(final Counter<K> counter) {
        final Set<K> keyset1 = map.keySet();
        final Set<K> keyset2 = counter.keySet();
        final int keySetSize1 = keyset1.size();
        final int keySetSize2 = keyset2.size();
        final Set<K> keys = keySetSize1 < keySetSize2 ? keyset1 : keyset2;
        
        int result = 0;
        for(K key : keys) {
            result += get(key) * counter.get(key);
        }
        return result;
    }

    /** Creates a new counter that is the union of labels from each counter,
     * and values are the added counts. */
    public Counter<K> add(final Counter<K> counter) {
        final Counter<K> c = new Counter<>();
        
        final Set<K> keys = new HashSet<>();
        keys.addAll(this.getSortedKeys());
        keys.addAll(counter.getSortedKeys());
        
        for(K key : keys) {
            c.put(key, this.get(key) + counter.get(key));
        }
        
        return c;
    }

    /** Creates a new counter that is the union of all labels from each counter,
     * and values are counts of second subtracted from counts of first. */
    public Counter<K> subtract(Counter<K> counter) {
        final Counter<K> c = new Counter<>();
        
        final Set<K> keys = new HashSet<>();
        keys.addAll(this.getSortedKeys());
        keys.addAll(counter.getSortedKeys());
        
        for(K key : keys) {
            c.put(key, this.get(key) - counter.get(key));
        }
        
        return c;
    }

    /** Increments a single key by the given amount */
    public double increment(K key, double count) {
        final double value = get(key) + count;
        map.put(key, value);
        return value;
    }

    /** Returns a set containing the Counter keys */
    public Set<K> keySet() {
        return map.keySet();
    }

    public boolean isEmpty() {
        return this.keySet().isEmpty();
    }
    
}
