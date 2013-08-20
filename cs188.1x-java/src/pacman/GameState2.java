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
    private final int width;
    private final int height;
    
    public GameState2(
            final Position position,
            final int width,
            final int height,
            final boolean visitedTopLeft,
            final boolean visitedTopRight,
            final boolean visitedBottomLeft,
            final boolean visitedBottomRight) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.tl = visitedTopLeft;
        this.tr = visitedTopRight;
        this.bl = visitedBottomLeft;
        this.br = visitedBottomRight;
        final int right = width;
        final int top = height;
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
            gs.getWidth(),
            gs.getHeight(),
            gs.getVisitedTopLeft(),
            gs.getVisitedTopRight(),
            gs.getVisitedBottomLeft(),
            gs.getVisitedBottomRight());
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
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public GameState2 copy(final GameState2 gameState) {
        return new GameState2(
                        gameState.getPosition(),
                        gameState.getWidth(),
                        gameState.getHeight(),
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
