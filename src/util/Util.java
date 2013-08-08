/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author archie
 */
public class Util {

    private Util() {
        throw new RuntimeException("This is a utility class and should not be instantiated.");
    }
    
    public static String sampleFromCounter(final Counter<String> counter) {
        return sample(counter);
    }
        
    /**
     * Finds the normalized distribution from a counter, and loops over the
     * counter's values totaling them, until the total exceeds the given limit.
     * Return the key associated with the value that caused the total to exceed
     * the limit.
     */
    public static String sample(final Counter<String> c, final double choice) {
        
        final Map<String, Float> dist = c.getNormalized();
        
        float total = 0;
        String lastKey = null;
        for(String key : c.getSortedKeys()) {
            total += dist.get(key);
            lastKey = key;
            if(total > choice) {
                return key;
            }
        }
        return lastKey;
    }
    
    /**
     * Gets a sample from a Counter, based upon a randomly chosen limit.
     */
    public static String sample(final Counter<String> c) {
        return sample(c, new Random().nextDouble());
    }
    
    public static String chooseFromDistribution(final Counter<String> c) {
        return sample(c);
    }

    public static <E> List<E> makeList(final int size, final E element) {
        final List<E> list = new ArrayList<>();
        for(int i=0; i<size; i++) {
            list.add(element);
        }
        return list;
    }
    
    
    public static boolean setCurrentDirectory(String directory_name)
    {
        boolean result = false;  // Boolean indicating whether directory was set
        final File directory;    // Desired current working directory

        directory = new File(directory_name).getAbsoluteFile();
        if (directory.exists())
        {
            result = (System.setProperty("user.dir", directory.getAbsolutePath()) != null);
        }

        return result;
    }
    
    public static List<String> readSmallTextFile(String aFileName) throws IOException {
        final InputStream is = new FileInputStream(aFileName);
        final List<String> result;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("ASCII")))) {
            result = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                line = br.readLine();
                result.add(line.trim());
            }
        }
        return result;
    }

    public static String concatStringList(final List<? extends Object> strings, final String sep) {
        final StringBuilder sb = new StringBuilder();
        for (Object s : strings) {
            sb.append(s.toString()).append(sep);
        }
        return sb.toString();
    }
    
def manhattanHeuristic(position, problem, info={}):
    "The Manhattan distance heuristic for a PositionSearchProblem"
    xy1 = position
    xy2 = problem.goal
    return abs(xy1[0] - xy2[0]) + abs(xy1[1] - xy2[1])

def euclideanHeuristic(position, problem, info={}):
    "The Euclidean distance heuristic for a PositionSearchProblem"
    xy1 = position
    xy2 = problem.goal
    return ( (xy1[0] - xy2[0]) ** 2 + (xy1[1] - xy2[1]) ** 2 ) ** 0.5
    
def cornersHeuristic(state, problem):
    """
    A heuristic for the CornersProblem that you defined.

      state:   The current search state
               (a data structure you chose in your search problem)

      problem: The CornersProblem instance for this layout.

    This function should always return a number that is a lower bound
    on the shortest path from the state to a goal of the problem; i.e.
    it should be admissible (as well as consistent).
    """
    corners = problem.corners # These are the corner coordinates
    walls = problem.walls # These are the walls of the maze, as a Grid (game.py)

    "*** YOUR CODE HERE ***"
    #return 0 # Default to trivial solution
    position = state[0]
    cornersVisitFlags = state[1]
    
    if cornersVisitFlags == (True, True, True, True):
        return 0
    
    cornersUnvisited = map(lambda x: x[0],
        filter(lambda x: not x[1],
               zip(corners, cornersVisitFlags)))
    
    perms = itertools.permutations(cornersUnvisited)
    
    minCost = 10**100
    for perm in perms:
        cost = travelDistance(position, perm)
        if cost < minCost:
            minCost = cost
    
    return minCost

def travelDistance(position, corners):
    totalCost = 0
    for corner in corners:
        totalCost += manhattanDistance(position, corner)
        position = corner
    return totalCost
}
