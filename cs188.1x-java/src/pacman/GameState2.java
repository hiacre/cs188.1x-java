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
public class GameState2 {
    private final Position position;
    private boolean tl;
    private boolean tr;
    private boolean bl;
    private boolean br;
    private final Position size;
    
    public GameState2(
            final Position position,
            final Position size,
            final boolean visitedTopLeft,
            final boolean visitedTopRight,
            final boolean visitedBottomLeft,
            final boolean visitedBottomRight) {
        this.position = position;
        this.tl = visitedTopLeft;
        this.tr = visitedTopRight;
        this.bl = visitedBottomLeft;
        this.br = visitedBottomRight;
        this.size = size;
        final int right = size.getX();
        final int top = size.getY();
        if(position.getX() == 1 && position.getY() == 1) {
            this.bl = true;
        }
        if(position.getX() == right && position.getY() == 1) {
            this.br = true;
        }
        if(position.getX() == 1 && position.getY() == top) {
            this.tl = true;
        }
        if(position.getX() == right && position.getY() == top) {
            this.tr = true;
        }
    }
        

    public static GameState2 fromGameState(final GameState2 gameState) {
        final GameState2 gs = gameState;
        return new GameState2(
                 gs.getPosition(),
                 gs.getSize(),
                 gs.getVisitedTopLeft(),
                 gs.getVisitedTopRight(),
                 gs.getVisitedBottomLeft(),
                 gs.getVisitedBottomRight());
    }

    public Position getSize() {
        return size;
    }
    public Position getPosition() {
        return position;
    }
    public boolean getVisitedTopLeft() {
        return this.tl;
    }
    public boolean getVisitedTopRight() {
        return this.tr;
    }
    public boolean getVisitedBottomLeft() {
        return this.bl;
    }
    public boolean getVisitedBottomRight() {
        return this.br;
    }
    public GameState2 copy(final GameState2 gameState) {
        return new GameState2(
                        gameState.getPosition(),
                        gameState.getSize(),
                        gameState.getVisitedTopLeft(),
                        gameState.getVisitedTopRight(),
                        gameState.getVisitedBottomLeft(),
                        gameState.getVisitedBottomRight());
    }
    
    @Override
    public String toString() {
        final StringBuilder val = new StringBuilder();
        val.append("GameState Pos: ").append(this.position).append(" Visited: ");
        if(this.tl) {
            val.append("TL ");
        }
        if(this.tr) {
            val.append("TR ");
        }
        if(this.bl) {
            val.append("BL ");
        }
        if(this.br) {
            val.append("BR ");
        }
        return val.toString();
    }
}
