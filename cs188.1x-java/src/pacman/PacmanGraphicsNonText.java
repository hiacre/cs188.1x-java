package pacman;

import common.Endpoints;
import common.Pair;
import common.Position;
import graphics.utils.GraphicsUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static pacman.Direction.East;
import static pacman.Direction.North;
import static pacman.Direction.South;
import static pacman.Direction.West;

import static graphics.utils.GraphicsUtils.square;
import static graphics.utils.GraphicsUtils.circle;
import static pacman.GraphicsDisplay.GHOST_SIZE;
import static pacman.GraphicsDisplay.WALL_RADIUS;
import java.util.Arrays;
import common.PositionGrid;

public class PacmanGraphicsNonText implements PacmanDisplay {
    
    private final int have_window;
    private final HashMap<Object, Object> currentGhostImages;
    private final double zoom;
    private final boolean capture;
    private double frameTime;
    private boolean isBlue;
    private GameState1 previousState;
    private static Layout layout;
    private static int width;
    private static int height;
    private static InfoPane infoPane;
    private static double gridSize;
    private static Layout currentState;
    private List<List> distributionImages;
    private final Object pacmanImage;
    private List<Pair<AgentState, List<Object>>> agentImages;
    private ArrayList expandedCells;
    private List food;
    private Map<PositionGrid, Object> capsules;

    PacmanGraphicsNonText(Double zoom, Double frameTime, Boolean capture) {
        zoom = zoom == null ? 1.0 : zoom;
        frameTime = frameTime == null ? 0.0 : frameTime;
        capture = capture == null ? false : capture;
        
        this.have_window = 0;
        this.currentGhostImages = new HashMap<>();
        this.pacmanImage = null;
        this.zoom = zoom;
        gridSize = GraphicsDisplay.DEFAULT_GRID_SIZE * zoom;
        this.capture = capture;
        this.frameTime = frameTime;
    }

    @Override
    public void initialize(final GameState1 state, Boolean isBlue) {
        
        this.isBlue = isBlue == null ? false : isBlue;
        PacmanGraphicsNonText.startGraphics(state);
        
        initializeDistributionImages(state);

        // this.drawDistributions(state)
        this.distributionImages = null;  // Initialized lazily
        this.drawStaticObjects();
        this.drawAgentObjects(state);

        // Information
        this.previousState = state;
    }
    
    /** Exists to be overriden */
    protected void initializeDistributionImages(final GameState1 state) {
        
    }
    

    static void startGraphics(final GameState1 state) {
        layout = state.getLayout();
        final Layout l = layout;
        width = l.getWidth();
        height = l.getHeight();
        make_window(width, height);
        infoPane = new InfoPane(l, gridSize);
        currentState = l;
    }

    private void drawDistributions(final GameState1 state) {
        final Grid walls = state.getLayout().getWalls();
        final List<List> dist = new ArrayList<>();
        for(int x=0; x<walls.getWidth(); x++) {
            final List distx = new ArrayList();
            dist.add(distx);
            for(int y=0; y<walls.getHeight(); y++) {
                final Position pos = this.to_screen(x, y);
                final double screen_x = pos.getX();
                final double screen_y = pos.getY();
                final Object block = square(screen_x, screen_y, 0.5 * gridSize, GraphicsDisplay.BACKGROUND_COLOR, 1, 2);
                distx.add(block);
            }
        }
        this.distributionImages = dist;
    }

    private void drawStaticObjects() {
        final Layout l = layout;
        this.drawWalls(l.getWalls());
        this.food = this.drawFood(l.getFood());
        this.capsules = this.drawCapsules(l.getCapsules().asList());
        GraphicsUtils.refresh();
    }

    protected void drawAgentObjects(final GameState1 state) {
        this.agentImages = new ArrayList();  // (agentState, image)
        for(int index=0; index<state.getAgentStates().size(); index++) {
            final AgentState agent = state.getAgentStates().get(index);
            final List<Object> image;
            image = this.draw(agent, index);
            this.agentImages.add(new Pair<>(agent, image));
        }
        GraphicsUtils.refresh();
    }
    
    /** Changes an image from a ghost to a pacman or vis versa (for capture) */
    private void swapImages(final int agentIndex, final AgentState newState) {
        final Pair<AgentState, List<Object>> pair = this.agentImages.get(agentIndex);
        final AgentState prevState = pair.getFirst();
        final List<Object> prevImage = pair.getSecond();
        for(Object item : prevImage) {
            GraphicsUtils.remove_from_screen(item, null, null);
        }
        final List<Object> image;
        if(newState.isPacman()) {
            image = this.draw(newState, agentIndex);
        } else {
            image = this.draw(newState, agentIndex);
        }
        this.agentImages.add(agentIndex, new Pair<>(newState, image));
        GraphicsUtils.refresh();
    }

    @Override
    public void update(final GameState1 newState) {
        final int agentIndex = newState.getAgentMoved();
        final AgentState agentState = newState.getAgentStates().get(agentIndex);

        if(this.agentImages.get(agentIndex).getFirst().isPacman() != agentState.isPacman()) {
            this.swapImages(agentIndex, agentState);
        }
        final AgentState prevState = this.agentImages.get(agentIndex).getFirst();
        final List<Object> prevImage = this.agentImages.get(agentIndex).getSecond();
        this.moveAgent(agentState, agentIndex, prevState, prevImage);  // used to call animatePacman or moveGhost
        this.agentImages.set(agentIndex, new Pair<>(agentState, prevImage));

        if(newState.getFoodEaten() != null) {
            final PositionGrid foodEaten = newState.getFoodEaten();
            this.removeFood(foodEaten.getX(), foodEaten.getY(), this.food);
        }
        if(newState.getCapsuleEaten() != null) {
            this.removeCapsule(newState.getCapsuleEaten(), this.capsules);
        }
        infoPane.updateScore(newState.getScore());
        // TODO we only do the following if newState supports the getGhostDistances() method
        //infoPane.updateGhostDistances(newState.getGhostDistances());
    }
    

    private static void make_window(final int width, final int height) {
        final double grid_width = (width-1) * gridSize;
        final double grid_height = (height-1) * gridSize;
        final double screen_width = 2*gridSize + grid_width;
        final double screen_height = 2*gridSize + grid_height + GraphicsDisplay.INFO_PANE_HEIGHT;

        GraphicsUtils.begin_graphics(
                        screen_width,
                        screen_height,
                        GraphicsDisplay.BACKGROUND_COLOR,
                        "CS188 Pacman");
    }

    private List<Object> draw(final AgentState pacman, final int index) {
        final Position position = getPosition1(pacman);
        final Position screen_point = this.to_screen(position);
        final Endpoints endpoints = this.getEndpoints(this.getDirection(pacman), null);

        width = GraphicsDisplay.PACMAN_OUTLINE_WIDTH;
        String outlineColor = GraphicsDisplay.PACMAN_COLOR;
        String fillColor = GraphicsDisplay.PACMAN_COLOR;

        if(this.capture) {
            outlineColor = GraphicsDisplay.TEAM_COLORS.get(index % 2);
            fillColor = GraphicsDisplay.GHOST_COLORS.get(index);
            width = GraphicsDisplay.PACMAN_CAPTURE_OUTLINE_WIDTH;
        }

        return Arrays.asList(GraphicsUtils.circle(screen_point.getX(), screen_point.getY(), GraphicsDisplay.PACMAN_SCALE * gridSize,
                       outlineColor,
                       fillColor,
                       endpoints,
                       null,
                       width));
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

    private void movePacman(final Position position, final Direction direction, final List<Object> image) {
        final Position screenPosition = this.to_screen(position);
        final Endpoints endpoints = this.getEndpoints( direction, position);
        final double r = GraphicsDisplay.PACMAN_SCALE * gridSize;
        GraphicsUtils.moveCircle(image.get(0), screenPosition, r, endpoints);
        GraphicsUtils.refresh();
    }

    private void moveAgent(final AgentState pacman, final Integer agentIndex, final AgentState prevPacman, final List<Object> image) {
        if(this.frameTime < 0) {
            System.out.println("Press any key to step forward, 'q' to play");
            final List<Character> keys = GraphicsUtils.wait_for_keys();
            if(keys.contains('q')) {
                this.frameTime = 0.1;
            }
        }
        if(this.frameTime > 0.01 || this.frameTime < 0) {
            final Position posPrev = getPosition1(prevPacman);
            final double fx = posPrev.getX();
            final double fy = posPrev.getY();
            final Position posCurr = getPosition1(pacman);
            final double px = posCurr.getX();
            final double py = posCurr.getY();
            final double frames = 4.0;
            for(int i=1; i<(int)frames+1; i++) {
                final Position pos = Position.newInstance(px*i/frames + fx*(frames-i)/frames, py*i/frames + fy*(frames-i)/frames);
                this.movePacman(pos, this.getDirection(pacman), image);
                GraphicsUtils.refresh();
                GraphicsUtils.sleep((int)(Math.abs(this.frameTime) / frames));
            }
        } else {
            this.movePacman(getPosition1(pacman), this.getDirection(pacman), image);
        }
        GraphicsUtils.refresh();
    }

    private String getGhostColor(final GhostState ghost, final int ghostIndex) {
        if(ghost.getScaredTimer() > 0) {
            return GraphicsDisplay.SCARED_COLOR;
        } else {
            return GraphicsDisplay.GHOST_COLORS.get(ghostIndex);
        }
    }

    protected List draw(final GhostState ghost, final int agentIndex) {
        final Position pos = getPosition1(ghost);
        final Direction dir = this.getDirection(ghost);
        final Position screenPos = this.to_screen(pos);
        final double screen_x = screenPos.getX();
        final double screen_y = screenPos.getY();
        final List<Position> coords = new ArrayList<>();
        for(Position p : GraphicsDisplay.GHOST_SHAPE) {
            final double x = p.getX();
            final double y = p.getY();
            coords.add(Position.newInstance(x*gridSize*GraphicsDisplay.GHOST_SIZE + screen_x, y*gridSize*GraphicsDisplay.GHOST_SIZE + screen_y));
        }

        final String colour = this.getGhostColor(ghost, agentIndex);
        final Object body = GraphicsUtils.polygon(coords, colour, null, 1, null, null, null);
        final String WHITE = GraphicsUtils.formatColor(1.0, 1.0, 1.0);
        final String BLACK = GraphicsUtils.formatColor(0.0, 0.0, 0.0);

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
        
        final Object leftEye = circle(screen_x+gridSize*GHOST_SIZE*(-0.3+dx/1.5), screen_y-gridSize*GHOST_SIZE*(0.3-dy/1.5), gridSize*GHOST_SIZE*0.2, WHITE, WHITE, null, null, null);
        final Object rightEye = circle(screen_x+gridSize*GHOST_SIZE*(0.3+dx/1.5), screen_y-gridSize*GHOST_SIZE*(0.3-dy/1.5), gridSize*GHOST_SIZE*0.2, WHITE, WHITE, null, null, null);
        final Object leftPupil = circle(screen_x+gridSize*GHOST_SIZE*(-0.3+dx), screen_y-gridSize*GHOST_SIZE*(0.3-dy), gridSize*GHOST_SIZE*0.08, BLACK, BLACK, null, null, null);
        final Object rightPupil = circle(screen_x+gridSize*GHOST_SIZE*(0.3+dx), screen_y-gridSize*GHOST_SIZE*(0.3-dy), gridSize*GHOST_SIZE*0.08, BLACK, BLACK, null, null, null);
        final List ghostImageParts = new ArrayList();
        ghostImageParts.add(body);
        ghostImageParts.add(leftEye);
        ghostImageParts.add(rightEye);
        ghostImageParts.add(leftPupil);
        ghostImageParts.add(rightPupil);

        return ghostImageParts;
    }
    
    
    private void moveEyes(final Position pos, final Direction dir, final List eyes) {
        final Position screenPos = this.to_screen(pos);
        final double screen_x = screenPos.getX();
        final double screen_y = screenPos.getY();
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
        GraphicsUtils.moveCircle(
                eyes.get(0),
                Position.newInstance(screen_x+gridSize*GHOST_SIZE*(-0.3+dx/1.5), screen_y-gridSize*GHOST_SIZE*(0.3-dy/1.5)),
                gridSize*GHOST_SIZE*0.2,
                null);
        GraphicsUtils.moveCircle(
                eyes.get(1),
                Position.newInstance(screen_x+gridSize*GHOST_SIZE*(0.3+dx/1.5), screen_y-gridSize*GHOST_SIZE*(0.3-dy/1.5)),
                gridSize*GHOST_SIZE*0.2,
                null);
        GraphicsUtils.moveCircle(
                eyes.get(2),
                Position.newInstance(screen_x+gridSize*GHOST_SIZE*(-0.3+dx), screen_y-gridSize*GHOST_SIZE*(0.3-dy)),
                gridSize*GHOST_SIZE*0.08,
                null);
        GraphicsUtils.moveCircle(
                eyes.get(3),
                Position.newInstance(screen_x+gridSize*GHOST_SIZE*(0.3+dx), screen_y-gridSize*GHOST_SIZE*(0.3-dy)),
                gridSize*GHOST_SIZE*0.08,
                null);
    }
    

    private void moveAgent(final GhostState ghost, final int ghostIndex, final AgentState prevGhost, final List ghostImageParts) {
        
        final Position posOld = this.to_screen(getPosition1(prevGhost));
        final double old_x = posOld.getX();
        final double old_y = posOld.getY();
        
        final Position posNew = this.to_screen(getPosition1(ghost));
        final double new_x = posNew.getX();
        final double new_y = posNew.getY();
        final Position delta = Position.newInstance(new_x - old_x, new_y - old_y);

        for(Object ghostImagePart : ghostImageParts) {
            GraphicsUtils.move_by(ghostImagePart, delta.getX(), delta.getY(), null, null, null);
        }
        GraphicsUtils.refresh();

        final String color;
        if(ghost.getScaredTimer() > 0) {
            color = GraphicsDisplay.SCARED_COLOR;
        } else {
            color = GraphicsDisplay.GHOST_COLORS.get(ghostIndex);
        }
        GraphicsUtils.edit(ghostImageParts.get(0), color, color);
        this.moveEyes(
                getPosition1(ghost),
                this.getDirection(ghost),
                ghostImageParts.subList(ghostImageParts.size()-4, ghostImageParts.size()));
        GraphicsUtils.refresh();
    }

    protected static Position getPosition1(final AgentState agentState) {
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

    @Override
    public void finish() {
        GraphicsUtils.end_graphics();
    }

    private Position to_screen(final Position point) {
        return to_screen(point.getX(), point.getY());
    }
    
    private Position to_screen(final PositionGrid point) {
        return to_screen(point.getX(), point.getY());
    }
    
    private Position to_screen(final double x, final double y) {
        final double newX = (x + 1)*gridSize;
        final double newY = (height - y)*gridSize;
        return Position.newInstance(newX, newY);
    }

    /** Fixes some TK issue with off-center circles */
    private Position to_screen2(final double x, final double y) {
        return to_screen(x, y);
    }

    private void drawWalls(final Grid wallMatrix) {
        String wallColor = GraphicsDisplay.WALL_COLOR;
        for(int xNum=0; xNum<wallMatrix.getWidth(); xNum++) {
            final List<Boolean> x = wallMatrix.getData().get(xNum);
            if(this.capture && (xNum * 2) < wallMatrix.getWidth()) {
                wallColor = GraphicsDisplay.TEAM_COLORS.get(0);
            }
            if(this.capture && (xNum * 2) >= wallMatrix.getWidth()) {
                wallColor = GraphicsDisplay.TEAM_COLORS.get(1);
            }

            for(int yNum=0; yNum<x.size(); yNum++) {
                final boolean cell = x.get(yNum);
                if(cell) { // There's a wall here
                    final Position screen = this.to_screen(xNum, yNum);
                    final Position screen2 = this.to_screen2(xNum, yNum);

                    // draw each quadrant of the square based on adjacent walls
                    final boolean wIsWall = this.isWall(xNum-1, yNum, wallMatrix);
                    final boolean eIsWall = this.isWall(xNum+1, yNum, wallMatrix);
                    final boolean nIsWall = this.isWall(xNum, yNum+1, wallMatrix);
                    final boolean sIsWall = this.isWall(xNum, yNum-1, wallMatrix);
                    final boolean nwIsWall = this.isWall(xNum-1, yNum+1, wallMatrix);
                    final boolean swIsWall = this.isWall(xNum-1, yNum-1, wallMatrix);
                    final boolean neIsWall = this.isWall(xNum+1, yNum+1, wallMatrix);
                    final boolean seIsWall = this.isWall(xNum+1, yNum-1, wallMatrix);

                    // NE quadrant
                    if(!nIsWall && !eIsWall) {
                        // inner circle
                        GraphicsUtils.circle(screen2, GraphicsDisplay.WALL_RADIUS * gridSize, wallColor, wallColor, new Endpoints(0,91), "arc", null);
                    }
                    if(!nIsWall && !eIsWall) {
                        // vertical line
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(gridSize*GraphicsDisplay.WALL_RADIUS, 0)),
                                add(screen, Position.newInstance(gridSize*GraphicsDisplay.WALL_RADIUS, gridSize*(-0.5)-1)),
                                wallColor,
                                null);
                    }
                    if(!nIsWall && eIsWall) {
                        // horizontal line
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(0, gridSize*(-1)*GraphicsDisplay.WALL_RADIUS)),
                                add(screen, Position.newInstance(gridSize*0.5+1, gridSize*(-1)*WALL_RADIUS)),
                                wallColor,
                                null);
                    }
                    if(nIsWall && eIsWall && !neIsWall) {
                        // outer circle
                        GraphicsUtils.circle(
                                add(screen2, Position.newInstance(gridSize*2*WALL_RADIUS, gridSize*(-2)*WALL_RADIUS)),
                                WALL_RADIUS * gridSize-1,
                                wallColor,
                                wallColor,
                                new Endpoints(180,271),
                                "arc",
                                null);
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(gridSize*2*WALL_RADIUS-1, gridSize*(-1)*WALL_RADIUS)),
                                add(screen, Position.newInstance(gridSize*0.5+1, gridSize*(-1)*WALL_RADIUS)),
                                wallColor,
                                null);
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(gridSize*WALL_RADIUS, gridSize*(-2)*WALL_RADIUS+1)),
                                add(screen, Position.newInstance(gridSize*WALL_RADIUS, gridSize*(-0.5))),
                                wallColor,
                                null);
                    }

                    // NW quadrant
                    if(!nIsWall && !wIsWall) {
                        // inner circle
                        GraphicsUtils.circle(
                                screen2,
                                WALL_RADIUS * gridSize,
                                wallColor,
                                wallColor,
                                new Endpoints(90,181),
                                "arc",
                                null);
                    }
                    if(nIsWall && !wIsWall) {
                        // vertical line
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(gridSize*(-1)*WALL_RADIUS, 0)),
                                add(screen, Position.newInstance(gridSize*(-1)*WALL_RADIUS, gridSize*(-0.5)-1)),
                                wallColor,
                                null);
                    }
                    if(!nIsWall && wIsWall) {
                        // horizontal line
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(0, gridSize*(-1)*WALL_RADIUS)),
                                add(screen, Position.newInstance(gridSize*(-0.5)-1, gridSize*(-1)*WALL_RADIUS)),
                                wallColor,
                                null);
                    }
                    if(nIsWall && wIsWall && !nwIsWall) {
                        // outer circle
                        GraphicsUtils.circle(
                                add(screen2, Position.newInstance(gridSize*(-2)*WALL_RADIUS, gridSize*(-2)*WALL_RADIUS)),
                                WALL_RADIUS * gridSize-1,
                                wallColor,
                                wallColor,
                                new Endpoints(270,361),
                                "arc",
                                null);
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(gridSize*(-2)*WALL_RADIUS+1, gridSize*(-1)*WALL_RADIUS)),
                                add(screen, Position.newInstance(gridSize*(-0.5), gridSize*(-1)*WALL_RADIUS)),
                                wallColor,
                                null);
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(gridSize*(-1)*WALL_RADIUS, gridSize*(-2)*WALL_RADIUS+1)),
                                add(screen, Position.newInstance(gridSize*(-1)*WALL_RADIUS, gridSize*(-0.5))),
                                wallColor,
                                null);
                    }

                    // SE quadrant
                    if(!sIsWall && !eIsWall) {
                        // inner circle
                        GraphicsUtils.circle(
                                screen2,
                                WALL_RADIUS * gridSize,
                                wallColor,
                                wallColor,
                                new Endpoints(270,361),
                                "arc",
                                null);
                    }
                    if(sIsWall && !eIsWall) {
                        // vertical line
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(gridSize*WALL_RADIUS, 0)),
                                add(screen, Position.newInstance(gridSize*WALL_RADIUS, gridSize*(0.5)+1)),
                                wallColor,
                                null);
                    }
                    if(!sIsWall && eIsWall) {
                        // horizontal line
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(0, gridSize*(1)*WALL_RADIUS)),
                                add(screen, Position.newInstance(gridSize*0.5+1, gridSize*(1)*WALL_RADIUS)),
                                wallColor,
                                null);
                    }
                    if(sIsWall && eIsWall && !seIsWall) {
                        // outer circle
                        GraphicsUtils.circle(
                                add(screen2, Position.newInstance(gridSize*2*WALL_RADIUS, gridSize*(2)*WALL_RADIUS)),
                                WALL_RADIUS * gridSize-1,
                                wallColor,
                                wallColor,
                                new Endpoints(90,181),
                                "arc",
                                null);
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(gridSize*2*WALL_RADIUS-1, gridSize*(1)*WALL_RADIUS)),
                                add(screen, Position.newInstance(gridSize*0.5, gridSize*(1)*WALL_RADIUS)),
                                wallColor,
                                null);
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(gridSize*WALL_RADIUS, gridSize*(2)*WALL_RADIUS-1)),
                                add(screen, Position.newInstance(gridSize*WALL_RADIUS, gridSize*(0.5))),
                                wallColor,
                                null);
                    }
                    // SW quadrant
                    if(!sIsWall && !wIsWall) {
                        // inner circle
                        GraphicsUtils.circle(
                                screen2,
                                WALL_RADIUS * gridSize,
                                wallColor,
                                wallColor,
                                new Endpoints(180,271),
                                "arc",
                                null);
                    }
                    if(sIsWall && !wIsWall) {
                        // vertical line
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(gridSize*(-1)*WALL_RADIUS, 0)),
                                add(screen, Position.newInstance(gridSize*(-1)*WALL_RADIUS, gridSize*(0.5)+1)),
                                wallColor,
                                null);
                    }
                    if(!sIsWall && wIsWall) {
                        // horizontal line
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(0, gridSize*(1)*WALL_RADIUS)),
                                add(screen, Position.newInstance(gridSize*(-0.5)-1, gridSize*(1)*WALL_RADIUS)),
                                wallColor,
                                null);
                    }
                    if(sIsWall && wIsWall && !swIsWall) {
                        // outer circle
                        GraphicsUtils.circle(
                                add(screen2, Position.newInstance(gridSize*(-2)*WALL_RADIUS, gridSize*(2)*WALL_RADIUS)),
                                WALL_RADIUS * gridSize-1,
                                wallColor,
                                wallColor,
                                new Endpoints(0,91),
                                "arc",
                                null);
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(gridSize*(-2)*WALL_RADIUS+1, gridSize*(1)*WALL_RADIUS)),
                                add(screen, Position.newInstance(gridSize*(-0.5), gridSize*(1)*WALL_RADIUS)),
                                wallColor,
                                null);
                        GraphicsUtils.line(
                                add(screen, Position.newInstance(gridSize*(-1)*WALL_RADIUS, gridSize*(2)*WALL_RADIUS-1)),
                                add(screen, Position.newInstance(gridSize*(-1)*WALL_RADIUS, gridSize*(0.5))),
                                wallColor,
                                null);
                    }
                }
            }
        }
    }
    
    private static Position add(final Position pos1, final Position pos2) {
        return Position.newInstance(pos1.getX() + pos2.getX(), pos1.getY()+pos2.getY());
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
    

    private List drawFood(final Grid foodGrid) {
        List<List<Boolean>> foodMatrix = foodGrid.getData();
        List foodImages = new ArrayList();
        String color = GraphicsDisplay.FOOD_COLOR;
        for(int xNum=0; xNum<foodMatrix.size(); xNum++) {
            final List<Boolean> x = foodMatrix.get(xNum);
            if(this.capture && (xNum * 2) <= foodGrid.getWidth()) {
                color = GraphicsDisplay.TEAM_COLORS.get(0);
            }
            if(this.capture && (xNum * 2) > foodGrid.getWidth()) {
                color = GraphicsDisplay.TEAM_COLORS.get(1);
            }
            final List imageRow = new ArrayList();
            foodImages.add(imageRow);
            for(int yNum=0; yNum<x.size(); yNum++) {
                final boolean cell = x.get(yNum);
                if(cell) { // There's food here
                    final Position screen = this.to_screen(xNum, yNum);
                    final Object dot = circle(
                            screen.getX(),
                            screen.getY(),
                            GraphicsDisplay.FOOD_SIZE * gridSize,
                            color,
                            color,
                            null,
                            null,
                            1);
                    imageRow.add(dot);
                } else {
                    imageRow.add(null);
                }
            }
        }
        return foodImages;
    }

    private Map<PositionGrid, Object> drawCapsules(final List<PositionGrid> capsules) {
        final Map<PositionGrid, Object> capsuleImages = new HashMap<>();
        for(PositionGrid capsule : capsules) {
            final Position pos = this.to_screen(capsule);
            final double screen_x = pos.getX();
            final double screen_y = pos.getY();
            final Object dot = circle(   screen_x, screen_y,
                            GraphicsDisplay.CAPSULE_SIZE * gridSize,
                            GraphicsDisplay.CAPSULE_COLOR,
                            GraphicsDisplay.CAPSULE_COLOR,
                            null,
                            null,
                            1);
            capsuleImages.put(capsule, dot);
        }
        return capsuleImages;
    }

    private void removeFood(final int x, final int y, final List<List> foodImages) {
        GraphicsUtils.remove_from_screen(foodImages.get(x).get(y), null, null);
    }

    private void removeCapsule(final PositionGrid cell, final Map<PositionGrid, Object> capsuleImages) {
        GraphicsUtils.remove_from_screen(capsuleImages.get(cell), null, null);
    }

    /** Draws an overlay of expanded grid positions for search agents */
    private void drawExpandedCells(final List<Position> cells) {
        final float n = cells.size();
        List<Double> baseColor = Arrays.asList(1.0, 0.0, 0.0);
        this.clearExpandedCells();
        this.expandedCells = new ArrayList();
        for(int k=0; k<cells.size(); k++) {
            final Position cell = cells.get(k);
            final Position screenPos = this.to_screen(cell);
            List<Double> newColor = new ArrayList<>();
            for(Double c : baseColor) {
                newColor.add(0.25 + ((n-k*c*0.5/n)));
            }
            final String cellColor = GraphicsUtils.formatColor(newColor.get(0), newColor.get(1), newColor.get(2));
            final Object block = square(screenPos.getX(), screenPos.getY(),
                     0.5 * gridSize,
                     cellColor,
                     1,
                     2);
            this.expandedCells.add(block);
            if(this.frameTime < 0) {
                GraphicsUtils.refresh();
            }
        }
    }

    private void clearExpandedCells() {
        if(!this.expandedCells.isEmpty()) {
            for(Object cell : this.expandedCells) {
                GraphicsUtils.remove_from_screen(cell, null, null);
            }
        }
    }


    /** Draws an agent's belief distributions */
    private void updateDistributions(final List<Map<Position,Double>> distributions) {
        if(this.distributionImages == null) {
            this.drawDistributions(this.previousState);
        }
        for(int x=0; x<this.distributionImages.size(); x++) {
            for(int y=0; y<this.distributionImages.get(0).size(); y++) {
                final Object image = this.distributionImages.get(x).get(y);
                final List<Double> weights = new ArrayList<>();
                for(Map<Position,Double> dist : distributions) {
                    weights.add(dist.get(Position.newInstance(x, y)));
                }
                // Fog of war
                List<Double> color = Arrays.asList(0.0,0.0,0.0);
                List<List<Double>> colors = GraphicsDisplay.GHOST_VEC_COLORS.subList(1, GraphicsDisplay.GHOST_VEC_COLORS.size());  // With Pacman
                if(this.capture) {
                    colors = GraphicsDisplay.GHOST_VEC_COLORS;
                }
                for(int i=0; i<weights.size(); i++) {
                    final Double weight = weights.get(i);
                    final List<Double> gcolor = colors.get(i);
                    final List<Double> foo = new ArrayList();
                    for(int j=0; j<color.size(); j++) {
                        final double c = color.get(j);
                        final double g = gcolor.get(j);
                        final double f = Math.min(1.0, c + 0.95 * g * Math.pow(weight, 0.3));
                        foo.add(f);
                    }
                    color = Arrays.asList(foo.get(0), foo.get(1), foo.get(2));
                }
                GraphicsUtils.changeColor(image, GraphicsUtils.formatColor(color.get(0), color.get(1), color.get(2)));
            }
        }
        GraphicsUtils.refresh();
    }
    
    protected void setCurrentGhostImages(final int index, final Object o) {
        currentGhostImages.put(index, o);
    }
    
    protected static void setLayout(final Layout l) {
        layout = l;
    }
}
