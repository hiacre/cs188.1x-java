/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.List;

/**
 *
 * @author archie
 */
public interface Search {
    
    public List<Direction> getActions(final SearchProblem problem);
}
