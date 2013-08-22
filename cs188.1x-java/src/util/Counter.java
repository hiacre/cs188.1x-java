/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.List;
import java.util.Set;

/**
 *
 * @author archie
 */
public interface Counter<K extends Comparable> {
    
    /** Gets the counter for the given key */
    public double get(K key);
    
    /** Puts the counter label with the given count */
    public double put(K key, double count);
    
    /** Increments a single key by the given amount */
    public double increment(final K key, final double count);
    
    /** Increments all elements of keys by the same amount */
    public void incrementAll(final List<K> keys, final double count);
    
    /** Returns the key with the highest counter */
    public K getArgMax();

    /** Returns a set containing the Counter keys */
    public Set<K> keySet();
    
    /** Returns a list of keys sorted by their counter value.  Keys with
     * highest value will appear first.
     */
    public List<K> getSortedKeys();
    
    /** Gets the sum of counts for all keys. */
    public double getTotalCount();
    
    /** Returns a Counter containing the same keys, but with values changed
     * such that the total count is 1.  The ratio of counts for all keys
     * will remain the same.  Note that normalizing an empty Counter will
     * result in an IllegalStateException.
     */
    public Counter<K> getNormalized() throws IllegalStateException;
    
    /** Returns the dot product of the two counters by treating each counter
     * as a vector.  Each unique label is a vector element. */
    public double dotProduct(final Counter<K> counter);
    
    /** Creates a new counter that is the union of labels from each counter,
     * and values are the added counts. */
    public Counter<K> add(Counter<K> counter);
    
    /** Creates a new counter that is the union of all labels from each counter,
     * and values are counts of second subtracted from counts of first. */
    public Counter<K> subtract(Counter<K> counter);

    public boolean isEmpty();
}
