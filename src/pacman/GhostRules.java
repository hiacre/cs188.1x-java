/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.List;

/**
 * These functions dictate how ghosts interact with their environment.
 * @author archie
 */
public class GhostRules {

    private static final double GHOST_SPEED = 1.0;
    
    /** Ghosts cannot stop, and cannot turn around unless they
        reach a dead end, but can turn 90 degrees at intersections. */
    private static List<Direction> getLegalActions(final GameState state, final int ghostIndex) {
        final Configuration conf = state.getGhostState(ghostIndex).getConfiguration();
        final List<Direction> possibleActions = Actions.getPossibleActions(conf, state.getData().getLayout().getWalls());
        final Direction reverse = conf.getDirection().getReverse();
        if(possibleActions.contains(Direction.Stop)) {
            possibleActions.remove(Direction.Stop);
        }
        if(possibleActions.contains(reverse) && possibleActions.size() > 1) {
            possibleActions.remove(reverse);
        }
        return possibleActions;
    }

    public static void applyAction(final GameState state, final Direction action, final int ghostIndex) {
        final List<Direction> legal = GhostRules.getLegalActions(state, ghostIndex);
        if(!legal.contains(action)) {
            throw new RuntimeException("Illegal ghost action " + action.toString());
        }

        final AgentState ghostState = state.getData().getAgentStates().get(ghostIndex);
        double speed = getGhostSpeed();
        if(ghostState.getScaredTimer() > 0) {
            speed /= 2.0;
        }
        final DirectionVector vector = action.toVector(speed);
        ghostState.configuration = ghostState.configuration.generateSuccessor( vector );
    }
    
    public static double getGhostSpeed() {
        return GHOST_SPEED;
    }

    def decrementTimer( ghostState):
        timer = ghostState.scaredTimer
        if timer == 1:
            ghostState.configuration.pos = nearestPoint( ghostState.configuration.pos )
        ghostState.scaredTimer = max( 0, timer - 1 )
    decrementTimer = staticmethod( decrementTimer )

    def checkDeath( state, agentIndex):
        pacmanPosition = state.getPacmanPosition()
        if agentIndex == 0: # Pacman just moved; Anyone can kill him
            for index in range( 1, len( state.data.agentStates ) ):
                ghostState = state.data.agentStates[index]
                ghostPosition = ghostState.configuration.getPosition()
                if GhostRules.canKill( pacmanPosition, ghostPosition ):
                    GhostRules.collide( state, ghostState, index )
        else:
            ghostState = state.data.agentStates[agentIndex]
            ghostPosition = ghostState.configuration.getPosition()
            if GhostRules.canKill( pacmanPosition, ghostPosition ):
                GhostRules.collide( state, ghostState, agentIndex )
    checkDeath = staticmethod( checkDeath )

    def collide( state, ghostState, agentIndex):
        if ghostState.scaredTimer > 0:
            state.data.scoreChange += 200
            GhostRules.placeGhost(state, ghostState)
            ghostState.scaredTimer = 0
            # Added for first-person
            state.data._eaten[agentIndex] = True
        else:
            if not state.data._win:
                state.data.scoreChange -= 500
                state.data._lose = True
    collide = staticmethod( collide )

    def canKill( pacmanPosition, ghostPosition ):
        return manhattanDistance( ghostPosition, pacmanPosition ) <= COLLISION_TOLERANCE
    canKill = staticmethod( canKill )

    def placeGhost(state, ghostState):
        ghostState.configuration = ghostState.start
    placeGhost = staticmethod( placeGhost )
}
