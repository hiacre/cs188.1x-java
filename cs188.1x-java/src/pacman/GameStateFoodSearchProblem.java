/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import util.Position;

/**
 *
 * @author archie
 */
public interface GameStateFoodSearchProblem {
    
    public Grid getFood();

    /** Has all the food gone? */
    public boolean isEmpty();

    public Position getPacmanPosition();

    public Grid getWalls();
    
}
