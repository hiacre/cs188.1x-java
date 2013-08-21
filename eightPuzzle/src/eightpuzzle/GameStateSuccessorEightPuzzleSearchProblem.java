package eightpuzzle;

import pacman.Direction;
import pacman.GameStateSuccessor;

/**
 *
 * @author archie
 */
public class GameStateSuccessorEightPuzzleSearchProblem implements GameStateSuccessor {
    
    private final GameStateEightPuzzleSearchProblem gameState;
    private final Direction move;
    private final int cost;

    public GameStateSuccessorEightPuzzleSearchProblem(
            final GameStateEightPuzzleSearchProblem gameState,
            final Direction move,
            final int cost) {
        this.gameState = gameState;
        this.move = move;
        this.cost = cost;
    }

    /**
     * @return the gameState
     */
    public GameStateEightPuzzleSearchProblem getGameState() {
        return gameState;
    }

    /**
     * @return the move
     */
    public Direction getMove() {
        return move;
    }

    /**
     * @return the cost
     */
    public int getCost() {
        return cost;
    }
    
}
