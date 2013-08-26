package pacman;

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

    // TODO this is very similar to super.initialize().  There's a better way to do this.
    private void initialize(final GameState1 state, Boolean isBlue) {

        this.isBlue = isBlue == null ? false : isBlue;
        
        PacmanGraphicsNonText.startGraphics(state);
        # Initialize distribution images
        walls = state.layout.walls
        dist = []
        self.layout = state.layout

        # Draw the rest
        self.distributionImages = None  # initialize lazily
        self.drawStaticObjects(state)
        self.drawAgentObjects(state)

        # Information
        self.previousState = state

    def lookAhead(self, config, state):
        if config.getDirection() == 'Stop':
            return
        else:
            pass
            # Draw relevant ghosts
            allGhosts = state.getGhostStates()
            visibleGhosts = state.getVisibleGhosts()
            for i, ghost in enumerate(allGhosts):
                if ghost in visibleGhosts:
                    self.drawGhost(ghost, i)
                else:
                    self.currentGhostImages[i] = None

    def getGhostColor(self, ghost, ghostIndex):
        return GHOST_COLORS[ghostIndex]

    def getPosition(self, ghostState):
        if not self.showGhosts and not ghostState.isPacman and ghostState.getPosition()[1] > 1:
            return (-1000, -1000)
        else:
            return PacmanGraphics.getPosition(self, ghostState)

    def add(x, y):
        return (x[0] + y[0], x[1] + y[1])

}
