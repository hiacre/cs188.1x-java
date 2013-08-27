package pacman;

import java.util.ArrayList;
import java.util.List;
import util.Position;

/**
 *
 * @author archie
 */
public class FirstPersonPacmanGraphics extends PacmanGraphicsNonText {
    private boolean showGhosts;

    public FirstPersonPacmanGraphics(final Double zoom, Boolean showGhosts, Boolean capture, Double frameTime) {
        super(zoom, frameTime, capture);
        this.showGhosts = showGhosts == null ? true : showGhosts;
    }


    @Override
    protected void initializeDistributionImages(final GameState1 state) {
        this.setLayout(state.getLayout());
    }
    

    public void lookAhead(final Configuration config, final GameState1 state) {
        // TODO invert this predicate and remove the branch
        if(Direction.Stop.equals(config.getDirection())) {
        } else {
            // Draw relevant ghosts
            final List<AgentState> allGhosts = state.getGhostStates();
            final List<AgentState> visibleGhosts = state.getVisibleGhosts();
            for(int i=0; i<allGhosts.size(); i++) {
                final AgentState ghost = allGhosts.get(i);
                if(visibleGhosts.contains(ghost)) {
                    this.drawGhost(ghost, i);
                } else {
                    this.setCurrentGhostImages(i, null);
                }
            }
        }
    }

    private String getGhostColor(final Object ghost, final int ghostIndex) {
        return GraphicsDisplay.GHOST_COLORS.get(ghostIndex);
    }

    private Position getPosition2(final AgentState ghostState) {
        if(!this.showGhosts && !ghostState.isPacman() && ghostState.getPosition().getX() > 1) {
            return Position.newInstance(-1000, -1000);
        } else {
            return PacmanGraphicsNonText.getPosition1(ghostState);
        }
    }

    /** TODO Does this take and return positions? */
    private List<Double> add(List<Double> x, List<Double> y) {
        List result = new ArrayList();
        result.add(x.get(0) + y.get(0));
        result.add(x.get(1) + y.get(1));
        return result;
    }

}
