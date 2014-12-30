package eightpuzzle;

import java.util.List;
import pacman.BreadthFirstSearch;
import pacman.Direction;

public class EightPuzzle {

    public static void main(String[] args) {
        final GameStateEightPuzzleSearchProblem puzzle = EightPuzzleSearchProblem.createRandomEightPuzzle(25);
        System.out.println("A random puzzle: ");
        System.out.println(puzzle);

        final EightPuzzleSearchProblem problem = new EightPuzzleSearchProblem(puzzle);
        final List<Direction> path = new BreadthFirstSearch().getActions(problem);
        final StringBuilder message = new StringBuilder();
        message.append("BFS found a path of ").append(path.size()).append(" moves: ").append(path);
        System.out.println(message.toString());
        GameStateEightPuzzleSearchProblem curr = puzzle;
        int i = 1;
        for(Direction a : path) {
            curr = curr.result(a).getGameState();
            final StringBuilder message2 = new StringBuilder();
            message2.append("After ").append(i).append(" moves: ").append(a);
            System.out.println(message2.toString());
            System.out.println(curr);

            throw new UnsupportedOperationException("Need to put code here to wait for keystroke");
            // TODO raw_input("Press return for the next state...");   // wait for key stroke
            // TODO i += 1;
        }
    }
}
