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
public class GameStateCornersProblemStandard implements GameStateCornersProblem {

    private final Grid walls;
    private final Position pacmanPosition;
    private final List cornersState;
    
    public GameStateCornersProblemStandard(final Grid walls, final Position pacmanPosition, final List cornersState) {
        this.walls = walls;
        this.pacmanPosition = pacmanPosition;
        this.cornersState = cornersState;
    }

    @Override
    public Grid getWalls() {
        return walls;
    }

    @Override
    public Position getPacmanPosition() {
        return pacmanPosition;
    }

    @Override
    public boolean hasFood(final int x, final int y) {
        return walls.get(x,y);
    }
    
    @Override
    public List getCornersState() {
        return this.cornersState;
    }
}
