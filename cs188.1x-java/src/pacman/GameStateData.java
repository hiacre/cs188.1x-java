package pacman;

import common.Position;
import java.util.ArrayList;
import java.util.List;
import common.PositionGrid;
import java.util.Collections;

/**
 *
 * @author archie
 */
public class GameStateData {
    
    private int scoreChange;
    private boolean lose;
    private boolean win;
    private Integer agentMoved;
    private Position capsuleEaten;
    private PositionGrid foodEaten;
    private List<AgentState> agentStates;
    private GridCapsules capsules;
    private Grid food;
    private Layout layout;
    private List<Boolean> eaten;
    private int score;
    
    public GameStateData() {
        foodEaten = null;
        capsuleEaten = null;
        agentMoved = null;
        lose = false;
        win = false;
        scoreChange = 0;
    }
    
    public GameStateData(final GameStateData prevState) {
        food = prevState.getFood().copy();
        capsules = prevState.getCapsules().copy();
        agentStates = new ArrayList<>(prevState.getAgentStates());
        layout = prevState.getLayout().deepCopy();
        eaten = prevState.getEaten();
        score = prevState.getScore();
    }
    
    public GameStateData deepCopy() {
        throw new UnsupportedOperationException();
    }
    
    public List<AgentState> copyAgentStates(final List<AgentState> agentStates) {
        final List<AgentState> result = new ArrayList<>();
        for(AgentState state : agentStates) {
            result.add(state.copy());
        }
        return result;
    }
    

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Why are we even calling this?!");
    }
    
    @Override
    public boolean equals(Object other) {
        throw new UnsupportedOperationException("Why are we even calling this?!");
    }
    
    @Override
    public String toString() {
        final int width = this.layout.getWidth();
        final int height = this.layout.getHeight();
        final Grid map = GridStandard.newInstance(width, height);
        for(int x=0; x<width; x++) {
            for(int y=0; y<height; y++) {
                final Grid food = this.food;
                final Grid walls = this.layout.getWalls();
                // TODO this next line tries to set grid cells to characters, rather than booleans,
                // which ain't how Grid works!
                //map.set(x, y, foodWallString(food.get(x, y), walls.get(x, y)));
            }
        }
        for(AgentState agentState : this.agentStates) {
            if(agentState == null) {
                continue;
            }
            if(agentState.getConfiguration() == null) {
                continue;
            }
            PositionGrid nearestPoint = Position.nearestPoint(agentState.getConfiguration().getPosition());
            final int ix = nearestPoint.getX();
            final int iy = nearestPoint.getY();
            
            final Direction agent_dir = agentState.getConfiguration().getDirection();
            if(agentState.isPacman()) {
                //TODO tries to set a character in a Grid
                //map.set(ix, iy, pacString( agent_dir ));
            } else {
                // TODO tries to set a character in a Grid
                //map.set(ix, iy, ghostString( agent_dir ));
            }
        }

        for(PositionGrid capsule : this.getCapsules().asList()) {
            // TODO tries to set a character in a Grid
            //map.set(capsule.getX(), capsule.getY(), 'o');
        }

        return map.toString() + "\nScore: " + this.score + "\n";
    }
    
    
    private static char foodWallString(final boolean hasFood, final boolean hasWall) {
        if(hasFood) {
            return '.';
        } else if(hasWall) {
            return '%';
        } else {
            return ' ';
        }
    }

    private static char pacString(final Direction dir) {
        switch(dir) {
            case North: return 'v';
            case South: return '^';
            case West: return '>';
            case East: return '<';
            default: throw new RuntimeException("Unhandled direction");
        }
    }
    
    private static char ghostString(final Direction dir) {
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

        final List<AgentState> agentStatesNew = new ArrayList<>();
        int numGhosts = 0;
        for(AgentTypeAndPosition pos : layout.getAgentPositions()) {
            if(pos.isPacman()) {
                if(numGhosts == numGhostAgents) {
                    continue;  // Max ghosts reached already
                }
                numGhosts++;
            }
            agentStatesNew.add( new AgentStateSimple( new ConfigurationStandard(pos.getPosition(), Direction.Stop), pos.isPacman()) );
        }
        final List<Boolean> eatenNew = new ArrayList<>();
        for(Object o : agentStatesNew) {
            eatenNew.add(false);
        }
        
        agentStates = Collections.unmodifiableList(agentStatesNew);
        eaten = eatenNew;
    }
    
    public Grid getFood() {
        return food;
    }

    public GridCapsules getCapsules() {
        return capsules;
    }

    public List<AgentState> getAgentStates() {
        return agentStates;
    }

    public Layout getLayout() {
        return layout;
    }

    public List<Boolean> getEaten() {
        return eaten;
    }
    
    public PositionGrid getFoodEaten() {
        return foodEaten;
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
    
    public int getAgentMoved() {
        return this.agentMoved;
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

    public void scoreAdd(final int scoreChange) {
        this.score += scoreChange;
    }

    public void setIsLose(boolean isLose) {
        this.lose = isLose;
    }

    void setFood(final Grid food) {
        this.food = food;
    }

    void setFoodEaten(final int x, final int y) {
        this.foodEaten = new PositionGrid(x, y);
    }

    boolean getLose() {
        return this.lose;
    }

    void setWin(boolean isWin) {
        this.win = isWin;
    }

    void setCapsuleEaten(final int x, final int y) {
        this.capsuleEaten = Position.newInstance(x, y);
    }
    
    Position getCapsuleEaten() {
        return capsuleEaten;
    }
}
