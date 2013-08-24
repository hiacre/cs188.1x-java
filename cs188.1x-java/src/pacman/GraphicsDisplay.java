package pacman;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author archie
 */
public class GraphicsDisplay {

    final static double DEFAULT_GRID_SIZE = 30.0;
    final static int INFO_PANE_HEIGHT = 35
    final static Object BACKGROUND_COLOR = formatColor(0,0,0);
    final static Object WALL_COLOR = formatColor(0.0/255.0, 51.0/255.0, 255.0/255.0);
    final static Object INFO_PANE_COLOR = formatColor(.4,.4,0);
    final static Object SCORE_COLOR = formatColor(.9, .9, .9);
    final static int PACMAN_OUTLINE_WIDTH = 2;
    final static int PACMAN_CAPTURE_OUTLINE_WIDTH = 4;

    final static List GHOST_COLORS;
    static {
        List GhostColors = new ArrayList();
        
        GhostColors.append(formatColor(.9,0,0)); // Red
        GhostColors.append(formatColor(0,.3,.9)); // Blue
        GhostColors.append(formatColor(.98,.41,.07)); // Orange
        GhostColors.append(formatColor(.1,.75,.7)); // Green
        GhostColors.append(formatColor(1.0,0.6,0.0)); // Yellow
        GhostColors.append(formatColor(.4,0.13,0.91)); // Purple
        
        GHOST_COLORS = ChostColors;
    }

    final static List TEAM_COLORS = GHOST_COLORS.subList(0, 2);

    GHOST_SHAPE = [
        ( 0,    0.3 ),
        ( 0.25, 0.75 ),
        ( 0.5,  0.3 ),
        ( 0.75, 0.75 ),
        ( 0.75, -0.5 ),
        ( 0.5,  -0.75 ),
        (-0.5,  -0.75 ),
        (-0.75, -0.5 ),
        (-0.75, 0.75 ),
        (-0.5,  0.3 ),
        (-0.25, 0.75 )
      ];
    final static double GHOST_SIZE = 0.65;
    final Object SCARED_COLOR = formatColor(1,1,1);

    final Object GHOST_VEC_COLORS = map(colorToVector, GHOST_COLORS);

    final Object PACMAN_COLOR = formatColor(255.0/255.0,255.0/255.0,61.0/255);
    final double PACMAN_SCALE = 0.5;

    // Food
    final Object FOOD_COLOR = formatColor(1,1,1);
    final double FOOD_SIZE = 0.1;

    // Laser
    final Object LASER_COLOR = formatColor(1,0,0);
    final double LASER_SIZE = 0.02;

    // Capsule graphics
    final Object CAPSULE_COLOR = formatColor(1,1,1);
    final double CAPSULE_SIZE = 0.25;

    // Drawing walls
    final double WALL_RADIUS = 0.15;



# Saving graphical output
# -----------------------
# Note: to make an animated gif from this postscript output, try the command:
# convert -delay 7 -loop 1 -compress lzw -layers optimize frame* out.gif
# convert is part of imagemagick (freeware)

SAVE_POSTSCRIPT = False
POSTSCRIPT_OUTPUT_DIR = 'frames'
FRAME_NUMBER = 0
import os

def saveFrame():
    "Saves the current graphical output as a postscript file"
    global SAVE_POSTSCRIPT, FRAME_NUMBER, POSTSCRIPT_OUTPUT_DIR
    if not SAVE_POSTSCRIPT: return
    if not os.path.exists(POSTSCRIPT_OUTPUT_DIR): os.mkdir(POSTSCRIPT_OUTPUT_DIR)
    name = os.path.join(POSTSCRIPT_OUTPUT_DIR, 'frame_%08d.ps' % FRAME_NUMBER)
    FRAME_NUMBER += 1
    writePostscript(name) # writes the current canvas
    
}
