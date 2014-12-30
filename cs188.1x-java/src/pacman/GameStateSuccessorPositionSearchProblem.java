package pacman;

import common.Position;

public class GameStateSuccessorPositionSearchProblem implements GameStateSuccessor {
    
    private final Position position;
    private final Direction action;
    private final int cost;

    public GameStateSuccessorPositionSearchProblem(Position position, Direction action, int cost) {
        this.position = position;
        this.action = action;
        this.cost = cost;
    }
    
}
