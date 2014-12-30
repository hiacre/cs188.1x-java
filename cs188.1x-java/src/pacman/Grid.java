package pacman;

import common.PositionGrid;
import java.util.List;

public interface Grid {
    
    public Grid copy();
    public Grid deepCopy();
    public Grid shallowCopy();
    public int getCount(Boolean item);
    public List<PositionGrid> asList();
    public List<Integer> packBits();
    public boolean get(int x, int y);
    public void set(int x, int y, boolean bool);
    public int getWidth();
    public int getHeight();
    public List<List<Boolean>> getData();
    
    /** Returns the number of items in the grid */
    public int getCount();
    
}
