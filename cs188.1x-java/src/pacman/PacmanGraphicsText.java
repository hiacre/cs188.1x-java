package pacman;

import common.Position;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author archie
 */
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
                print "%4d) P: %-8s" % (self.turn, str(pacman.nearestPoint(state.getPacmanPosition())));
                print '| Score: %-5d' % state.score,'| Ghosts:', ghosts;
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
        time.sleep(sleepTime);
    }

    private void draw(final GameState1 state) {
        System.out.println(state);
    }

    @Override
    public void finish() {
    }
}
