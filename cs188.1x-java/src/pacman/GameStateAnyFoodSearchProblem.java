package pacman;

import util.Position;

/**
 *
 * @author archie
 */
public class GameStateAnyFoodSearchProblem implements IGameState {
    
    private final Position pacmanPosition;
    
    public GameStateAnyFoodSearchProblem(final Position pacmanPosition) {
        this.pacmanPosition = pacmanPosition;
    }
    
    public Position getPacmanPosition() {
        return pacmanPosition;
    }
    
}