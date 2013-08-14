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
    public int getCount();
    public List<Position> asList();
    public List<Integer> packBits();
    public Grid reconstituteGrid(List<Integer> bitRep);
    public boolean get(int x, int y);
    public void set(int x, int y, boolean bool);
    public int getWidth();
    public int getHeight();
    public boolean isCapsule(Position position);
    public void removeCapsule(Position position);
    
}
