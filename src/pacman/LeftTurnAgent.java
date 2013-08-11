/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.Collection;

/**
 * An agent that turns left at every opportunity
 * @author archie
 */
public class LeftTurnAgent implements Agent {

    @Override
    public Direction getAction(final GameState1 state) {
        final Collection<Direction> legal = state.getLegalPacmanActions();
        Direction current = state.getPacmanState().getConfiguration().getDirection();
        if(Direction.Stop.equals(current)) {
            current = Direction.North;
        }
        final Direction left = current.turnLeft();
        if(legal.contains(left)) {
            return left;
        }
        if(legal.contains(current)) {
            return current;
        }
        if(legal.contains(current.turnRight())) {
            return current.turnRight();
        }
        if(legal.contains(left.turnLeft())) {
            return left.turnLeft();
        }
        return Direction.Stop;
    }

}
