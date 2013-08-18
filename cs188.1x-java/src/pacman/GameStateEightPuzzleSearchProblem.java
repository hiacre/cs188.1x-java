package pacman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import util.Util;

/**
 * The Eight Puzzle is described in the course textbook on
    page 64.

    This class defines the mechanics of the puzzle itself.  The
    task of recasting this puzzle as a search problem is left to
    the EightPuzzleSearchProblem class.
 *
 * @author archie
 */
public class GameStateEightPuzzleSearchProblem implements IGameState {
    
    private final List<List<Integer>> cells;
    private Location blankLocation;
    final static String horizontalLine;
    
    static {
        final StringBuilder sb = new StringBuilder();
        for(int i=0; i<13; i++) {
            sb.append('-');
        }
        horizontalLine = sb.toString();
    }

    /**
     * Constructs a new eight puzzle from an ordering of numbers.

        numbers: a list of integers from 0 to 8 representing an
          instance of the eight puzzle.  0 represents the blank
          space.  Thus, the list

            [1, 0, 2, 3, 4, 5, 6, 7, 8]

          represents the eight puzzle:
            -------------
            | 1 |   | 2 |
            -------------
            | 3 | 4 | 5 |
            -------------
            | 6 | 7 | 8 |
            ------------

        The configuration of the puzzle is stored in a 2-dimensional
        list (a list of lists) 'cells'.
     * @param result
     * @param a
     * @param i 
     */
    public GameStateEightPuzzleSearchProblem(final List<Integer> nums) {
        cells = new ArrayList();
        final List<Integer> nums2 = new ArrayList(nums);  // Make a copy so as not to cause side-effects.
        Collections.reverse(nums2);
        final Stack<Integer> numbers = new Stack<>();
        for(int num : nums2) {
            numbers.push(num);
        }
        for(int row=0; row<3; row++) {
            this.cells.add(new ArrayList());
            for(int col=0; col<3; col++) {
                this.cells.get(row).add( numbers.pop() );
                if(this.cells.get(row).get(col) == 0) {
                    this.blankLocation = new Location(row, col);
                }
            }
        }
    }
    
    private GameStateEightPuzzleSearchProblem(final List<List<Integer>> nums, final Location blankLocation) {
        this.cells = nums;
        this.blankLocation = blankLocation;
    }
    
    
    private class Location {
        private final int row;
        private final int col;
        Location(final int row, final int col) {
            this.row = row;
            this.col = col;
        }
        public int getRow() { return row; }
        public int getCol() { return col; }
    }

    /**
     *           Checks to see if the puzzle is in its goal state.

            -------------
            |   | 1 | 2 |
            -------------
            | 3 | 4 | 5 |
            -------------
            | 6 | 7 | 8 |
            -------------

        >>> EightPuzzleState([0, 1, 2, 3, 4, 5, 6, 7, 8]).isGoal()
        True

        >>> EightPuzzleState([1, 0, 2, 3, 4, 5, 6, 7, 8]).isGoal()
        False
     * @return 
     */
    public boolean isGoal() {
        int current = 0;
        for(int row=0; row<3; row++) {
            for(int col=0; col<3; col++) {
                if(current != this.cells.get(row).get(col)) {
                    return false;
                }
                current += 1;
            }
        }
        return true;
    }

    /**
     * Returns a list of legal moves from the current state.

        Moves consist of moving the blank space up, down, left or right.
        These are encoded as 'up', 'down', 'left' and 'right' respectively.

        >>> EightPuzzleState([0, 1, 2, 3, 4, 5, 6, 7, 8]).legalMoves()
        ['down', 'right']
     * @return 
     */
    public List<Direction> legalMoves() {
        final List<Direction> moves = new ArrayList();
        final Location location = this.blankLocation;
        final int row = location.getRow();
        final int col = location.getCol();
        if(row != 0) {
            moves.add(Direction.North);
        }
        if(row != 2) {
            moves.add(Direction.South);
        }
        if(col != 0) {
            moves.add(Direction.West);
        }
        if(col != 2) {
            moves.add(Direction.East);
        }
        return moves;
    }
    
    /**
     * Returns a new eightPuzzle with the current state and blankLocation
        updated based on the provided move.

        The move should be a string drawn from a list returned by legalMoves.
        Illegal moves will raise an exception, which may be an array bounds
        exception.

        NOTE: This function *does not* change the current object.  Instead,
        it returns a new object.
     * @param direction
     * @return 
     */
    public GameStateSuccessorEightPuzzleSearchProblem result(final Direction move) {
        final int row = this.blankLocation.getRow();
        final int col = this.blankLocation.getCol();
        final int newrow;
        final int newcol;
        switch(move) {
            case North:
                newrow = row-1;
                newcol = col;
                break;
            case South:
                newrow = row+1;
                newcol = col;
                break;
            case West:
                newrow = row;
                newcol = col-1;
                break;
            case East:
                newrow = row;
                newcol = col+1;
                break;
            default:
                throw new RuntimeException("Illegal move");
        }


        // copy this.cells
        final List<List<Integer>> cellsCopy = new ArrayList<>();
        for(List<Integer> li : cells) {
            cellsCopy.add(new ArrayList<>(li));
        }
        // And update it to reflect the move
        cellsCopy.get(row).set(col, this.cells.get(newrow).get(newcol));
        cellsCopy.get(newrow).set(newcol, this.cells.get(row).get(col));
        return new GameStateSuccessorEightPuzzleSearchProblem(
                new GameStateEightPuzzleSearchProblem(cellsCopy, new Location(newrow, newcol)),
                move,
                1);
    }
    

    // Utilities for comparison and display
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameStateEightPuzzleSearchProblem other = (GameStateEightPuzzleSearchProblem) obj;
        if (!Objects.equals(this.cells, other.cells)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.cells);
        return hash;
    }

    /** Returns a display string for the maze */
    @Override
    public String toString() {
        final List lines = new ArrayList();
        
        lines.add(horizontalLine);
        for(List<Integer> row : this.cells) {
            final StringBuilder rowLine = new StringBuilder().append('|');
            for(int col : row) {
                if(col == 0) {
                    col = ' ';
                }
                rowLine.append(' ').append(col==0?' ':col).append(" |");
            }
            lines.add(rowLine);
            lines.add(horizontalLine);
        }
        return Util.concatStringList(lines, "\n");
    }
            
}
