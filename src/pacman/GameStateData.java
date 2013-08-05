/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author archie
 */
public class GameStateData {
    
    private int scoreChange;
    private final boolean lose;
    private final boolean win;
    private Integer agentMoved;
    private final Object capsuleEaten;
    private final Object foodAdded;
    private final Object foodEaten;
    private List agentStates;
    private Grid capsules;
    private Grid food;
    private Layout layout;
    private List eaten;
    private int score;
    
    public GameStateData() {
        foodEaten = null;
        foodAdded = null;
        capsuleEaten = null;
        agentMoved = null;
        lose = false;
        win = false;
        scoreChange = 0;
    }
    
    public GameStateData(final GameStateData prevState) {
        food = prevState.getFood().copy();
        capsules = prevState.getCapsules().copy();
        agentStates = prevState.getAgentStates().copy();
        layout = prevState.getLayout().copy();
        eaten = prevState.getEaten();
        score = prevState.getScore();
    }
    
    public GameStateData deepCopy() {
        throw new NotImplementedException();
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
    public void initialize(final Layout layout, final Object numGhostAgents) {
        this.food = layout.getFood().copy();
        capsules = layout.getCapsules().copy();
        this.layout = layout;
        this.score = 0;
        scoreChange = 0;

        agentStates = new ArrayList<>();
        int numGhosts = 0;
        for(AgentTypeAndPosition pos : layout.getAgentPositions()) {
            if(pos.isPacman()) {
                if(numGhosts == numGhostAgents) {
                    continue;  // Max ghosts reached already
                }
                numGhosts++;
            }
            agentStates.add( new AgentStateSimple( new ConfigurationStandard(pos.getPosition(), Direction.Stop), pos.isPacman()) );
        }
        eaten = new ArrayList<>();
        for(Object o : agentStates) {
            eaten.add(false);
        }
    }
    
    public Grid getFood() {
        return food;
    }

    public Grid getCapsules() {
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
    
    public int getScoreChange() {
        return scoreChange;
    }

    public void setScoreChange(final int scoreChange) {
        this.scoreChange = scoreChange;
    }
    
    public void setAgentMoved(final int agentIndex) {
        this.agentMoved = agentIndex;
    }

    public void setscore(final int score) {
        this.score = score;
    }

    public void setEaten(final List<Boolean> makeList) {
        this.eaten = makeList;
    }

    public boolean isLose() {
        return this.lose;
    }

    public boolean isWin() {
        return this.win;
    }
}
