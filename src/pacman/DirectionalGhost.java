package pacman;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import util.Position;
import util.PositionStandard;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
        final Collection<Direction> legalActions = state.getLegalActions(getIndex());
        final Position pos = state.getGhostPosition(getIndex());
        final boolean isScared = ghostState.getScaredTimer() > 0;

        double speed = 1;
        if(isScared) {
            speed = 0.5;
        }

        final Collection<DirectionVector> actionVectors = new ArrayList<>();
        for(Direction a : legalActions) {
            actionVectors.add(a.toVector(speed));
        }
        
        final Collection<Position> newPositions = new ArrayList<>();
        for(DirectionVector a : actionVectors) {
            newPositions.add(
                    PositionStandard.newInstance(
                        pos.getX()+a.getX(),
                        pos.getY()+a.getY()));
        }
        final Position pacmanPosition = state.getPacmanPosition();

        // Select best actions given the state
        final Collection distancesToPacman = new ArrayList<>();
        for(Position pos2 : newPositions) {
            distancesToPacman.add(pos2.manhattanDistance(pacmanPosition));
        }
        final int bestScore;
        final double bestProb;
        if(isScared) {
            bestScore = Collections.max( distancesToPacman );
            bestProb = this.prob_scaredFlee;
        } else {
            bestScore = min( distancesToPacman );
            bestProb = this.prob_attack;
        }
        bestActions = [action for action, distance in zip( legalActions, distancesToPacman ) if distance == bestScore];

        // Construct distribution
        dist = util.Counter();
        for a in bestActions: dist[a] = bestProb / len(bestActions);
        for a in legalActions: dist[a] += ( 1-bestProb ) / len(legalActions);
        dist.normalize();
        return dist;
    }
}
