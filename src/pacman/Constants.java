/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author archie
 */
public class Constants {
    
    public static int SCARED_TIME = 40;
    public static double COLLISION_TOLERANCE = 0.7;
    public static int TIME_PENALTY = 1;
    
    /** Number of moves for which ghosts are scared */
    public static int getScaredTime() {
        return SCARED_TIME;
    }
    
    /** How close ghosts must be to Pacman to kill */
    public static double getCollisionTolerance() {
        return COLLISION_TOLERANCE;
    }
    
    /** Number of points lost each round */
    public static int getTimePenalty() {
        return TIME_PENALTY;
    }
}
