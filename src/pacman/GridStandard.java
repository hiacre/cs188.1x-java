/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.Position;
import util.PositionStandard;
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

    private GridStandard(final int width, final int height, Boolean initialValue, final List<Integer> bitRepresentation) {
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
    
    public static Grid newInstance(final int width, final int height, final boolean initialValue) {
        return new GridStandard(width, height, initialValue, null);
    }
        
    public static GridStandard newInstance(
            final int width,
            final int height,
            final Map<Direction, Set<Object>> mapDirectionSet) {
        return new GridStandard();
    }
    
    @Override
    public Grid copy() {
        g = Grid(self.width, self.height)
        g.data = [x[:] for x in self.data]
        return g
    }

    @Override
    public Grid deepCopy() {
        return self.copy()
    }

    @Override
    public Grid shallowCopy() {
        g = Grid(self.width, self.height)
        g.data = self.data
        return g
    }

    @Override
    public int getCount() {
        return sum([x.count(item) for x in self.data])
    }

    @Override
    public List<Position> asList() {
        list = []
        for x in range(self.width):
            for y in range(self.height):
                if self[x][y] == key: list.append( (x,y) )
        return list
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
            final Position pos = cellIndexToPosition(i);
            final int x = pos.getX();
            final int y = pos.getY();
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
    
    private Position cellIndexToPosition(final int index) {
        final int x = index / height;
        final int y = index % height;
        return PositionStandard.newInstance(x, y);
    }
    
    /** Fills in data from a bit-level representation */
    private void unpackBits(final List<Integer> bits) {
        int cell = 0;
        for(int packed : bits) {
            for(boolean bit : unpackInt(packed, CELLS_PER_INT)) {
                if(cell == width * height) {
                    break;
                }
                final Position pos = cellIndexToPosition(cell);
                final int x = pos.getX();
                final int y = pos.getY();
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
    
    public int hashCode() {
        
    }
    
    public boolean equals(Object o) {
        
    }
}
