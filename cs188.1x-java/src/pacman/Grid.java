package pacman;

import java.util.List;
import util.Position;

/**
 *
 * @author archie
 */
public interface Grid {
    
    public Grid copy();
    public Grid deepCopy();
    public Grid shallowCopy();
    public int getCount(Boolean item);
    public List<Position> asList(Boolean key);
    public List<Integer> packBits();
    public Grid reconstituteGrid(List<Integer> bitRep);
    public boolean get(int x, int y);
    public void set(int x, int y, boolean bool);
    public int getWidth();
    public int getHeight();
    public List<List<Boolean>> getData();
    
    /** Returns the number of items in the grid */
    public int getCount();
    
}
