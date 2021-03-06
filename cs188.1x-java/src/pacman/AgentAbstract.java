package pacman;

public abstract class AgentAbstract implements Agent {
    
    private final int index;
    
    public AgentAbstract(Integer index) {
        this.index = (index == null ? 0 : index);
    }
    
    public int getIndex() {
        return index;
    }

    @Override
    abstract public Direction getAction(GameState1 state, Timeout timeout);

    @Override
    public void registerInitialState(GameState1 state, Timeout timeout) {
        
    }
}
