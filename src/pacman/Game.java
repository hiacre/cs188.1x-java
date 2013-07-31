/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Util;


/**
 * The Game manages the control flow, soliciting actions from agents.
 * @author archie
 */
public class Game {
    private boolean agentCrashed;
    private final List agents;
    private final Object display;
    private final Object rules;
    private final int startingIndex;
    private boolean gameOver;
    private final boolean muteAgents;
    private final boolean catchExceptions;
    private final ArrayList<Object> moveHistory;
    private final List<Integer> totalAgentTimes;
    private final List<Integer> totalAgentTimeWarnings;
    
    private Object OLD_STDOUT = null;
    private Object OLD_STDERR = null;
    
    private final Logger logger = Logger.getLogger(this.getClass().getPackage().getName());
    private Object state;

    private Game(
            final List agents,
            final Object display,
            final Object rules,
            final Integer startingIndex,
            final Boolean muteAgents,
            final Boolean catchExceptions) {
     
        this.agentCrashed = false;
        this.agents = agents;
        this.display = display;
        this.rules = rules;
        this.startingIndex = (startingIndex == null ? 0 : startingIndex);
        this.gameOver = false;
        this.muteAgents = (muteAgents == null ? false : muteAgents);
        this.catchExceptions = (catchExceptions == null ? false : catchExceptions);
        this.moveHistory = new ArrayList<>();
        this.totalAgentTimes = Util.makeList(agents.size(), 0);
        this.totalAgentTimeWarnings = Util.makeList(agents.size(), 0);
    }

    public double getProgress() {
        if(gameOver) {
            return 1.0;
        } else {
            return rules.getProgress(this);
        }
    }
    
    /** Helper method for handling agent crashes */
    private void agentCrash(final Object agentIndex, final boolean quiet) {
        
        if(!quiet) {
            traceback.print_exc();
        }
        gameOver = true;
        agentCrashed = true;
        rules.agentCrash(this, agentIndex);
    }
    
    private void agentCrash(final Object agentIndex) {
        agentCrash(agentIndex, false);
    }


    /** Main control loop for game play. */
    public void run() {

        display.initialize(state.getData());
        final int numMoves = 0;

        // inform learning agents of the game start
        for(int i=0; i<agents.size(); i++) {
            agent = agents.get(i);
            if(agent == null) {
                // this is a null agent, meaning it failed to load
                // the other team wins
                logger.log(Level.SEVERE, "Agent {0} failed to load", i);
                agentCrash(i, true);
                return;
            }
            if(catchExceptions) {
                try {
                    
                    try {
                        agent.registerInitialState(state.copy(), new Timeout(rules.getMaxStartupTime(i)));
                    } catch(ExceptionTimeout ex) {
                        logger.log(Level.SEVERE, "Agent {0} ran out of time on startup", i);
                        agentCrash(i, true);
                        return;
                    }
                }
                catch(Exception ex) {
                    agentCrash(i, false);
                    return;
                }
            } else {
                agent.registerInitialState(state.copy());
            }
        }

        int agentIndex = startingIndex;
        final int numAgents = agents.size();

        while(!gameOver) {
            // Fetch the next agent
            final Object agent = agents.get(agentIndex);
            final int move_time = 0;
            final boolean skip_action = false;
            // Generate an observation of the state
            final Object observation = state.copy();

            // Solicit an action
            Object action = null;
            if(catchExceptions) {
                try {
                    Object start_time = time.time();
                    try {
                        if(skip_action) {
                            throw new ExceptionTimeout();
                        }
                        action = agent.getAction(observation, new Timeout(rules.getMoveTimeout(agentIndex) - move_time));
                    }
                    catch(ExceptionTimeout ex) {
                        logger.log(Level.SEVERE, "Agent {0} timed out on a single move", agentIndex);
                        agentCrash(agentIndex, true);
                        return;
                    }
                    
                    move_time += time.time() - start_time;

                    if(move_time > rules.getMoveWarningTime(agentIndex)) {
                        totalAgentTimeWarnings.add(totalAgentTimeWarnings.get(agentIndex) + 1);
                        logger.log(Level.WARNING, "Agent " + agentIndex + " took too long to make a move.  This is warning {0}", totalAgentTimeWarnings.get(agentIndex));
                        if(totalAgentTimeWarnings.get(agentIndex) > rules.getMaxTimeWarnings(agentIndex)) {
                            
                            logger.log(Level.SEVERE, "Agent " + agentIndex + " exceeded the maximum number of warnings: {0}", totalAgentTimeWarnings.get(agentIndex));
                            agentCrash(agentIndex, true);
                            return;
                        }
                    }

                    totalAgentTimes.add(totalAgentTimes.get(agentIndex) + move_time);
                    if(totalAgentTimes.get(agentIndex) > rules.getMaxTotalTime(agentIndex)) {
                        logger.log(Level.SEVERE, "Agent " + agentIndex + " ran out of time (time: {0})", totalAgentTimes.get(agentIndex));
                        agentCrash(agentIndex, true);
                        return;
                    }
                }
                catch(Exception ex) {
                    agentCrash(agentIndex);
                    return;
                }
            } else {
                action = agent.getAction(observation);
            }

            // Execute the action
            moveHistory.add( new AgentAction(agentIndex, action) );
            if(catchExceptions) {
                try {
                    state = state.generateSuccessor( agentIndex, action );
                }
                catch(Exception ex) {
                    agentCrash(agentIndex);
                    return;
                }
            } else {
                state = state.generateSuccessor( agentIndex, action );
            }

            // Change the display
            display.update( state.getData() );

            // Allow for game specific conditions (winning, losing, etc.)
            rules.process(state, this);
            // Track progress
            if(agentIndex == numAgents + 1) {
                numMoves += 1;
            }
            // Next agent
            agentIndex = ( agentIndex + 1 ) % numAgents;
        }
        display.finish();
}
