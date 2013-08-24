package pacman;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * These game rules manage the control flow of a game, deciding when
    and how the game starts and ends.
 * @author archie
 */
public class ClassicGameRules {
    
    private final int timeout;
    private GameState1 initialState;
    private boolean quiet;
    
    private final Logger logger = Logger.getLogger(getClass().getPackage().getName());

    public ClassicGameRules() {
        this.timeout = 30;
    }
    
    public ClassicGameRules(final int timeout) {
        this.timeout = timeout;
    }
    
    public Game newGame(
            final Layout layout,
            final Agent pacmanAgent,
            final List<GhostAgent> ghostAgents,
            final Object display,
            final Boolean quiet,
            final Boolean catchExceptions) {
        List agents = new ArrayList();
        agents.add(pacmanAgent);
        agents.addAll(ghostAgents.subList(0, layout.getNumGhosts()));
        final GameState1 initState = new GameState1();
        initState.initialize( layout, ghostAgents.size() );
        final Game game = new Game(agents, display, this, 0, false, catchExceptions == null ? false : catchExceptions);
        game.setState(initState);
        initialState = initState.deepCopy();
        this.quiet = (quiet == null ? false : quiet);
        return game;
    }
    
    public Game newGame(
            final Layout layout,
            final Agent pacmanAgent,
            final List<GhostAgent> ghostAgents,
            final Object display) {
        return newGame(layout, pacmanAgent, ghostAgents, display, false, false);
    }

    /** Checks to see whether it is time to end the game. */
    public void process(final GameState1 state, final Game game) {
        if(state.isWin()) {
            win(state, game);
        }
        if(state.isLose()) {
            lose(state, game);
        }
    }

    public void win(final GameState1 state, final Game game) {
        if(!quiet) {
            logger.log(Level.INFO, "Pacman emerges victorious! Score: {0}", state.getData().getScore());
        }
        game.setGameOver(true);
    }

    public void lose(final GameState1 state, final Game game) {
        if(!quiet) {
            logger.log(Level.INFO, "Pacman died! Score: {0}", state.getData().getScore());
            game.setGameOver(true);
        }
    }

    public double getProgress(final Game game) {
        return game.getState().getNumFood() / initialState.getNumFood();
    }

    public void agentCrash(final Game game, final int agentIndex) {
        if(agentIndex == 0) {
            logger.log(Level.SEVERE, "Pacman crashed");
        } else {
            logger.log(Level.SEVERE, "A ghost crashed");
        }
    }

    public int getMaxTotalTime(final int agentIndex) {
        return timeout;
    }
    

    public int getMaxStartupTime(final int agentIndex) {
        return timeout;
    }

    public int getMoveWarningTime(final int agentIndex) {
        return timeout;
    }

    public int getMoveTimeout(final int agentIndex) {
        return timeout;
    }

    public int getMaxTimeWarnings(final int agentIndex) {
        return 0;
    }

    void setQuiet(final boolean isQuiet) {
        this.quiet = isQuiet;
    }
}
