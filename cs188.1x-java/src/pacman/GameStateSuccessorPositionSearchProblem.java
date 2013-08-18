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
public class GameStateSuccessorPositionSearchProblem implements GameStateSuccessor {
    
    private final Position position;
    private final Direction action;
    private final int cost;

    public GameStateSuccessorPositionSearchProblem(Position position, Direction action, int cost) {
        this.position = position;
        this.action = action;
        this.cost = cost;
    }
    
}
