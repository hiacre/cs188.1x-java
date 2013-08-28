package pacman;

import java.util.ArrayList;
import java.util.List;
import util.Position;
import static graphics.utils.GraphicsUtils.text;
import static graphics.utils.GraphicsUtils.changeText;


/**
 *
 * @author archie
 */
public class InfoPane {
    private final double gridSize;
    private final double base;
    private final double width;
    private final int fontSize;
    private final int height;
    private final String textColor;
    private Object scoreText;
    private List ghostDistanceText = null;
    private Object teamText;

    public InfoPane(final Layout layout, final double gridSize) {
        this.gridSize = gridSize;
        this.width = (layout.getWidth()) * gridSize;
        this.base = (layout.getHeight() + 1) * gridSize;
        this.height = GraphicsDisplay.INFO_PANE_HEIGHT;
        this.fontSize = 24;
        this.textColor = GraphicsDisplay.PACMAN_COLOR;
        this.drawPane();
    }

    /** Translates a point relative from the bottom left of the info pane. */
    public Position toScreen(double x, double y) {
        x += this.gridSize; // Margin
        y += this.base;
        return Position.newInstance(x, y);
    }

    public final void drawPane() {
        this.scoreText = text(0, 0, this.textColor, "SCORE:    0", "Times", this.fontSize, "bold", null);
    }

    public void initializeGhostDistances(final List distances) {
        this.ghostDistanceText = new ArrayList();

        int size = 20;
        if(this.width < 240) {
            size = 12;
        }
        if(this.width < 160) {
            size = 10;
        }

        for(int i=0; i<distances.size(); i++) {
            final String d = distances.get(i).toString();
            final Position pos = this.toScreen(this.width/2 + this.width/8 * i, 0);
            final Object t = text(pos.getX(), pos.getY(), GraphicsDisplay.GHOST_COLORS.get(i+1), d, "Times", size, "bold", null);
            this.ghostDistanceText.add(t);
        }
    }

    public void updateScore(final int score) {
        changeText(this.scoreText, String.format("SCORE: %4d", score), null, null, null);
    }

    public void setTeam(final boolean isBlue) {
        String text = "RED TEAM";
        if(isBlue) {
            text = "BLUE TEAM";
        }
        final Position pos = this.toScreen(300, 0);
        this.teamText = text(pos.getX(), pos.getY(), this.textColor, text, "Times", this.fontSize, "bold", null);
    }

    public void updateGhostDistances(final List distances) {
        if(distances.isEmpty()) {
            return;
        }
        if(this.ghostDistanceText == null) {
            this.initializeGhostDistances(distances);
        } else {
            for(int i=0; i<distances.size(); i++) {
                final String d = distances.get(i).toString();
                changeText(this.ghostDistanceText.get(i), d, null, null, null);
            }
        }
    }
    
    private void drawGhost() {
        
    }

    private void drawPacman() {
        
    }

    private void drawWarning() {
    }

    private void clearIcon() {
    }

    private void updateMessage(final Object message) {
    }

    private void clearMessage() {
    }
}
