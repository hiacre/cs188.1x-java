package pacman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.Counter;
import util.CounterStandard;
import util.Position;

/**
 * A ghost that prefers to rush Pacman, or flee when scared.
 * @author archie
 */
public class DirectionalGhost extends GhostAgent {
    
    private final double prob_attack;
    private final double prob_scaredFlee;
    
    public DirectionalGhost(final int index, final Double prob_attack, final Double prob_scaredFlee) {
        super(index);
        this.prob_attack = prob_attack == null ? 0.8 : prob_attack;
        this.prob_scaredFlee = prob_scaredFlee == null ? 0.8 : prob_scaredFlee;
    }

    private Object getDistribution(final GameState1 state) {
        // Read variables from state
        final GhostState ghostState = state.getGhostState(getIndex());
        final List<Direction> legalActions = state.getLegalActions(getIndex());
        final Position pos = state.getGhostPosition(getIndex());
        final boolean isScared = ghostState.getScaredTimer() > 0;

        double speed = 1;
        if(isScared) {
            speed = 0.5;
        }

        final List<DirectionVector> actionVectors = new ArrayList<>();
        for(Direction a : legalActions) {
            actionVectors.add(a.toVector(speed));
        }
        
        final List<Position> newPositions = new ArrayList<>();
        for(DirectionVector a : actionVectors) {
            newPositions.add(
                    Position.newInstance(
                        pos.getX()+a.getX(),
                        pos.getY()+a.getY()));
        }
        final Position pacmanPosition = state.getPacmanPosition();

        // Select best actions given the state
        final List<Integer> distancesToPacman = new ArrayList<>();
        for(Position pos2 : newPositions) {
            distancesToPacman.add((int)Math.round(pos2.manhattanDistance(pacmanPosition)));
        }
        final int bestScore;
        final double bestProb;
        if(isScared) {
            bestScore = Collections.max( distancesToPacman );
            bestProb = this.prob_scaredFlee;
        } else {
            bestScore = Collections.min( distancesToPacman );
            bestProb = this.prob_attack;
        }
        // wherever the distance is the bestScore, pick the associated action in legalActions
        final List<Direction> bestActions = new ArrayList<>();
        for(int i=0; i<legalActions.size(); i++) {
            if(distancesToPacman.get(i) == bestScore) {
                bestActions.add(legalActions.get(i));
            }
        }

        // Construct distribution
        final Counter<Direction> dist = CounterStandard.newInstance();
        for(Direction a : bestActions) {
            dist.put(a, bestProb / bestActions.size());
        }
        for(Direction a : legalActions) {
            dist.increment(a, ( 1-bestProb ) / legalActions.size());
        }
        return dist.getNormalized();
    }
}
