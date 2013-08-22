package pacman;

import java.util.List;

/**
 * This very general search agent finds a path using a supplied search algorithm for a
    supplied search problem, then returns actions to follow that path.

    As a default, this agent runs DFS on a PositionSearchProblem to find location (1,1)

    Options for fn include:
      depthFirstSearch or dfs
      breadthFirstSearch or bfs


    Note: You should NOT change any code in SearchAgent
 * @author archie
 */
public class SearchAgent extends AgentAbstract {
    
    private final Search searchFunction;
    private int actionIndex;
    private List<Direction> actions;
    private final ProblemFactory searchType;
    private final Heuristic heur;

    public SearchAgent() {
        this(new DepthFirstSearch(), new PositionSearchProblemFactory(), new NullHeuristic());
    }
    public SearchAgent(final Search searchFunc, final ProblemFactory problem, final Heuristic heuristic) {
        super(null);
        this.searchFunction = (searchFunc == null ? new DepthFirstSearch() : searchFunc);
        searchType = (problem == null ? new PositionSearchProblemFactory() : problem);
        this.heur = (heuristic == null ? new NullHeuristic(): heuristic);
        
        this.actionIndex = 0;
        
        //final Object func = getattr(search, fn)
//        if 'heuristic' not in func.func_code.co_varnames:
//            print('[SearchAgent] using function ' + fn)
//            self.searchFunction = func
//        else:
//            if heuristic in globals().keys():
//                heur = globals()[heuristic]
//            elif heuristic in dir(search):
//                heur = getattr(search, heuristic)
//            else:
//                raise AttributeError, heuristic + ' is not a function in searchAgents.py or search.py.'
//            print('[SearchAgent] using function %s and heuristic %s' % (fn, heuristic))
//            # Note: this bit of Python trickery combines the search algorithm and the heuristic
//            self.searchFunction = lambda x: func(x, heuristic=heur)
        

//        # Get the search problem type from the name
//        if prob not in globals().keys() or not prob.endswith('Problem'):
//            raise AttributeError, prob + ' is not a search problem type in SearchAgents.py.'
//        self.searchType = globals()[prob]
//        print('[SearchAgent] using problem type ' + prob)
    }

    
    /** 
     * This is the first time that the agent sees the layout of the game board. Here, we
        choose a path to the goal.  In this phase, the agent should compute the path to the
        goal and store it in a local variable.  All of the work is done in this method!

        state: a GameState object (pacman.py)
     * @param state 
     */
    @Override
    public void registerInitialState(final GameState1 state) {

        if(this.searchFunction == null) {
            throw new RuntimeException("No search function provided for SearchAgent");
        }
        final long starttime = System.currentTimeMillis();
        final SearchProblem problem = searchType.makeProblem(state); // Makes a new search problem
        this.actions = searchFunction.getActions(problem);  // Find a path
        final int totalCost = problem.getCostOfActions(this.actions);
        final double duration = (starttime - System.currentTimeMillis())/1000;
        System.out.println("Path found with total cost of " + totalCost + " in " + duration + " seconds");
        System.out.println("Search nodes expanded: " + problem.getExpanded());
    }

    
    /**
     * Returns the next action in the path chosen earlier (in registerInitialState).  Return
        Directions.STOP if there is no further action to take.

        state: a GameState object (pacman.py)
     * @param state
     * @return 
     */
    @Override
    public Direction getAction(final GameState1 state) {
        
        final int i = actionIndex;
        this.actionIndex += 1;
        if(i < this.actions.size()) {
            return this.actions.get(i);
        }
        else {
            return Direction.Stop;
        }
    }
}
