package eightpuzzle;

/**
 *
 * @author archie
 */
public class EightPuzzle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EightPuzzleSearchProblempuzzle = EightPuzzleSearchProblem.createRandomEightPuzzle(25);
        print('A random puzzle:');
        print(puzzle);

        problem = EightPuzzleSearchProblem(puzzle);
        path = search.breadthFirstSearch(problem);
        print('BFS found a path of %d moves: %s' % (len(path), str(path)));
        curr = puzzle;
        i = 1;
        for(a in path) {
            curr = curr.result(a);
            print('After %d move%s: %s' % (i, ("", "s")[i>1], a));
            print(curr);

            raw_input("Press return for the next state...");   // wait for key stroke
            i += 1;
        }
    }
}
