/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.Position;

/**
 *
 * @author archie
 */
public class GameStateData {
    
    private final int scoreChange;
    private final boolean lose;
    private final boolean win;
    private final Object agentMoved;
    private final Object capsuleEaten;
    private final Object foodAdded;
    private final Object foodEaten;
    private List agentStates;
    private List<Position> capsules;
    private List<List<Boolean>> food;
    private Layout layout;
    private List eaten;
    private int score;
    
    public GameStateData() {
        this.foodEaten = null;
        this.foodAdded = null;
        this.capsuleEaten = null;
        this.agentMoved = null;
        this.lose = false;
        this.win = false;
        this.scoreChange = 0;
    }
    
    public GameStateData(final GameStateData prevState) {
        this.food = prevState.getFood().copy();
        this.capsules = prevState.getCapsules().copy();
        this.agentStates = prevState.getAgentStates().copy();
        this.layout = prevState.getLayout().copy();
        this.eaten = prevState.getEaten();
        this.score = prevState.getScore();
    }

    GameStateData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public GameStateData deepCopy() {
        return GameStateData.newInstance();
    }
    
    public List<AgentState> copyAgentStates(final List<AgentState> agentStates) {
        final List<AgentState> result = new ArrayList<>();
        for(AgentState state : agentStates) {
            result.add(state.copy());
        }
        return result;
    }
    
    @Override
    public boolean equals(Object other) {
        throw new NotImplementedException();
    }

    @Override
    public int hashCode() {
        throw new NotImplementedException();
    }
    
    @Override
    public String toString() {
        throw new NotImplementedException();
    }
    
    
    private char foodWallString(final boolean hasFood, final boolean hasWall) {
        if(hasFood) {
            return '.';
        } else if(hasWall) {
            return '%';
        } else {
            return ' ';
        }
    }

    private char pacString(final Direction dir) {
        switch(dir) {
            case North: return 'v';
            case South: return '^';
            case West: return '>';
            case East: return '<';
            default: throw new RuntimeException("Unhandled direction");
        }
    }
    
    private char ghostString(final Direction dir) {
        switch(dir) {
            case North: return 'M';
            case South: return 'W';
            case West: return '3';
            case East: return '£';
            default: throw new RuntimeException("Unhandled direction");
        }
    }
    
    /**
     * Creates an initial game state from a layout array (see layout.py).
     */
    public void initialize(final Object layout, final Object numGhostAgents) {
        this.food = layout.getFood().copy();
        this.capsules = layout.getCapsules().copy();
        this.layout = layout;
        this.score = 0;
        this.scoreChange = 0;

        agentStates = new ArrayList<>();
        int numGhosts = 0;
        for(AgentPosition pos : layout.getAgentPositions()) {
            if(pos.isPacman()) {
                if(numGhosts == numGhostAgents) {
                    continue;  // Max ghosts reached already
                }
                numGhosts++;
            }
            agentStates.add( new AgentState( Configuration( pos, Direction.Stop), pos.isPacman()) );
        }
        eaten = new ArrayList<>();
        for(Object o : agentStates) {
            eaten.add(false);
        }
    }
    
    public List<List<Boolean>> getFood() {
        return food;
    }

    public List<Position> getCapsules() {
        return capsules;
    }

    public List<AgentState> getAgentStates() {
        return agentStates;
    }

    public Layout getLayout() {
        return layout;
    }

    private List getEaten() {
        return eaten;
    }

    public int getScore() {
        return score;
    }
}
