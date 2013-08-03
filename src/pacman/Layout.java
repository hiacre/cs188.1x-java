/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import util.Position;
import util.PositionStandard;

/**
 * A Layout manages the static information about the game board.
 * @author archie
 */
public class Layout {

    private final Map<String, Object> VISIBILITY_MATRIX_CACHE = new HashMap<>();
    private final List<String> layoutText;
    private int numGhosts;
    private final int width;
    private final int height;
    private final Grid walls;
    private List<List<Map<Direction,Set<Position>>>> visibility;
    private List agentPositions;

    private Layout(final List<String> layoutText) {
        this.width = layoutText.get(0).length();
        this.height= layoutText.size();
        this.walls = GridStandard.newInstance(width, height, false);
        final Grid food = GridStandard.newInstance(width, height, false);
        final List capsules = new ArrayList();
        final List<Position> agentPositions = new ArrayList<>();
        numGhosts = 0;
        processLayoutText(layoutText);
        this.layoutText = layoutText;
    }

    public int getNumGhosts() {
        return numGhosts;
    }
    
    private String concatStringList(final List<String> strings, final String sep) {
        final StringBuilder sb = new StringBuilder();
        for(String s : strings) {
            sb.append(s).append(sep);
        }
        return sb.toString();
    }

    public void initializeVisibilityMatrix() {
        
        if(!VISIBILITY_MATRIX_CACHE.keySet().contains(concatStringList(layoutText, ""))) {
            final Map<Direction, Set<Object>> mapDirectionSet = new HashMap<>();
            mapDirectionSet.put(Direction.North, new HashSet());
            mapDirectionSet.put(Direction.South, new HashSet());
            mapDirectionSet.put(Direction.East, new HashSet());
            mapDirectionSet.put(Direction.West, new HashSet());
            mapDirectionSet.put(Direction.Stop, new HashSet());
            final Grid vis = GridStandard.newInstance(width, height, mapDirectionSet);
            final List<DirectionVector> vecs = Arrays.asList(
                    DirectionVector.newInstance(-1, 0),
                    DirectionVector.newInstance( 1, 0),
                    DirectionVector.newInstance( 0,-1),
                    DirectionVector.newInstance( 0, 1));
            final List<Direction> dirs = Arrays.asList(Direction.North, Direction.South, Direction.West, Direction.East);
            if(dirs.size() != vecs.size()) {
                throw new RuntimeException("Vecs must be the same size as Dirs");
            }
            for(int x=0; x<width; x++) {
                for(int y=0; y<height; y++) {
                    if(!walls.get(x, y)) {
                        // x,y is a space, not a wall... I think....
                        for(int i=0; i<vecs.size(); i++) {
                            final DirectionVector vec = vecs.get(i);
                            final Direction direction = dirs.get(i);
                            final int dx = vec.getX();
                            final int dy = vec.getY();
                            final int nextx = x + dx;
                            final int nexty = y + dy;
                            while((nextx + nexty) != int(nextx) + int(nexty) || !walls[int(nextx)][int(nexty)]) {
                                vis[x][y][direction].add((nextx, nexty));
                                nextx, nexty = x + dx, y + dy;
                            }
                        }
                    }
                }
            }
            self.visibility = vis;
            VISIBILITY_MATRIX_CACHE[reduce(str.__add__, self.layoutText)] = vis;
        }
        else {
            self.visibility = VISIBILITY_MATRIX_CACHE[reduce(str.__add__, self.layoutText)];
        }
    }

    public boolean isWall(final Position pos) {
        return walls.get(pos.getX(), pos.getY());
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
        int maxDist = -1;
        Position maxPos = null;
        for(Position pos : poses) {
            final int dist = pos.manhattanDistance(pacPos);
            if(dist > maxDist) {
                maxDist = dist;
                maxPos = pos;
            }
        }
        return maxPos;
    }

    public boolean isVisibleFrom(final Position ghostPos, final Position pacPos, final Direction pacDirection) {
        final int row = pacPos.getX();
        final int col = pacPos.getY();
        return visibility.get(row).get(col).get(pacDirection).contains(ghostPos);
    }

    @Override
    public String toString() {
        return concatStringList(layoutText, "\n");
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
    public void processLayoutText(final List<String> layoutText) {
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
            case 'P': agentPositions.add(new Pair<Integer, Position>(0, PositionStandard.newInstance(x, y))); break;
            case 'G':
                agentPositions.add(new Pair<Integer, Position>(1, PositionStandard.newInstance(x, y)));
                numGhosts++;
                break;
            case '1':
            case '2':
            case '3':
            case '4':
                agentPositions.add(new Pair<Integer, Position>(new Integer(layoutChar), PositionStandard.newInstance(x, y)));
                numGhosts++;
                break;
            default:
                throw new RuntimeException("Unrecognized character in layout map");
        }
    }
    
    
    public Grid getWalls() {
        return walls;
    }
}

def getLayout(name, back = 2):
    if name.endswith('.lay'):
        layout = tryToLoad('layouts/' + name)
        if layout == None: layout = tryToLoad(name)
    else:
        layout = tryToLoad('layouts/' + name + '.lay')
        if layout == None: layout = tryToLoad(name + '.lay')
    if layout == None and back >= 0:
        curdir = os.path.abspath('.')
        os.chdir('..')
        layout = getLayout(name, back -1)
        os.chdir(curdir)
    return layout

def tryToLoad(fullname):
    if(not os.path.exists(fullname)): return None
    f = open(fullname)
    try: return Layout([line.strip() for line in f])
    finally: f.close()

}
