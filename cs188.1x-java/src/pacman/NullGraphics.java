package pacman;

/**
 *
 * @author archie
 */
public class NullGraphics {
    
    public NullGraphics(final GameState1 state, Boolean isBlue) {
        // if isBlue is null, then it should default to false
        // according to original Python (not that it seems to matter here)
    }
    

    public void update(final GameState1 state) {
    }

    public void pause() {
        time.sleep(SLEEP_TIME);
    }

    public void draw(final GameState1 state) {
        System.out.println(state);
    }

    public void finish() {
    }
}
