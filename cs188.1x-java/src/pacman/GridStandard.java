package pacman;

import common.Position;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import util.Util;

/**
 * A 2-dimensional array of objects backed by a list of lists.  Data is accessed
    via grid.get(x,y) where (x,y) are positions on a Pacman map with x horizontal,
    y vertical and the origin (0,0) in the bottom left corner.

    The toString() method constructs an output that is oriented like a pacman board.
 * @author archie
 */
public class GridStandard implements Grid {
    
    private final static int CELLS_PER_INT = 30;
    private final int width;
    private final int height;
    private final List<List<Boolean>> data;

    protected GridStandard(final int width, final int height, Boolean initialValue, final List<Integer> bitRepresentation) {
        if(initialValue == null) {
            initialValue = false;
        }

        this.width = width;
        this.height = height;
        this.data = new ArrayList();
        for(int x=0; x<width; x++) {
            final List<Boolean> column = Util.makeList(height, initialValue);
            this.data.add(column);
        }
        if(bitRepresentation != null) {
            unpackBits(bitRepresentation);
        }
    }
    
    protected GridStandard(final List<List<Boolean>> data, final boolean isDeepCopy) {
        this.width = data.size();
        this.height = data.get(0).size();
        if(isDeepCopy) {
            this.data = deepCopyGrid(data);
        } else {
            this.data = data;
        }
    }
    
    protected static List<List<Boolean>> deepCopyGrid(List<List<Boolean>> data) {
        final int width = data.size();
        final int height = data.get(0).size();
        final List<List<Boolean>> result = new ArrayList<>();
        for(int x=0; x<width; x++) {
            final List<Boolean> column = new ArrayList<>();
            for(int y=0; y<height; y++) {
                column.add(data.get(x).get(y));
            }
            result.add(column);
        }
        return result;
    }
    
    @Override
    public List<List<Boolean>> getData() {
        return data;
    }
    
    public static Grid newInstance(final int width, final int height, final boolean initialValue) {
        return new GridStandard(width, height, initialValue, null);
    }
    
    @Override
    /** This returns a deep copy */
    public Grid copy() {
        return deepCopy();
    }

    @Override
    public Grid deepCopy() {
        return new GridStandard(this.data, true);
    }

    @Override
    public Grid shallowCopy() {
        return new GridStandard(this.data, false);
    }
    
    @Override
    public int getCount() {
        return getCount(true);
    }

    @Override
    public int getCount(Boolean item) {
        if(item == null) {
            item = true;
        }
        int count = 0;
        for(List<Boolean> col : this.data) {
            for(Boolean b : col) {
                if(item.equals(b)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public List<Position> asList() {
        return asList(true);
    }
    
    private List<Position> asList(Boolean key) {
        if(key == null) {
            key = true;
        }
        final List list = new ArrayList();
        for(int x=0; x<width; x++) {
            for(int y=0; y<height; y++) {
                if(key.equals(get(x,y))) {
                    list.add(Position.newInstance(x, y));
                }
            }
        }
        return list;
    }

    @Override
    /** Returns an efficient int list representation

        (width, height, bitPackedInts...)
    */
    public List<Integer> packBits() {
        final List<Integer> bits = Arrays.asList(this.width, this.height);
        int currentInt = 0;
        for(int i=0; i<this.height * this.width; i++) {
            final int bit = CELLS_PER_INT - (i % CELLS_PER_INT) - 1;
            final int x = cellIndexToX(i);
            final int y = cellIndexToY(i);
            if(get(x,y)) {
                currentInt += Math.pow(2, bit);
            }
            if((i + 1) % CELLS_PER_INT == 0) {
                bits.add(currentInt);
                currentInt = 0;
            }
        }
        bits.add(currentInt);
        return bits;
    }
    
    private int cellIndexToX(final int index) {
        return index / height;
    }
    
    private int cellIndexToY(final int index) {
        return index % height;
    }
    
    /** Fills in data from a bit-level representation */
    private void unpackBits(final List<Integer> bits) {
        int cell = 0;
        for(int packed : bits) {
            for(boolean bit : unpackInt(packed, CELLS_PER_INT)) {
                if(cell == width * height) {
                    break;
                }
                final int x = cellIndexToX(cell);
                final int y = cellIndexToY(cell);
                this.set(x,y, bit);
                cell += 1;
            }
        }
    }
    
    
    private List<Boolean> unpackInt(int packed, final int size) {
        final List<Boolean> bools = new ArrayList();
        if(packed < 0) {
            throw new RuntimeException("Must be a positive integer");
        }
        for(int i=0; i<size; i++) {
            final int n = (int)Math.pow(2, (CELLS_PER_INT - i - 1));
            if(packed >= n) {
                bools.add(true);
                packed -= n;
            } else {
                bools.add(false);
            }
        }
        return bools;
    }
    
    
    @Override
    public Grid reconstituteGrid(final List<Integer> bitRep) {
        final int w = bitRep.get(0);
        final int h = bitRep.get(1);
        return new GridStandard(w, h, null, bitRep.subList(2, bitRep.size()));
    }

    @Override
    public boolean get(int x, int y) {
        return data.get(x).get(y);
    }

    @Override
    public int getWidth() {
        return data.size();
    }

    @Override
    public int getHeight() {
        return data.get(0).size();
    }

    @Override
    public void set(int x, int y, boolean bool) {
        data.get(x).set(y, bool);
    }
    
    @Override
    public String toString() {
        final List<List<Character>> out = new ArrayList<>();
        for(int y=0; y<height; y++) {
            final List<Character> row = new ArrayList<>();
            for(int x=0; x<width; x++) {
                row.add(get(x,y) ? 'T' : 'F');
            }
            out.add(row);
        }
        Collections.reverse(out);
        final StringBuilder sb = new StringBuilder();
        for(List<Character> lc : out) {
            sb.append(Util.concatStringList(lc, "")).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GridStandard other = (GridStandard) obj;
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.width;
        hash = 23 * hash + this.height;
        hash = 23 * hash + Objects.hashCode(this.data);
        return hash;
    }
    
}
