package pacman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static pacman.Direction.East;
import static pacman.Direction.North;
import static pacman.Direction.South;
import static pacman.Direction.West;
import sun.management.resources.agent;
import util.Position;

/**
 *
 * @author archie
 */
public class PacmanGraphicsNonText {
    
    private final int have_window;
    private final HashMap<Object, Object> currentGhostImages;
    private final double zoom;
    private final boolean capture;
    private double frameTime;
    private boolean isBlue;
    private GameState1 previousState;
    private Layout layout;
    private int width;
    private int height;
    private InfoPane infoPane;
    private int gridSize;
    private Layout currentState;
    private List<List> distributionImages;

    private PacmanGraphicsNonText(Double zoom, Double frameTime, Boolean capture) {
        zoom = zoom == null ? 1.0 : zoom;
        frameTime = frameTime == null ? 0.0 : frameTime;
        capture = capture == null ? false : capture;
        
        this.have_window = 0;
        this.currentGhostImages = new HashMap<>();
        this.pacmanImage = null;
        this.zoom = zoom;
        this.gridSize = DEFAULT_GRID_SIZE * zoom;
        this.capture = capture;
        this.frameTime = frameTime;
    }

    private void initialize(final GameState1 state, Boolean isBlue) {
        this.isBlue = isBlue == null ? false : isBlue;
        
        this.startGraphics(state);

        // this.drawDistributions(state)
        this.distributionImages = null;  // Initialized lazily
        this.drawStaticObjects(state);
        this.drawAgentObjects(state);

        // Information
        this.previousState = state;
    }
    

    private void startGraphics(final GameState1 state) {
        this.layout = state.getLayout();
        final Layout l = this.layout;
        this.width = l.getWidth();
        this.height = l.getHeight();
        this.make_window(this.width, this.height);
        this.infoPane = new InfoPane(l, this.gridSize);
        this.currentState = l;
    }

    private void drawDistributions(final GameState1 state) {
        final Grid walls = state.getLayout().getWalls();
        final List<List> dist = new ArrayList<>();
        for(int x=0; x<walls.getWidth(); x++) {
            final List distx = new ArrayList();
            dist.add(distx);
            for(int y=0; y<walls.getHeight(); y++) {
                ( screen_x, screen_y ) = this.to_screen(x,y);
                block = square( (screen_x, screen_y),
                                0.5 * this.gridSize,
                                color = BACKGROUND_COLOR,
                                filled = 1, behind=2);
                distx.append(block);
            }
        this.distributionImages = dist;
    }

    private void drawStaticObjects(final GameState1 state) {
        final Layout l = this.layout;
        this.drawWalls(l.getWalls());
        this.food = this.drawFood(l.getFood());
        this.capsules = this.drawCapsules(l.getCapsules());
        refresh();
    }

    private void drawAgentObjects(final GameState1 state) {
        this.agentImages = new ArrayList();  // (agentState, image)
        for(index, agent in enumerate(state.agentStates)) {
            if(agent.isPacman) {
                image = this.drawPacman(agent, index);
                this.agentImages.append( (agent, image) );
            } else {
                image = this.drawGhost(agent, index);
                this.agentImages.append( (agent, image) );
            }
        }
        refresh();
    }
    
    /** Changes an image from a ghost to a pacman or vis versa (for capture) */
    private void swapImages(final int agentIndex, final GameState1 newState) {
        prevState, prevImage = this.agentImages.get(agentIndex);
        for item in prevImage: remove_from_screen(item);
        if(newState.isPacman) {
            image = this.drawPacman(newState, agentIndex);
            this.agentImages[agentIndex] = (newState, image );
        } else {
            image = this.drawGhost(newState, agentIndex);
            this.agentImages[agentIndex] = (newState, image );
        }
        refresh();
    }

    private void update(final GameState1 newState) {
        final int agentIndex = newState.getAgentMoved();
        final AgentState agentState = newState.getAgentStates().get(agentIndex);

        if(this.agentImages.get(agentIndex).get(0).isPacman != agentState.isPacman()) {
            this.swapImages(agentIndex, agentState);
        }
        prevState, prevImage = this.agentImages.get(agentIndex);
        if(agentState.isPacman()) {
            this.animatePacman(agentState, prevState, prevImage);
        } else {
            this.moveGhost(agentState, agentIndex, prevState, prevImage);
        }
        this.agentImages.get(agentIndex) = (agentState, prevImage);

        if(newState.getFoodEaten() != null) {
            this.removeFood(newState.getFoodEaten(), this.food);
        }
        if(newState.getCapsuleEaten() != null) {
            this.removeCapsule(newState.getCapsuleEaten(), this.capsules);
        }
        this.infoPane.updateScore(newState.getScore());
        // TODO we only do the following if newState supports the getGhostDistances() method
        this.infoPane.updateGhostDistances(newState.getGhostDistances());
    }
    

    private void make_window(final int width, final int height) {
        final int grid_width = (width-1) * this.gridSize;
        final int grid_height = (height-1) * this.gridSize;
        final int screen_width = 2*this.gridSize + grid_width;
        final int screen_height = 2*this.gridSize + grid_height + INFO_PANE_HEIGHT;

        begin_graphics(screen_width,
                       screen_height,
                       BACKGROUND_COLOR,
                       "CS188 Pacman");
    }

    private void drawPacman(final Object pacman, final int index) {
        position = this.getPosition(pacman);
        screen_point = this.to_screen(position);
        endpoints = this.getEndpoints(this.getDirection(pacman));

        width = PACMAN_OUTLINE_WIDTH;
        outlineColor = PACMAN_COLOR;
        fillColor = PACMAN_COLOR;

        if(this.capture) {
            outlineColor = TEAM_COLORS[index % 2];
            fillColor = GHOST_COLORS[index];
            width = PACMAN_CAPTURE_OUTLINE_WIDTH;
        }

        return [circle(screen_point, PACMAN_SCALE * this.gridSize,
                       fillColor = fillColor, outlineColor = outlineColor,
                       endpoints = endpoints,
                       width = width)];
    }

    private Endpoints getEndpoints(final Direction direction, Position position) {
        position = position == null ? Position.newInstance(0, 0) : position;
        final double x = position.getX();
        final double y = position.getY();
        double pos = x - (int)x + y - (int)y;
        final double w = 30 + 80 * Math.sin(Math.PI * pos);

        final double delta = w / 2;
        final Endpoints endpoints;
        switch(direction) {
            case West: endpoints = new Endpoints(180+delta, 180-delta); break;
            case North: endpoints = new Endpoints(90+delta, 90-delta); break;
            case South: endpoints = new Endpoints(270+delta, 270-delta); break;
            default:
                endpoints = new Endpoints(0+delta, 0-delta);
        }
        return endpoints;
    }

    private void movePacman(final Position position, final Direction direction, final Object image) {
        screenPosition = this.to_screen(position);
        final Endpoints endpoints = this.getEndpoints( direction, position );
        r = PACMAN_SCALE * this.gridSize;
        moveCircle(image[0], screenPosition, r, endpoints);
        refresh();
    }

    private void animatePacman(final Object pacman, final Object prevPacman, final Object image) {
        if(this.frameTime < 0) {
            System.out.println("Press any key to step forward, 'q' to play");
            final List<Character> keys = wait_for_keys();
            if(keys.contains('q')) {
                this.frameTime = 0.1;
            }
        }
        if(this.frameTime > 0.01 || this.frameTime < 0) {
            start = time.time();
            fx, fy = this.getPosition(prevPacman);
            px, py = this.getPosition(pacman);
            frames = 4.0;
            for(int i=1; i<(int)frames+1; i++) {
                pos = px*i/frames + fx*(frames-i)/frames, py*i/frames + fy*(frames-i)/frames;
                this.movePacman(pos, this.getDirection(pacman), image);
                refresh();
                sleep(abs(this.frameTime) / frames);
            }
        } else {
            this.movePacman(this.getPosition(pacman), this.getDirection(pacman), image);
        }
        refresh();
    }

    private Object getGhostColor(final GhostState ghost, final int ghostIndex) {
        if(ghost.getScaredTimer() > 0) {
            return SCARED_COLOR;
        } else {
            return GHOST_COLORS.get(ghostIndex);
        }
    }

    private List drawGhost(final Object ghost, final int agentIndex) {
        pos = this.getPosition(ghost);
        final Direction dir = this.getDirection(ghost);
        (screen_x, screen_y) = (this.to_screen(pos) );
        coords = new ArrayList();
        for(Position pos : GHOST_SHAPE) {
            final double x = pos.getX();
            final double y = pos.getY();
            coords.append((x*this.gridSize*GHOST_SIZE + screen_x, y*this.gridSize*GHOST_SIZE + screen_y));
        }

        colour = this.getGhostColor(ghost, agentIndex);
        body = polygon(coords, colour, filled = 1);
        WHITE = formatColor(1.0, 1.0, 1.0);
        BLACK = formatColor(0.0, 0.0, 0.0);

        double dx = 0;
        double dy = 0;
        switch(dir) {
            case North: dy = -0.2; break;
            case South: dy = 0.2; break;
            case East: dx = 0.2; break;
            case West: dx = -0.2; break;
            default:
                throw new RuntimeException("Unhandled direction");
        }
        final Object leftEye = circle((screen_x+this.gridSize*GHOST_SIZE*(-0.3+dx/1.5), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy/1.5)), this.gridSize*GHOST_SIZE*0.2, WHITE, WHITE);
        final Object rightEye = circle((screen_x+this.gridSize*GHOST_SIZE*(0.3+dx/1.5), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy/1.5)), this.gridSize*GHOST_SIZE*0.2, WHITE, WHITE);
        final Object leftPupil = circle((screen_x+this.gridSize*GHOST_SIZE*(-0.3+dx), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy)), this.gridSize*GHOST_SIZE*0.08, BLACK, BLACK);
        final Object rightPupil = circle((screen_x+this.gridSize*GHOST_SIZE*(0.3+dx), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy)), this.gridSize*GHOST_SIZE*0.08, BLACK, BLACK);
        final List ghostImageParts = new ArrayList();
        ghostImageParts.add(body);
        ghostImageParts.add(leftEye);
        ghostImageParts.add(rightEye);
        ghostImageParts.add(leftPupil);
        ghostImageParts.add(rightPupil);

        return ghostImageParts;
    }
    
    
    private void moveEyes(final Position pos, final Direction dir, final Object eyes) {
        (screen_x, screen_y) = (this.to_screen(pos) );
        double dx = 0;
        double dy = 0;
        switch(dir) {
            case North: dy = -0.2; break;
            case South: dy = 0.2; break;
            case East: dx = 0.2; break;
            case West: dx = -0.2; break;
            default:
                throw new RuntimeException("Unhandled direction");
        }
        moveCircle(eyes[0],(screen_x+this.gridSize*GHOST_SIZE*(-0.3+dx/1.5), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy/1.5)), this.gridSize*GHOST_SIZE*0.2);
        moveCircle(eyes[1],(screen_x+this.gridSize*GHOST_SIZE*(0.3+dx/1.5), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy/1.5)), this.gridSize*GHOST_SIZE*0.2);
        moveCircle(eyes[2],(screen_x+this.gridSize*GHOST_SIZE*(-0.3+dx), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy)), this.gridSize*GHOST_SIZE*0.08);
        moveCircle(eyes[3],(screen_x+this.gridSize*GHOST_SIZE*(0.3+dx), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy)), this.gridSize*GHOST_SIZE*0.08);
    }
    

    private void moveGhost(final GhostState ghost, final int ghostIndex, final Object prevGhost, final List ghostImageParts) {
        old_x, old_y = this.to_screen(this.getPosition(prevGhost));
        new_x, new_y = this.to_screen(this.getPosition(ghost));
        final double delta = new_x - old_x, new_y - old_y;

        for(Object ghostImagePart : ghostImageParts) {
            move_by(ghostImagePart, delta);
        }
        refresh();

        if(ghost.getScaredTimer() > 0) {
            color = SCARED_COLOR;
        } else {
            color = GHOST_COLORS[ghostIndex];
        }
        edit(ghostImageParts[0], ('fill', color), ('outline', color));
        this.moveEyes(this.getPosition(ghost), this.getDirection(ghost), ghostImageParts[-4:]);
        refresh();
    }

    private Position getPosition(final AgentState agentState) {
        if(agentState.getConfiguration() == null) {
            return Position.newInstance(-1000, -1000);
        }
        return agentState.getPosition();
    }

    private Direction getDirection(final AgentState agentState) {
        if(agentState.getConfiguration() == null) {
            return Direction.Stop;
        }
        return agentState.getConfiguration().getDirection();
    }

    private void finish() {
        end_graphics();
    }

    private Position to_screen(final Position point) {
        double x = point.getX();
        double y = point.getY();
        x = (x + 1)*this.gridSize;
        y = (this.height  - y)*this.gridSize;
        return Position.newInstance(x, y);
    }

    /** Fixes some TK issue with off-center circles */
    private Position to_screen2(final Position point) {
        double x = point.getX();
        double y = point.getY();
        x = (x + 1)*this.gridSize;
        y = (this.height  - y)*this.gridSize;
        return Position.newInstance(x, y);
    }

    private void drawWalls(final Object wallMatrix) {
        wallColor = WALL_COLOR;
        for(xNum, x in enumerate(wallMatrix)) {
            if(this.capture and (xNum * 2) < wallMatrix.width) {
                wallColor = TEAM_COLORS[0];
            }
            if(this.capture and (xNum * 2) >= wallMatrix.width) {
                wallColor = TEAM_COLORS[1];
            }

            for(yNum, cell in enumerate(x)) {
                if(cell) { // There's a wall here
                    pos = (xNum, yNum);
                    screen = this.to_screen(pos);
                    screen2 = this.to_screen2(pos);

                    // draw each quadrant of the square based on adjacent walls
                    wIsWall = this.isWall(xNum-1, yNum, wallMatrix);
                    eIsWall = this.isWall(xNum+1, yNum, wallMatrix);
                    nIsWall = this.isWall(xNum, yNum+1, wallMatrix);
                    sIsWall = this.isWall(xNum, yNum-1, wallMatrix);
                    nwIsWall = this.isWall(xNum-1, yNum+1, wallMatrix);
                    swIsWall = this.isWall(xNum-1, yNum-1, wallMatrix);
                    neIsWall = this.isWall(xNum+1, yNum+1, wallMatrix);
                    seIsWall = this.isWall(xNum+1, yNum-1, wallMatrix);

                    // NE quadrant
                    if((not nIsWall) && (not eIsWall)) {
                        // inner circle
                        circle(screen2, WALL_RADIUS * this.gridSize, wallColor, wallColor, (0,91), 'arc');
                    }
                    if((nIsWall) and (not eIsWall)) {
                        // vertical line
                        line(add(screen, (this.gridSize*WALL_RADIUS, 0)), add(screen, (this.gridSize*WALL_RADIUS, this.gridSize*(-0.5)-1)), wallColor);
                    }
                    if((not nIsWall) and (eIsWall)) {
                        // horizontal line
                        line(add(screen, (0, this.gridSize*(-1)*WALL_RADIUS)), add(screen, (this.gridSize*0.5+1, this.gridSize*(-1)*WALL_RADIUS)), wallColor);
                    }
                    if((nIsWall) and (eIsWall) and (not neIsWall)) {
                        // outer circle
                        circle(add(screen2, (this.gridSize*2*WALL_RADIUS, this.gridSize*(-2)*WALL_RADIUS)), WALL_RADIUS * this.gridSize-1, wallColor, wallColor, (180,271), 'arc');
                        line(add(screen, (this.gridSize*2*WALL_RADIUS-1, this.gridSize*(-1)*WALL_RADIUS)), add(screen, (this.gridSize*0.5+1, this.gridSize*(-1)*WALL_RADIUS)), wallColor);
                        line(add(screen, (this.gridSize*WALL_RADIUS, this.gridSize*(-2)*WALL_RADIUS+1)), add(screen, (this.gridSize*WALL_RADIUS, this.gridSize*(-0.5))), wallColor);
                    }

                    // NW quadrant
                    if((not nIsWall) and (not wIsWall)) {
                        // inner circle
                        circle(screen2, WALL_RADIUS * this.gridSize, wallColor, wallColor, (90,181), 'arc');
                    }
                    if((nIsWall) and (not wIsWall)) {
                        // vertical line
                        line(add(screen, (this.gridSize*(-1)*WALL_RADIUS, 0)), add(screen, (this.gridSize*(-1)*WALL_RADIUS, this.gridSize*(-0.5)-1)), wallColor);
                    }
                    if((not nIsWall) and (wIsWall)) {
                        // horizontal line
                        line(add(screen, (0, this.gridSize*(-1)*WALL_RADIUS)), add(screen, (this.gridSize*(-0.5)-1, this.gridSize*(-1)*WALL_RADIUS)), wallColor);
                    }
                    if((nIsWall) and (wIsWall) and (not nwIsWall)) {
                        // outer circle
                        circle(add(screen2, (this.gridSize*(-2)*WALL_RADIUS, this.gridSize*(-2)*WALL_RADIUS)), WALL_RADIUS * this.gridSize-1, wallColor, wallColor, (270,361), 'arc');
                        line(add(screen, (this.gridSize*(-2)*WALL_RADIUS+1, this.gridSize*(-1)*WALL_RADIUS)), add(screen, (this.gridSize*(-0.5), this.gridSize*(-1)*WALL_RADIUS)), wallColor);
                        line(add(screen, (this.gridSize*(-1)*WALL_RADIUS, this.gridSize*(-2)*WALL_RADIUS+1)), add(screen, (this.gridSize*(-1)*WALL_RADIUS, this.gridSize*(-0.5))), wallColor);
                    }

                    // SE quadrant
                    if((not sIsWall) and (not eIsWall)) {
                        // inner circle
                        circle(screen2, WALL_RADIUS * this.gridSize, wallColor, wallColor, (270,361), 'arc');
                    }
                    if((sIsWall) and (not eIsWall)) {
                        // vertical line
                        line(add(screen, (this.gridSize*WALL_RADIUS, 0)), add(screen, (this.gridSize*WALL_RADIUS, this.gridSize*(0.5)+1)), wallColor);
                    }
                    if((not sIsWall) and (eIsWall)) {
                        // horizontal line
                        line(add(screen, (0, this.gridSize*(1)*WALL_RADIUS)), add(screen, (this.gridSize*0.5+1, this.gridSize*(1)*WALL_RADIUS)), wallColor);
                    }
                    if((sIsWall) and (eIsWall) and (not seIsWall)) {
                        // outer circle
                        circle(add(screen2, (this.gridSize*2*WALL_RADIUS, this.gridSize*(2)*WALL_RADIUS)), WALL_RADIUS * this.gridSize-1, wallColor, wallColor, (90,181), 'arc');
                        line(add(screen, (this.gridSize*2*WALL_RADIUS-1, this.gridSize*(1)*WALL_RADIUS)), add(screen, (this.gridSize*0.5, this.gridSize*(1)*WALL_RADIUS)), wallColor);
                        line(add(screen, (this.gridSize*WALL_RADIUS, this.gridSize*(2)*WALL_RADIUS-1)), add(screen, (this.gridSize*WALL_RADIUS, this.gridSize*(0.5))), wallColor);
                    }
                    // SW quadrant
                    if((not sIsWall) and (not wIsWall)) {
                        // inner circle
                        circle(screen2, WALL_RADIUS * this.gridSize, wallColor, wallColor, (180,271), 'arc');
                    }
                    if((sIsWall) and (not wIsWall)) {
                        // vertical line
                        line(add(screen, (this.gridSize*(-1)*WALL_RADIUS, 0)), add(screen, (this.gridSize*(-1)*WALL_RADIUS, this.gridSize*(0.5)+1)), wallColor);
                    }
                    if((not sIsWall) and (wIsWall)) {
                        // horizontal line
                        line(add(screen, (0, this.gridSize*(1)*WALL_RADIUS)), add(screen, (this.gridSize*(-0.5)-1, this.gridSize*(1)*WALL_RADIUS)), wallColor);
                    }
                    if((sIsWall) and (wIsWall) and (not swIsWall)) {
                        // outer circle
                        circle(add(screen2, (this.gridSize*(-2)*WALL_RADIUS, this.gridSize*(2)*WALL_RADIUS)), WALL_RADIUS * this.gridSize-1, wallColor, wallColor, (0,91), 'arc');
                        line(add(screen, (this.gridSize*(-2)*WALL_RADIUS+1, this.gridSize*(1)*WALL_RADIUS)), add(screen, (this.gridSize*(-0.5), this.gridSize*(1)*WALL_RADIUS)), wallColor);
                        line(add(screen, (this.gridSize*(-1)*WALL_RADIUS, this.gridSize*(2)*WALL_RADIUS-1)), add(screen, (this.gridSize*(-1)*WALL_RADIUS, this.gridSize*(0.5))), wallColor);
                    }
                }
            }
        }
    }

    private boolean isWall(final int x, final int y, final Grid walls) {
        if(x < 0 || y < 0) {
            return false;
        }
        if(x >= walls.getWidth() || y >= walls.getHeight()) {
            return false;
        }
        return walls.get(x, y);
    }
    

    private drawFood(final Object foodMatrix) {
        List foodImages = new ArrayList();
        color = FOOD_COLOR;
        for(xNum, x in enumerate(foodMatrix)) {
            if(this.capture && (xNum * 2) <= foodMatrix.width) {
                color = TEAM_COLORS[0];
            }
            if(this.capture && (xNum * 2) > foodMatrix.width) {
                color = TEAM_COLORS[1];
            }
            List imageRow = new ArrayList();
            foodImages.append(imageRow);
            for(yNum, cell in enumerate(x)) {
                if(cell) { // There's food here
                    screen = this.to_screen((xNum, yNum ));
                    dot = circle( screen,
                                  FOOD_SIZE * this.gridSize,
                                  outlineColor = color, fillColor = color,
                                  width = 1);
                    imageRow.append(dot);
                } else {
                    imageRow.append(None);
                }
            }
        }
        return foodImages;
    }

    private void drawCapsules(final Object capsules) {
        final Map capsuleImages = new HashMap();
        for(capsule in capsules) {
            ( screen_x, screen_y ) = this.to_screen(capsule);
            dot = circle( (screen_x, screen_y),
                              CAPSULE_SIZE * this.gridSize,
                              outlineColor = CAPSULE_COLOR,
                              fillColor = CAPSULE_COLOR,
                              width = 1);
            capsuleImages[capsule] = dot;
        }
        return capsuleImages;
    }

    private void removeFood(final Object cell, final Object foodImages) {
        x, y = cell;
        remove_from_screen(foodImages[x][y]);
    }

    private void removeCapsule(final Object cell, final Object capsuleImages) {
        x, y = cell;
        remove_from_screen(capsuleImages[(x, y)]);
    }

    /** Draws an overlay of expanded grid positions for search agents */
    private void drawExpandedCells(final Object cells) {
        n = float(len(cells));
        baseColor = [1.0, 0.0, 0.0];
        this.clearExpandedCells();
        this.expandedCells = new ArrayList();
        for(k, cell in enumerate(cells)) {
            screenPos = this.to_screen( cell);
            cellColor = formatColor(*[(n-k) * c * .5 / n + .25 for c in baseColor]);
            block = square(screenPos,
                     0.5 * this.gridSize,
                     color = cellColor,
                     filled = 1, behind=2);
            this.expandedCells.append(block);
            if(this.frameTime < 0) {
                refresh();
            }
        }
    }

    private void clearExpandedCells() {
        if('expandedCells' in dir(self) and len(this.expandedCells) > 0) {
            for(cell in this.expandedCells) {
                remove_from_screen(cell);
            }
        }
    }


    /** Draws an agent's belief distributions */
    private void updateDistributions(final Object distributions) {
        if(this.distributionImages == None) {
            this.drawDistributions(this.previousState);
        }
        for(x in range(len(this.distributionImages))) {
            for(y in range(len(this.distributionImages[0]))) {
                image = this.distributionImages[x][y];
                weights = [dist[ (x,y) ] for dist in distributions];

                if(sum(weights) != 0) {
                    pass;
                }
                // Fog of war
                color = [0.0,0.0,0.0];
                colors = GHOST_VEC_COLORS[1:]; // With Pacman
                if(this.capture) {
                    colors = GHOST_VEC_COLORS;
                }
                for(weight, gcolor in zip(weights, colors)) {
                    color = [min(1.0, c + 0.95 * g * weight ** .3) for c,g in zip(color, gcolor)];
                }
                changeColor(image, formatColor(*color));
            }
        }
        refresh();
    }
}
