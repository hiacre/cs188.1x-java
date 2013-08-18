package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import util.Util;

/**
 * Implementation of a SearchProblem for the  Eight Puzzle domain

      Each state is represented by an instance of an eightPuzzle.
 * @author archie
 */
public class EightPuzzleSearchProblem implements SearchProblem<GameStateEightPuzzleSearchProblem, GameStateSuccessorEightPuzzleSearchProblem> {
    
    private final GameStateEightPuzzleSearchProblem puzzle;
    
    private final static List<List<Integer>> EIGHT_PUZZLE_DATA =
            Arrays.asList(
                Arrays.asList(1, 0, 2, 3, 4, 5, 6, 7, 8),
                Arrays.asList(1, 7, 8, 2, 3, 4, 5, 6, 0),
                Arrays.asList(4, 3, 2, 7, 0, 5, 1, 6, 8),
                Arrays.asList(5, 1, 3, 4, 0, 2, 6, 7, 8),
                Arrays.asList(1, 2, 5, 7, 6, 8, 0, 4, 3),
                Arrays.asList(0, 3, 1, 6, 8, 2, 7, 5, 4));

    /** Creates a new EightPuzzleSearchProblem which stores search information. */
    public EightPuzzleSearchProblem(final GameStateEightPuzzleSearchProblem puzzle) {
        this.puzzle = puzzle;
    }
    
    /**
     * puzzleNumber: The number of the eight puzzle to load.

          Returns an eight puzzle object generated from one of the
          provided puzzles in EIGHT_PUZZLE_DATA.

          puzzleNumber can range from 0 to 5.

          >>> print loadEightPuzzle(0)
          -------------
          | 1 |   | 2 |
          -------------
          | 3 | 4 | 5 |
          -------------
          | 6 | 7 | 8 |
          -------------
     * @param puzzleNumber
     * @return 
     */
    public static GameStateEightPuzzleSearchProblem loadEightPuzzle(final int puzzleNumber) {
        return new GameStateEightPuzzleSearchProblem(EIGHT_PUZZLE_DATA.get(puzzleNumber));
    }
    
    /**
     * moves: number of random moves to apply

          Creates a random eight puzzle by applying
          a series of 'moves' random moves to a solved
          puzzle.
     * @param moves
     * @return 
     */
    public static GameStateEightPuzzleSearchProblem createRandomEightPuzzle(Integer moves) {
          
        if(moves == null) {
            moves = 100;
        }
        
        GameStateEightPuzzleSearchProblem puzzle =
                new GameStateEightPuzzleSearchProblem(Arrays.asList(0,1,2,3,4,5,6,7,8));
        
        for(int i=0; i<moves; i++) {
            // Execute a random legal move
            final List<Direction> legalMoves = puzzle.legalMoves();
            final Direction legalMove = Util.randomChoice(legalMoves);
            final GameStateSuccessorEightPuzzleSearchProblem successor = puzzle.result(legalMove);
            puzzle = successor.getGameState();
        }
        return puzzle;
    }

    @Override
    public GameStateEightPuzzleSearchProblem getStartState() {
        return puzzle;
    }

    @Override
    public boolean isGoalState(final GameStateEightPuzzleSearchProblem state) {
        return state.isGoal();
    }

    /**
     * Returns list of (successor, action, stepCost) pairs where
          each succesor is either left, right, up, or down
          from the original state and the cost is 1.0 for each
     * @param state
     * @return 
     */
    @Override
    public List<GameStateSuccessorEightPuzzleSearchProblem> getSuccessors(final GameStateEightPuzzleSearchProblem state) {
        final List<GameStateSuccessorEightPuzzleSearchProblem> succ = new ArrayList<>();
        for(Direction a : state.legalMoves()) {
            succ.add(state.result(a));
        }
        return succ;
    }
    

    /**
     * actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.  The sequence must
        be composed of legal moves
     * @param actions
     * @return 
     */
    @Override
    public int getCostOfActions(final List<Direction> actions) {
        return actions.size();
    }
}
