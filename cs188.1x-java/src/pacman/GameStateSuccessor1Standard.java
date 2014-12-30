package pacman;

class GameStateSuccessor1Standard implements GameStateSuccessor1 {
    private final GameState1 gameState;
    private final Direction action;

    public GameStateSuccessor1Standard(final GameState1 gameState, final Direction action) {
        this.gameState = gameState;
        this.action = action;
        
    }

    @Override
    public Direction getAction() {
        return action;
    }

    @Override
    public GameState1 getState() {
        return gameState;
    }
    
}
