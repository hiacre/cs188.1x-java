package pacman;

import util.Position;

/**
 *
 * @author archie
 */
public class GameStatePositionSearchProblem implements GameState {
    
    private final Position pacmanPosition;
    
    public GameStatePositionSearchProblem(Position pacmanPosition) {
        this.pacmanPosition = pacmanPosition;
    }
    
    public Position getPacmanPosition() {
        return pacmanPosition;
    }
}
