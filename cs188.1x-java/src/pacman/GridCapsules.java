package pacman;

import java.util.List;
import util.Position;

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

    public boolean isCapsule(final Position position) {
        return this.get(position.getX(), position.getY());
    }

    public void removeCapsule(final Position position) {
        this.set(position.getX(), position.getY(), false);
    }
}
