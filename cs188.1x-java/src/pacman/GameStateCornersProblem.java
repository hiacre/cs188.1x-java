package pacman;

import java.util.List;
import util.Position;

/**
 *
 * @author archie
 */
public interface GameStateCornersProblem extends IGameState {

    public Grid getWalls();
    
    public Position getPacmanPosition();

    public boolean hasFood(int x, int y);

    public List<Boolean> getCornersState();

}
