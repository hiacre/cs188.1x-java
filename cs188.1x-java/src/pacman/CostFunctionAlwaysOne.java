package pacman;

import common.Position;

public class CostFunctionAlwaysOne implements CostFunction {

    public CostFunctionAlwaysOne() {
    }

    @Override
    public int eval(Position nextState) {
        return 1;
    }
    
}
