/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import util.Position;

/**
 *
 * @author archie
 */
public class CostFunctionAlwaysOne implements CostFunction {

    public CostFunctionAlwaysOne() {
    }

    @Override
    public int eval(Position nextState) {
        return 1;
    }
    
}
