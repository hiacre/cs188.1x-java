package pacman;

import java.util.List;
import util.Position;

/**
 *
 * @author archie
 */
public class GameStateCornersProblem {

    private final Grid walls;
    private final Position pacmanPosition;
    private final List cornersState;
    
    public GameStateCornersProblem(final Grid walls, final Position pacmanPosition, final List cornersState) {
        this.walls = walls;
        this.pacmanPosition = pacmanPosition;
        this.cornersState = cornersState;
    }

    public Grid getWalls() {
        return walls;
    }

    public Position getPacmanPosition() {
        return pacmanPosition;
    }

    public boolean hasFood(final int x, final int y) {
        return walls.get(x,y);
    }
    
    public List getCornersState() {
        return this.cornersState;
    }
}
