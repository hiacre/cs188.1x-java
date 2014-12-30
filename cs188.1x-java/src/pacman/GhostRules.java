package pacman;

import common.Position;
import java.util.List;

/**
 * These functions dictate how ghosts interact with their environment.
 */
public class GhostRules {

    private static final double GHOST_SPEED = 1.0;
    private static final double COLLISION_TOLERANCE = 0.7;
    
    /** Ghosts cannot stop, and cannot turn around unless they
        reach a dead end, but can turn 90 degrees at intersections. */
    public static List<Direction> getLegalActions(final GameState1 state, final int ghostIndex) {
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

    public static void applyAction(final GameState1 state, final Direction action, final int ghostIndex) {
        final List<Direction> legal = GhostRules.getLegalActions(state, ghostIndex);
        if(!legal.contains(action)) {
            throw new RuntimeException("Illegal ghost action " + action.toString());
        }

        final GhostState ghostState = (GhostState)state.getData().getAgentStates().get(ghostIndex);
        double speed = getGhostSpeed();
        if(ghostState.getScaredTimer() > 0) {
            speed /= 2.0;
        }
        final DirectionVector vector = action.toVector(speed);
        ghostState.setConfiguration(ghostState.getConfiguration().generateSuccessor( vector ));
    }
    
    private static double getGhostSpeed() {
        return GHOST_SPEED;
    }

    public static void decrementTimer(final GhostState ghostState) {
        final int timer = ghostState.getScaredTimer();
//        if(timer == 1) {
//            ghostState.getConfiguration().setPosition(PositionStandard.nearestPoint(ghostState.getConfiguration().getPosition()));
//        }
        ghostState.setScaredTimer(Math.max(0, timer - 1));
    }

    public static void checkDeath(final GameState1 state, final int agentIndex) {
        final Position pacmanPosition = state.getPacmanPosition();
        if(agentIndex == 0) { // Pacman just moved; Anyone can kill him
            for(int index = 1; index < state.getData().getAgentStates().size(); index++) {
                final GhostState ghostState = (GhostState)state.getData().getAgentStates().get(index);
                final Position ghostPosition = ghostState.getConfiguration().getPosition();
                if(GhostRules.canKill(pacmanPosition, ghostPosition)) {
                    GhostRules.collide(state, ghostState, index);
                }
            }
        } else {
            final GhostState ghostState = (GhostState)state.getData().getAgentStates().get(agentIndex);
            final Position ghostPosition = ghostState.getConfiguration().getPosition();
            if(GhostRules.canKill(pacmanPosition, ghostPosition)) {
                GhostRules.collide(state, ghostState, agentIndex);
            }
        }
    }

    private static void collide(final GameState1 state, final GhostState ghostState, final int agentIndex) {
        if(ghostState.getScaredTimer() > 0) {
            state.getData().setScoreChange(state.getData().getScoreChange() + 200);
            GhostRules.placeGhost(ghostState);
            ghostState.setScaredTimer(0);
            // Added for first-person
            // TODO perhaps remove getEaten and introduce setEaten?
            state.getData().getEaten().set(agentIndex, true);
        } else {
            if(!state.getData().isWin()) {
                state.getData().scoreAdd(-500);
                // TODO do we really need to set this here?  Can't GameSetData tell?
                state.getData().setIsLose(true);
            }
        }
    }

    private static boolean canKill(final Position pacmanPosition, final Position ghostPosition) {
        return ghostPosition.manhattanDistance(pacmanPosition) <= getCollisionTolerance();
    }

    /** How close ghosts must be to Pacman to kill */
    private static double getCollisionTolerance() {
        return COLLISION_TOLERANCE;
    }
    
    private static void placeGhost(final GhostState ghostState) {
        ghostState.setConfiguration(ghostState.getStart());
    }
}
