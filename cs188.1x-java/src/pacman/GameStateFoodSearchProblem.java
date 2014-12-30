package pacman;

import common.Position;

public class GameStateFoodSearchProblem implements GameState {

    private final Position position;
    private final Grid food;
    
    public GameStateFoodSearchProblem(Position position, Grid food) {
        this.position = position;
        this.food = food;
    }

    public Grid getFood() {
        return food;
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Position getPacmanPosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Grid getWalls() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
