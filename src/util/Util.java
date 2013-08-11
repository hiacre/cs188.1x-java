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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import pacman.PositionSearchProblem;

/**
 *
 * @author archie
 */
public class Util {

    public static int getMaximumCost() {
        return 999999;
    }

    public static <E> E randomChoice(Collection<E> collection) {
        final int randomIndex = new Random().nextInt(collection.size());
        int i = 0;
        for(E o : collection) {
            if(i == randomIndex) {
                return o;
            }
            i++;
        }
        return null;
    }
    
    public static <E> E randomChoice(List<E> list) {
        final int randomIndex = new Random().nextInt(list.size());
        return list.get(randomIndex);
    }

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
    public static <E extends Comparable> E sample(final Counter<E> c, final double choice) {
        
        final Counter<E> dist = c.getNormalized();
        
        float total = 0;
        E lastKey = null;
        for(E key : c.getSortedKeys()) {
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
    public static <E extends Comparable> E sample(final Counter<E> c) {
        return sample(c, new Random().nextDouble());
    }
    
    public static <E extends Comparable> E chooseFromDistribution(final Counter<E> c) {
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
    
    /** The Manhattan distance heuristic for a PositionSearchProblem */
    public int manhattanHeuristic(final Position position, final PositionSearchProblem problem, Map info) {
        final Position xy1 = position;
        // Problem is PositionSearchProblem
        final Position xy2 = problem.getGoal();
        return Math.abs(xy1.getX() - xy2.getX()) + Math.abs(xy1.getY() - xy2.getY());
    }

    /** The Euclidean distance heuristic for a PositionSearchProblem */
    public double euclideanHeuristic(final Position position, final PositionSearchProblem problem, Map info) {
        final Position xy1 = position;
        final Position xy2 = problem.getGoal();
        return  Math.sqrt(
                    Math.pow(xy1.getX() - xy2.getX(), 2) +
                    Math.pow(xy1.getY() - xy2.getY(), 2));
    }    
}
