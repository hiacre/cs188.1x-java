package pacman;

import common.Position;

/**
 *
 * @author archie
 */
public class GameStateAnyFoodSearchProblem implements GameState {
    
    private final Position pacmanPosition;
    
    public GameStateAnyFoodSearchProblem(final Position pacmanPosition) {
        this.pacmanPosition = pacmanPosition;
    }
    
    public Position getPacmanPosition() {
        return pacmanPosition;
    }
    
}
