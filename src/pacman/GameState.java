/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import util.Position;
import util.Util;

/**
 *     A GameState specifies the full game state, including the food, capsules,
    agent configurations and score changes.

    GameStates are used by the Game object to capture the actual state of the game and
    can be used by agents to reason about the game.

    Much of the information in a GameState is stored in a GameStateData object.  We
    strongly suggest that you access that data via the accessor methods below rather
    than referring to the GameStateData object directly.

    Note that in classic Pacman, Pacman is always agent 0.
 * @author archie
 */
public class GameState {
    
    private static Set<GameState> explored = new HashSet<>();
    final private GameStateData data;
    
    /** Generates a new state by copying information from its predecessor. */
    public GameState(final GameState prevState) {
        if(prevState != null) { // Initial state
            data = GameStateData(prevState.getData());
        } else {
            data = new GameStateData();
        }
    }
    
    
    public static Set<GameState> getAndResetExplored() {
        final Set<GameState> temp = new HashSet<>();
        temp.addAll(explored);
        explored.clear();
        return temp;
    }
    
    
    /** Returns the legal actions for the agent specified. */
    public Object getLegalActions(Integer agentIndex) {
        
        if(agentIndex == null) {
            agentIndex = 0;
        }
        
        explored.add(this);
        
        if(isWin() || isLose()) {
            return new ArrayList<Object>();
        }
        
        if(agentIndex == 0) {
            return PacmanRules.getLegalActions(this);
        } else {
            return GhostRules.getLegalActions(this, agentIndex);
        }
    }
    
    /** Returns the successor state after the specified agent takes the action. */
    public void generateSuccessor(final int agentIndex, final Action action) {
        // Check that successors exist
        if(isWin() || isLose()) {
            throw new RuntimeException("Can't generate a successor of a terminal state.");
        }

        // Copy current state
        final GameState state = new GameState(this);

        // Let agent's logic deal with its action's effects on the board
        if(agentIndex == 0) {
            // Pacman is moving
            state.getData().setEaten(Util.makeList(state.getNumAgents(), false));
            PacmanRules.applyAction(state, action);
        } else {
            // A ghost is moving
            GhostRules.applyAction(state, action, agentIndex);
        }

        // Time passes
        if(agentIndex == 0) {
            // Penalty for waiting around
            state.getData().setScoreChange(state.getData().getScoreChange() - TIME_PENALTY);
        } else {
            GhostRules.decrementTimer( state.getData().getAgentStates().get(agentIndex));
        }

        // Resolve multi-agent effects
        GhostRules.checkDeath(state, agentIndex);

        // Book keeping
        state.getData().setAgentMoved(agentIndex);
        state.getData().setscore(state.getData().getScore() + state.getData().getScoreChange());
        return state;
    }
    

    public Object getLegalPacmanActions() {
        return getLegalActions(0);
    }

    /** Generates the successor state after the specified pacman move */
    public Object generatePacmanSuccessor(final Action action) {
        return generateSuccessor(0, action);
    }
    

    /** Returns an AgentState object for pacman (in game.py)

        state.pos gives the current position
        state.direction gives the travel vector
    */
    public Object getPacmanState() {
        return data.getAgentStates().get(0).copy();
    }
    

    public Position getPacmanPosition() {
        return data.getAgentStates().get(0).getPosition();
    }

    public List<AgentState> getGhostStates() {
        List<AgentState> ghostStates = new ArrayList<>();
        for(AgentState state : data.getAgentStates()) {
            ghostStates.add(state);
        }
        ghostStates.remove(0);
        return ghostStates;
    }

    public AgentState getGhostState(final int agentIndex) {
        if(agentIndex == 0 || agentIndex >= getNumAgents()) {
            throw new RuntimeException("Invalid index passed to getGhostState");
        }
        return data.getAgentStates().get(agentIndex);
    }

    public Position getGhostPosition(final int agentIndex) {
        if(agentIndex == 0) {
            throw new RuntimeException("Pacman's index passed to getGhostPosition");
        }
        return data.getAgentStates().get(agentIndex).getPosition();
    }
    
    public List getGhostPositions() {
        
        final List result = new ArrayList();
        for(AgentState s : getGhostStates()) {
            result.add(s.getPosition());
        }
        return result;
    }

    public int getNumAgents() {
        return data.getAgentStates().size();
    }

    public int getScore() {
        return data.getScore();
    }

    /** Returns a list of positions (x,y) of the remaining capsules. */
    public List<Position> getCapsules() {
        return data.getCapsules();
    }

    public int getNumFood() {
        return data.getFood().getCount();
    }

    /**
     * Returns a Grid of boolean food indicator variables.

        Grids can be accessed via list notation, so to check
        if there is food at (x,y), just call

        currentFood = state.getFood()
        if currentFood[x][y] == True: ...
     */
    public Object getFood(self) {
        return data.getFood();
    }

    
    /**
     * Returns a Grid of boolean wall indicator variables.

        Grids can be accessed via list notation, so to check
        if there is a wall at (x,y), just call

        walls = state.getWalls()
        if walls[x][y] == True: ...
     */
    public Object getWalls() {
        return data.getLayout().getWalls();
    }

    public boolean hasFood(final int x, final int y) {
        return data.getFood().get(x).get(y);
    }

    public boolean hasWall(final int x, final int y) {
        return data.getLayout().getWalls.get(x).get(y);
    }

    public boolean isLose() {
        return data.isLose();
    }

    public boolean isWin() {
        return data.isWin();
    }

    /**
    *             Helper methods:
    * You shouldn't need to call these directly
    */


    public GameState deepCopy() {
        final GameState state = new GameState();
        state.setData(data.deepCopy());
        return state;
    }

    public boolean equals(Object other) {
        return data == other.data;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return data.toString();
    }

    /** Creates an initial game state from a layout array (see layout.py). */
    public void initialize(layout, Integer numGhostAgents) {
        if(numGhostAgents == null) {
            numGhostAgents = 1000;
        }
    
        data.initialize(layout, numGhostAgents);
    }

    private GameStateData getData() {
        return data;
    }

}
