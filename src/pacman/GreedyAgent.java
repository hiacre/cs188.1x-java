/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import util.Util;

/**
 *
 * @author archie
 */
public class GreedyAgent implements Agent {
    
    private final Evaluator evaluator;
    
    public GreedyAgent() {
        this(new ScoreEvaluation());
    }
    public GreedyAgent(final Evaluator evalFn) {
//        this.evaluationFunction = util.lookup(evalFn, globals());
//        assert self.evaluationFunction != None;
        this.evaluator = evalFn;
    }

    @Override
    public Direction getAction(final GameState1 state) {
        // Generate candidate actions
        final Collection<Direction> legal = state.getLegalPacmanActions();
        if(legal.contains(Direction.Stop)) {
            legal.remove(Direction.Stop);
        }

        final Collection<GameStateSuccessor1> successors = new ArrayList<>();
        for(Direction action : legal) {
            final GameState1 gs = state.generateSuccessor(0, action);
            final GameStateSuccessor1 successor = new GameStateSuccessor1Standard(gs, action);
            successors.add(successor);
        }
        final Collection<GameState1Scored> scored = new ArrayList<>();
        int bestScore = 0;
        for(GameStateSuccessor1 successor : successors) {
            final int score = evaluator.eval(successor.getState());
            if(score > bestScore) {
                bestScore = score;
            }
            final GameState1Scored gameStateScore = new GameState1ScoredStandard(
                score,
                successor.getAction());
            scored.add(gameStateScore);
        }
        final List<Direction> bestActions = new ArrayList<>();
        for(GameState1Scored gss : scored) {
            if(gss.getScore() == bestScore) {
                bestActions.add(gss.getAction());
            }
        }
        return Util.randomChoice(bestActions);
    }

    /**
     *
     * @author archie
     */
    private static interface GameState1Scored {

        public int getScore();

        public Direction getAction();
    }

    /**
     *
     * @author archie
     */
    private static class GameState1ScoredStandard implements GameState1Scored {

        private final Direction action;
        private final int score;

        public GameState1ScoredStandard(final int score, final Direction action) {
            super();
            this.score = score;
            this.action = action;
        }

        @Override
        public int getScore() {
            return score;
        }

        @Override
        public Direction getAction() {
            return action;
        }
    }
}
