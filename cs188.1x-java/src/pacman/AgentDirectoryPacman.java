package pacman;

/**
 *
 * @author archie
 */
public enum AgentDirectoryPacman {

    KeyboardAgent(new AgentFactory() {
                        @Override
                        public Agent make() { return new KeyboardAgent(); }
                    }),
    KeyboardAgent2(new AgentFactory() {
                        @Override
                        public Agent make() { return new KeyboardAgent(); }
                    }),
    LeftTurnAgent(new AgentFactory() {
                        @Override
                        public Agent make() { return new LeftTurnAgent(); }
                    }),
    GreedyAgent(new AgentFactory() {
                    @Override
                    public Agent make() { return new GreedyAgent(); }
                }),
    SearchAgent(new AgentFactory() {
                    @Override
                    public Agent make() { return new SearchAgent(); }
                }),
    StayEastSearchAgent(new AgentFactory() {
                            @Override
                            public Agent make() { return new StayEastSearchAgent(); }
                        }),
    StayWestSearchAgent(new AgentFactory() {
                            @Override
                            public Agent make() { return new StayWestSearchAgent(); }
                        }),
    GoWestAgent(new AgentFactory() {
                    @Override
                    public Agent make() { return new GoWestAgent(); }
                }),
    AStarCornersAgent(new AgentFactory() {
                            @Override
                            public Agent make() { return new AStarCornersAgent(); }
                        }),
    AStarFoodSearchAgent(new AgentFactory() {
                            @Override
                            public Agent make() { return new AStarFoodSearchAgent(); }
                        }),
    ClosestDotSearchAgent(new AgentFactory() {
                            @Override
                            public Agent make() { return new ClosestDotSearchAgent(); }
                        }),
    ApproximateSearchAgent(new AgentFactory() {
                                @Override
                                public Agent make() { return new ApproximateSearchAgent(); }
                            })
    ;
    
    private final AgentFactory factory;
    
    private AgentDirectoryPacman(final AgentFactory factory) {
        this.factory = factory;
    }

    public AgentFactory getFactory() {
        return this.factory;
    }

}
