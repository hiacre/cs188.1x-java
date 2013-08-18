/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import pacman.SearchProblem;

/**
 *
 * @author archie
 */
public interface Heuristic {
    
    public State getState();
    public SearchProblem getProblem();
    
}
