package pacman;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.Position;

/**
 ******************
 * Mini-contest 1 *
 ******************
 * Implement your contest entry here.  Change anything but the class name.
 * @author archie
 */
public class ApproximateSearchAgent extends AgentAbstract {

    public ApproximateSearchAgent() {
        super(null);
    }
            
    public void registerInitialState(final Object state) {
        /** This method is called before any moves are made. **/
        /*** YOUR CODE HERE ***/
    }

    @Override
    public Direction getAction(final GameState1 state) {
        /*
        From game.py:
        The Agent will receive a GameState and must return an action from
        the Direction enum
        */
        /*** YOUR CODE HERE ***/
        throw new NotImplementedException();
    }

                
    /** Returns the maze distance between any two points, using the search functions
        you have already built.  The gameState can be any game state -- Pacman's position
        in that state is ignored.

        Example usage: mazeDistance( (2,4), (5,6), gameState)

        This might be a useful helper function for your ApproximateSearchAgent.
        */
    public int mazeDistance(final Position point1, final Position point2, final GameState1 gameState) {
        final int x1 = point1.getX();
        final int y1 = point1.getY();
        final int x2 = point2.getX();
        final int y2 = point2.getY();
        final Grid walls = gameState.getWalls();
        if(walls.get(x1, y1)) {
            throw new RuntimeException("point1 is a wall: " + point1.toString());
        }
        if(walls.get(x2, y2)) {
            throw new RuntimeException("point2 is a wall: " + point2.toString());
        }
        final PositionSearchProblem prob = new PositionSearchProblem(
                gameState,
                null,
                point2,
                gameState,
                false,
                false);
        return new BreadthFirstSearch().getActions(prob).size();
    }
}
