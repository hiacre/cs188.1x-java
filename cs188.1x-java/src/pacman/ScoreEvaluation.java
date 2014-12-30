package pacman;

public class ScoreEvaluation implements Evaluator {

    public ScoreEvaluation() {
    }

    @Override
    public int eval(GameState1 gameState) {
        return gameState.getScore();
    }
    
}
