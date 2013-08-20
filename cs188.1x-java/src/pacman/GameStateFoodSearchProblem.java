package pacman;

import util.Position;

/**
 *
 * @author archie
 */
public interface GameStateFoodSearchProblem extends IGameState {
    
    public Grid getFood();

    /** Has all the food gone? */
    public boolean isEmpty();

    public Position getPacmanPosition();

    public Grid getWalls();
    
}
