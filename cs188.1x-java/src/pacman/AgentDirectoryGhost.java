package pacman;

public enum AgentDirectoryGhost implements AgentDirectory {

    // Ghost Agents
    RandomGhost(new AgentFactoryGhost() {
                    @Override
                    public GhostAgent make(final int index) {
                        return new RandomGhost(index);
                    }}),
    DirectionalGhost(new AgentFactoryGhost() {
                        @Override
                        public GhostAgent make(int index) {
                            return new DirectionalGhost(index, null, null);
                        }})
    ;
    
    private AgentDirectoryGhost(final AgentFactoryGhost factory) {
        this.factory = factory;
    }
    
    private AgentFactoryGhost factory;

    @Override
    public AgentFactoryGhost getFactory() {
        return this.factory;
    }
}
