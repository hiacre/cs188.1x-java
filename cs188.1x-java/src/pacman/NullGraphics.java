package pacman;

/**
 *
 * @author archie
 */
public class NullGraphics implements PacmanGraphics {
    
    public NullGraphics(final GameState1 state, Boolean isBlue) {
        // if isBlue is null, then it should default to false
        // according to original Python (not that it seems to matter here)
    }
    

    @Override
    public void update(final GameState1 state) {
    }

    private void pause() {
        time.sleep(SLEEP_TIME);
    }

    private void draw(final GameState1 state) {
        System.out.println(state);
    }

    @Override
    public void finish() {
    }

    @Override
    public void initialize(GameState1 state, Boolean isBlue) {
    }
}
