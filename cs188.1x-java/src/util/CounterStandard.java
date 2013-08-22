/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class CounterStandard<K extends Comparable> implements Counter<K> {
    
    final Map<K, Double> map;
    private final static double defaultValue = 0;

    private CounterStandard() {
        this.map = new HashMap<>();
    }
    
    public static CounterStandard newInstance() {
        return new CounterStandard();
    }
    
    public static double getDefaultValue() {
        return defaultValue;
    }
    
    @Override
    public double get(K key) {
        if(map.containsKey(key)) {
            return map.get(key);
        }
        return defaultValue;
    }

    @Override
    public double put(K key, double value) {
        final Double previousValue = map.put(key, value);
        if(previousValue == null) {
            return defaultValue;
        } else {
            return previousValue;
        }
    }

    @Override
    public void incrementAll(List<K> keys, double count) {
        for(K key : keys) {
            map.put(key, map.get(key) + count);
        }
    }

    @Override
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

    @Override
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

    @Override
    public double getTotalCount() {
        double total = 0;
        for(double i : map.values()) {
            total += i;
        }
        return total;
    }

    @Override
    public Counter<K> getNormalized() throws IllegalStateException {
        double totalCount = getTotalCount();
        
        final Counter<K> result = new CounterStandard<>();
        for(Entry<K, Double> entry : map.entrySet()) {
            result.put(entry.getKey(), entry.getValue() / totalCount);
        }
        return result;
    }

    @Override
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

    @Override
    public Counter<K> add(final Counter<K> counter) {
        final Counter<K> c = CounterStandard.newInstance();
        
        final Set<K> keys = new HashSet<>();
        keys.addAll(this.getSortedKeys());
        keys.addAll(counter.getSortedKeys());
        
        for(K key : keys) {
            c.put(key, this.get(key) + counter.get(key));
        }
        
        return c;
    }

    @Override
    public Counter<K> subtract(Counter<K> counter) {
        final Counter<K> c = CounterStandard.newInstance();
        
        final Set<K> keys = new HashSet<>();
        keys.addAll(this.getSortedKeys());
        keys.addAll(counter.getSortedKeys());
        
        for(K key : keys) {
            c.put(key, this.get(key) - counter.get(key));
        }
        
        return c;
    }

    @Override
    public double increment(K key, double count) {
        final double value = get(key) + count;
        map.put(key, value);
        return value;
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public boolean isEmpty() {
        return this.keySet().isEmpty();
    }
    
}
