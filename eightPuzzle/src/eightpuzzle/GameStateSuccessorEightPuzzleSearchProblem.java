package eightpuzzle;

import pacman.Direction;
import pacman.GameStateSuccessor;

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

    public GameStateEightPuzzleSearchProblem getGameState() {
        return gameState;
    }

    public Direction getMove() {
        return move;
    }

    public int getCost() {
        return cost;
    }
    
}
