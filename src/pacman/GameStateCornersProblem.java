/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public boolean hasFood(Position corner);

    public List<Boolean> getCornersState();

}
