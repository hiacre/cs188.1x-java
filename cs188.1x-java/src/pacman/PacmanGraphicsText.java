package pacman;

import common.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacmanGraphicsText extends TextDisplay {
    
    private int agentCounter;
    private int turn;
    private final int sleepTime;
    
    public PacmanGraphicsText(final Integer speed) {
        sleepTime = speed == null ? getSleepTime() : speed;
    }

    @Override
    public void initialize(final GameState1 state, Boolean isBlue) {
        // if isBlue is null, then it should default to false
        // according to original Python (not that it seems to matter here)
        this.draw(state);
        this.pause();
        this.turn = 0;
        this.agentCounter = 0;
    }

    @Override
    public void update(final GameState1 state) {
        final int numAgents = state.getNumAgents();
        this.agentCounter = (this.agentCounter + 1) % numAgents;
        if(this.agentCounter == 0) {
            this.turn += 1;
            if(movesAreDisplayed()) {
                List<Object> ghosts = new ArrayList<>();
                for(int i=1; i<numAgents; i++) {
                    final Position pos = state.getGhostPosition(i);
                    ghosts.add(Position.nearestPoint(pos.getX(), pos.getY()));
                }
                final StringBuilder sb = new StringBuilder();
                sb.append(String.format("%4d) P: %-8s", this.turn, Position.nearestPoint(state.getPacmanPosition())));
                sb.append(String.format("| Score: %-5d", state.getScore()));
                sb.append("| Ghosts:").append(ghosts);
                System.out.println(sb.toString());
            }
            if(this.turn % getDrawEvery() == 0) {
                this.draw(state);
                this.pause();
            }
        }
        if(state.isWin() || state.isLose()) {
            this.draw(state);
        }
    }

    private void pause() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(PacmanGraphicsText.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void draw(final GameState1 state) {
        System.out.println(state);
    }

    @Override
    public void finish() {
    }
}
