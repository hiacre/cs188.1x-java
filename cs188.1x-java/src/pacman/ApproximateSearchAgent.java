package pacman;

import util.PositionStandard;

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
        throw new UnsupportedOperationException();
    }

                
    /** Returns the maze distance between any two points, using the search functions
        you have already built.  The gameState can be any game state -- Pacman's position
        in that state is ignored.

        Example usage: mazeDistance( (2,4), (5,6), gameState)

        This might be a useful helper function for your ApproximateSearchAgent.
        */
    public int mazeDistance(final int x1, final int y1, final int x2, final int y2, final GameState1 gameState) {
        final Grid walls = gameState.getWalls();
        if(walls.get(x1, y1)) {
            throw new RuntimeException("point1 is a wall: " + PositionStandard.newInstance(x1, y1));
        }
        if(walls.get(x2, y2)) {
            throw new RuntimeException("point2 is a wall: " + PositionStandard.newInstance(x2, y2));
        }
        final PositionSearchProblem prob = new PositionSearchProblem(
                gameState,
                null,
                PositionStandard.newInstance(x2, y2),
                new GameStatePositionSearchProblem(gameState.getPacmanPosition()),
                false,
                false);
        return new BreadthFirstSearch().getActions(prob).size();
    }
}
