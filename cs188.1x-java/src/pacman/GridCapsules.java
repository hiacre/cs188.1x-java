package pacman;

import java.util.List;

/**
 *
 * @author archie
 */
public class GridCapsules extends GridStandard {

    public GridCapsules(final int width, final int height, Boolean initialValue, final List<Integer> bitRepresentation) {
        super(width, height, initialValue, null);
    }
    
    public GridCapsules(List<List<Boolean>> data) {
        super(data, false);
    }
    
    public static GridCapsules newInstance(final int width, final int height, final boolean initialValue) {
        return new GridCapsules(width, height, initialValue, null);
    }
    
    @Override
    public GridCapsules copy() {
        return deepCopy();
    }
    
    @Override
    public GridCapsules deepCopy() {
        return new GridCapsules(deepCopyGrid(getData()));
    }

    public boolean isCapsule(final int x, final int y) {
        return this.get(x, y);
    }

    public void removeCapsule(final int x, final int y) {
        this.set(x, y, false);
    }
}
