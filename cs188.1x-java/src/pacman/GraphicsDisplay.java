package pacman;

import common.Position;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import util.Util;
import static graphics.utils.GraphicsUtils.formatColor;
import static graphics.utils.GraphicsUtils.colorToVector;
import static graphics.utils.GraphicsUtils.writePostscript;

/**
 *
 * @author archie
 */
public class GraphicsDisplay {

    final static double DEFAULT_GRID_SIZE = 30.0;
    final static int INFO_PANE_HEIGHT = 35;
    final static String BACKGROUND_COLOR = formatColor(0,0,0);
    final static String WALL_COLOR = formatColor(0.0/255.0, 51.0/255.0, 255.0/255.0);
    final static String INFO_PANE_COLOR = formatColor(.4,.4,0);
    final static String SCORE_COLOR = formatColor(.9, .9, .9);
    final static int PACMAN_OUTLINE_WIDTH = 2;
    final static int PACMAN_CAPTURE_OUTLINE_WIDTH = 4;

    final static List<String> GHOST_COLORS;
    static {
        final List<String> GhostColors = new ArrayList<>();
        
        GhostColors.add(formatColor((float)0.9,         0,           0));    // Red
        GhostColors.add(formatColor((float)0,    (float)0.3,  (float)0.9));  // Blue
        GhostColors.add(formatColor((float)0.98, (float)0.41, (float)0.07)); // Orange
        GhostColors.add(formatColor((float)0.1,  (float)0.75, (float)0.7));  // Green
        GhostColors.add(formatColor((float)1.0,  (float)0.6,  (float)0.0));  // Yellow
        GhostColors.add(formatColor((float)0.4,  (float)0.13, (float)0.91)); // Purple
        
        GHOST_COLORS = Collections.unmodifiableList(GhostColors);
    }

    public final static List<String> TEAM_COLORS = GHOST_COLORS.subList(0, 2);

    final static List<Position> GHOST_SHAPE = Arrays.asList(
        Position.newInstance( 0,     0.3 ),
        Position.newInstance( 0.25,  0.75 ),
        Position.newInstance( 0.5,   0.3 ),
        Position.newInstance( 0.75,  0.75 ),
        Position.newInstance( 0.75, -0.5 ),
        Position.newInstance( 0.5,  -0.75 ),
        Position.newInstance(-0.5,  -0.75 ),
        Position.newInstance(-0.75, -0.5 ),
        Position.newInstance(-0.75,  0.75 ),
        Position.newInstance(-0.5,   0.3 ),
        Position.newInstance(-0.25,  0.75 )
    );
    final static double GHOST_SIZE = 0.65;
    final static String SCARED_COLOR = formatColor(1,1,1);

    final static List<List<Double>> GHOST_VEC_COLORS;
    static {
        List<List<Double>> ghostVecColors = new ArrayList<>();
        for(String c : GHOST_COLORS) {
            ghostVecColors.add(colorToVector(c));
        }
        GHOST_VEC_COLORS = Collections.unmodifiableList(ghostVecColors);
    }

    final static String PACMAN_COLOR = formatColor(255.0/255.0,255.0/255.0,61.0/255);
    final double PACMAN_SCALE = 0.5;

    // Food
    public static final String FOOD_COLOR = formatColor(1,1,1);
    public static final double FOOD_SIZE = 0.1;

    // Laser
    final String LASER_COLOR = formatColor(1,0,0);
    final double LASER_SIZE = 0.02;

    // Capsule graphics
    final static String CAPSULE_COLOR = formatColor(1,1,1);
    final static double CAPSULE_SIZE = 0.25;

    // Drawing walls
    final double WALL_RADIUS = 0.15;


    /** Saves the current graphical output as a postscript file */
    public void saveFrame() {

        final boolean SAVE_POSTSCRIPT = false;
        final String POSTSCRIPT_OUTPUT_DIR = "frames";
        int FRAME_NUMBER = 0;

        if(!SAVE_POSTSCRIPT) {
            return;
        }
        Util.makeDirectory(POSTSCRIPT_OUTPUT_DIR);
        final StringBuilder name = new StringBuilder();
        name.append(POSTSCRIPT_OUTPUT_DIR).append(File.separator).append("frame_");
        name.append(String.format("%010d", FRAME_NUMBER)).append(".ps");
        FRAME_NUMBER += 1;
        writePostscript(name.toString()); // writes the current canvas
    }
}
