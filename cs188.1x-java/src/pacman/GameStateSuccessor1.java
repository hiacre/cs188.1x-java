package pacman;

/**
 *
 * @author archie
 */
public interface GameStateSuccessor1 {
    
    public Direction getAction();
    public GameState1 getState();
    
}
