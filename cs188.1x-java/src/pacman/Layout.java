package pacman;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Position;
import util.PositionStandard;
import util.Util;

/**
 * A Layout manages the static information about the game board.
 * @author archie
 */
public class Layout {

    private final Map<String, GridVisibility> VISIBILITY_MATRIX_CACHE = new HashMap<>();
    private final List<String> layoutText;
    private int numGhosts;
    private final int width;
    private final int height;
    private final Grid walls;
    private GridVisibility visibility;
    private List<AgentTypeAndPosition> agentPositions;
    private Grid food;
    private GridCapsules capsules;

    private Layout(final List<String> layoutText) {
        this.width = layoutText.get(0).length();
        this.height= layoutText.size();
        this.walls = GridStandard.newInstance(width, height, false);
        food = GridStandard.newInstance(width, height, false);
        capsules = GridCapsules.newInstance(width, height, false);
        agentPositions = new ArrayList<>();
        numGhosts = 0;
        processLayoutText(layoutText);
        this.layoutText = layoutText;
    }

    public int getNumGhosts() {
        return numGhosts;
    }

    public void initializeVisibilityMatrix() {
        
        if(!VISIBILITY_MATRIX_CACHE.keySet().contains(Util.concatStringList(layoutText, ""))) {
            
            final GridVisibility vis = GridVisibility.newInstance(width, height);
            final List<DirectionVector> vecs = Arrays.asList(
                    DirectionVector.newInstance(-0.5, 0),
                    DirectionVector.newInstance( 0.5, 0),
                    DirectionVector.newInstance( 0,  -0.5),
                    DirectionVector.newInstance( 0,   0.5));
            final List<Direction> dirs = Arrays.asList(Direction.North, Direction.South, Direction.West, Direction.East);
            if(dirs.size() != vecs.size()) {
                throw new RuntimeException("Vecs must be the same size as Dirs");
            }
            for(int x=0; x<width; x++) {
                for(int y=0; y<height; y++) {
                    if(!walls.get(x, y)) {
                        // x,y is a space, not a wall
                        for(int i=0; i<vecs.size(); i++) {
                            final DirectionVector vec = vecs.get(i);
                            final Direction direction = dirs.get(i);
                            final double dx = vec.getX();
                            final double dy = vec.getY();
                            double nextx = x + dx;
                            double nexty = y + dy;
                            while(
                                    ((nextx + nexty) != (int)nextx + (int)nexty) ||
                                    !walls.get((int)nextx,(int)nexty)) {
                                vis.get(x,y,direction).add(PositionStandard.newInstance(nextx, nexty));
                                nextx = x + dx;
                                nexty = y + dy;
                            }
                        }
                    }
                }
            }
            visibility = vis;
            VISIBILITY_MATRIX_CACHE.put(Util.concatStringList(layoutText, ""), vis);
        }
        else {
            visibility = VISIBILITY_MATRIX_CACHE.get(Util.concatStringList(layoutText, ""));
        }
    }

    public boolean isWall(final int x, final int y) {
        return walls.get(x, y);
    }

    public Position getRandomLegalPosition() {
        final Random r = new Random();
        int x = r.nextInt(width);
        int y = r.nextInt(height);
        while(isWall(x, y)) {
            x = r.nextInt(width);
            y = r.nextInt(height);
        }
        return PositionStandard.newInstance(x, y);
    }
    
    private List<Position> getCorners() {
        final List<Position> poses = new ArrayList<>();
        poses.add(PositionStandard.newInstance(1, 1));
        poses.add(PositionStandard.newInstance(1, height - 2));
        poses.add(PositionStandard.newInstance(width - 2, 1));
        poses.add(PositionStandard.newInstance(width - 2, height - 2));
        return poses;
    }
    
    public Position getRandomCorner() {
        final List<Position> poses = getCorners();
        return poses.get(new Random().nextInt(poses.size()));
    }

    public Position getFurthestCorner(final Position pacPos) {
        final List<Position> poses = getCorners();
        double maxDist = -1;
        Position maxPos = null;
        for(Position pos : poses) {
            final double dist = pos.manhattanDistance(pacPos);
            if(dist > maxDist) {
                maxDist = dist;
                maxPos = pos;
            }
        }
        return maxPos;
    }

    public boolean isVisibleFrom(final Position ghostPos, final Position pacPos, final Direction pacDirection) {
        final int row = pacPos.getFloorX();
        final int col = pacPos.getFloorY();
        return visibility.get(row, col, pacDirection).contains(ghostPos);
    }

    @Override
    public String toString() {
        return Util.concatStringList(layoutText, "\n");
    }

    public Layout deepCopy() {
        return new Layout(layoutText);
    }

    /**
     * Coordinates are flipped from the input format to the (x,y) convention here

        The shape of the maze.  Each character
        represents a different type of object.
         % - Wall
         . - Food
         o - Capsule
         G - Ghost
         P - Pacman
        Other characters are ignored.
     */
    public final void processLayoutText(final List<String> layoutText) {
        final int maxY = height - 1;
        for(int y=0; y<height; y++) {
            for(int x=0; x<width; x++) {
                final char layoutChar = layoutText.get(maxY - y).charAt(x);
                processLayoutChar(x, y, layoutChar);
            }
        }
        Collections.sort(agentPositions);
        agentPositions = agentPositions.subList(1, agentPositions.size());
    }

    public void processLayoutChar(final int x, final int y, final char layoutChar) {
        
        switch(layoutChar) {
            case '%': walls.set(x,y, true); break;
            case '.': food.set(x,y, true); break;
            case 'o': capsules.set(x,y, true); break;
            case 'P': agentPositions.add(new AgentTypeAndPosition(PositionStandard.newInstance(x, y), 0)); break;
            case 'G':
                agentPositions.add(new AgentTypeAndPosition(PositionStandard.newInstance(x, y), 1));
                numGhosts++;
                break;
            case '1':
            case '2':
            case '3':
            case '4':
                agentPositions.add(new AgentTypeAndPosition(PositionStandard.newInstance(x, y), new Integer(layoutChar)));
                numGhosts++;
                break;
            default:
                throw new RuntimeException("Unrecognized character in layout map");
        }
    }
    
    
    public Grid getWalls() {
        return walls;
    }
    
    public static Layout getLayout(final String name) {
        return Layout.getLayout(name, null);
    }
    private static Layout getLayout(final String name, Integer back) {
        if(back == null) {
            back = 2;
        }
        Layout layout;
        if(name.endsWith(".lay")) {
            layout = tryToLoad("layouts/" + name);
            if(layout == null) {
                layout = tryToLoad(name);
            }
        } else {
            layout = tryToLoad("layouts/" + name + ".lay");
            if(layout == null) {
                layout = tryToLoad(name + ".lay");
            }
        }
        if(layout == null && back >= 0) {
            final String curdir = System.getProperty("user.dir");
            Util.setCurrentDirectory("..");
            layout = getLayout(name, back -1);
            Util.setCurrentDirectory(curdir);
        }
        return layout;
    }
    
    
    private static Layout tryToLoad(final String fullname) {
        final File file = new File(fullname);
        if(!file.exists()) {
            return null;
        }
        try {
            final List<String> layout = Util.readSmallTextFile(fullname);
            return new Layout(layout);
        } catch (IOException ex) {
            Logger.getLogger(Layout.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Grid getFood() {
        return food;
    }

    public GridCapsules getCapsules() {
        return capsules;
    }
    
    public List<AgentTypeAndPosition> getAgentPositions() {
        return agentPositions;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
