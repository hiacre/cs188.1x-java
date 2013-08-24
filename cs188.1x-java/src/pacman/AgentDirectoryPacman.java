package pacman;

/**
 *
 * @author archie
 */
public enum AgentDirectoryPacman implements AgentDirectory {

    KeyboardAgent(new AgentFactoryPacman() {
                        @Override
                        public Agent make() { return new KeyboardAgent(); }
                    }),
    KeyboardAgent2(new AgentFactoryPacman() {
                        @Override
                        public Agent make() { return new KeyboardAgent(); }
                    }),
    LeftTurnAgent(new AgentFactoryPacman() {
                        @Override
                        public Agent make() { return new LeftTurnAgent(); }
                    }),
    GreedyAgent(new AgentFactoryPacman() {
                    @Override
                    public Agent make() { return new GreedyAgent(); }
                }),
    SearchAgent(new AgentFactoryPacman() {
                    @Override
                    public Agent make() { return new SearchAgent(); }
                }),
    StayEastSearchAgent(new AgentFactoryPacman() {
                            @Override
                            public Agent make() { return new StayEastSearchAgent(); }
                        }),
    StayWestSearchAgent(new AgentFactoryPacman() {
                            @Override
                            public Agent make() { return new StayWestSearchAgent(); }
                        }),
    GoWestAgent(new AgentFactoryPacman() {
                    @Override
                    public Agent make() { return new GoWestAgent(); }
                }),
    AStarCornersAgent(new AgentFactoryPacman() {
                            @Override
                            public Agent make() { return new AStarCornersAgent(); }
                        }),
    AStarFoodSearchAgent(new AgentFactoryPacman() {
                            @Override
                            public Agent make() { return new AStarFoodSearchAgent(); }
                        }),
    ClosestDotSearchAgent(new AgentFactoryPacman() {
                            @Override
                            public Agent make() { return new ClosestDotSearchAgent(); }
                        }),
    ApproximateSearchAgent(new AgentFactoryPacman() {
                                @Override
                                public Agent make() { return new ApproximateSearchAgent(); }
                            })
    ;
    
    private final AgentFactoryPacman factory;
    
    private AgentDirectoryPacman(final AgentFactoryPacman factory) {
        this.factory = factory;
    }

    @Override
    public AgentFactoryPacman getFactory() {
        return this.factory;
    }
}
