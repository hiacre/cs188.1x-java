package pacman;

public abstract class TextDisplay implements PacmanDisplay {
    
    private final static int DRAW_EVERY = 1;
    private final static int SLEEP_TIME = 0;
    private final static boolean DISPLAY_MOVES = false;
    
    protected int getSleepTime() {
        return SLEEP_TIME;
    }
    protected boolean movesAreDisplayed() {
        return DISPLAY_MOVES;
    }
    protected int getDrawEvery() {
        return DRAW_EVERY;
    }
}
