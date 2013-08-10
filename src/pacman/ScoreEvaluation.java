/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author archie
 */
public class ScoreEvaluation implements Evaluator {

    public ScoreEvaluation() {
    }

    @Override
    public int eval(GameState1 gameState) {
        return gameState.getScore();
    }
    
}
