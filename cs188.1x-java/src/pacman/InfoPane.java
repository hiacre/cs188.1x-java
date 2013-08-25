package pacman;

import java.util.List;
import util.Position;

/**
 *
 * @author archie
 */
public class InfoPane {
    private final int gridSize;
    private final int base;
    private final int width;
    private final int fontSize;

    public InfoPane(final Layout layout, final int gridSize) {
        this.gridSize = gridSize;
        this.width = (layout.getWidth()) * gridSize;
        this.base = (layout.getHeight() + 1) * gridSize;
        this.height = INFO_PANE_HEIGHT;
        this.fontSize = 24;
        this.textColor = PACMAN_COLOR;
        this.drawPane();
    }

    /** Translates a point relative from the bottom left of the info pane. */
    public Position toScreen(final Position pos, Double y) {
        double x;
        if(y == null) {
            x = pos.getX();
            y = pos.getY();
        } else {
            x = pos.getX();
        }

        x += this.gridSize; // Margin
        y += this.base;
        return Position.newInstance(x, y);
    }

    public final void drawPane() {
        this.scoreText = text( this.toScreen(0, 0  ), this.textColor, "SCORE:    0", "Times", this.fontSize, "bold");
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

        for(i, d in enumerate(distances)) {
            t = text( this.toScreen(this.width/2 + this.width/8 * i, 0), GHOST_COLORS[i+1], d, "Times", size, "bold");
            this.ghostDistanceText.append(t);
        }
    }

    public void updateScore(score) {
        changeText(self.scoreText, "SCORE: % 4d" % score);
    }

    public void setTeam(self, isBlue) {
        text = "RED TEAM";
        if isBlue: text = "BLUE TEAM";
        self.teamText = text( self.toScreen(300, 0  ), self.textColor, text, "Times", self.fontSize, "bold");
    }

    public void updateGhostDistances(final List distances) {
        if(distances.isEmpty()) {
            return;
        }
        if('ghostDistanceText' not in dir(self)) {
            self.initializeGhostDistances(distances);
        } else {
            for(i, d in enumerate(distances)) {
                changeText(self.ghostDistanceText[i], d);
            }
        }
    }

    def drawGhost(self):
        pass

    def drawPacman(self):
        pass

    def drawWarning(self):
        pass

    def clearIcon(self):
        pass

    def updateMessage(self, message):
        pass

    def clearMessage(self):
        pass    
}
