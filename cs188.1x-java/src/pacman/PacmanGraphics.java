package pacman;

/**
 *
 * @author archie
 */
public interface PacmanGraphics {
    
    public void initialize(final GameState1 state, Boolean isBlue);

    public void update(GameState1 data);

    public void finish();
    
}
