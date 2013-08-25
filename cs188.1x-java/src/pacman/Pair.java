package pacman;

/**
 *
 * @author archie
 */
public class Pair<K,V> {
    
    private final K first;
    private final V second;
    
    public Pair(final K first, final V second) {
        this.first = first;
        this.second = second;
    }
    
    public K getFirst() {
        return first;
    }
    
    public V getSecond() {
        return second;
    }
}
