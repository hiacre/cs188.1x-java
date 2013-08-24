package pacman;

import java.util.List;

/**
 *
 * @author archie
 */
public class Command {
    
    private Layout layout = null;
    private Integer numTraining;
    private Agent pacman;
    private List<GhostAgent> ghosts;
    private int numGames;
    private Boolean catchExceptions;
    private boolean record;
    private Integer timeout;
    private Object display;
    
    public Layout getLayout() {
        return layout;
    }

    void setLayout(final Layout layout) {
        this.layout = layout;
    }

    void setNumTraining(Integer numTraining) {
        this.numTraining = numTraining;
    }

    void setPacman(Agent pacman) {
        this.pacman = pacman;
    }

    void setGhosts(List<GhostAgent> ghosts) {
        this.ghosts = ghosts;
    }

    void setNumGames(int numGames) {
        this.numGames = numGames;
    }

    void setRecord(boolean record) {
        this.record = record;
    }

    void setCatchExceptions(final Boolean catchExceptions) {
        this.catchExceptions = catchExceptions;
    }

    void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getNumTraining() {
        return numTraining;
    }

    public Agent getPacman() {
        return pacman;
    }

    public List<GhostAgent> getGhosts() {
        return ghosts;
    }

    public int getNumGames() {
        return numGames;
    }

    public Boolean isCatchExceptions() {
        return catchExceptions;
    }

    public boolean getRecord() {
        return record;
    }

    public Integer getTimeout() {
        return timeout;
    }
    
    public Object getDisplay() {
        return display;
    }
}
