package pacman;

/**
 *
 * @author archie
 */
public class Timeout {

    final int seconds;
    
    // TODO look into Java's Timer class
    // TODO http://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
    // TODO Above page includes details on stoping a task after a certain time
    public Timeout(final int seconds) {
        this.seconds = seconds;
    }
}
