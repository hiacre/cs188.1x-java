package pacman;

import java.util.HashMap;

/**
 *
 * @author archie
 */
public class PacmanGraphicsNonText {
    private final int have_window;
    private final HashMap<Object, Object> currentGhostImages;
    private final double zoom;
    private final boolean capture;
    private final double frameTime;
    private boolean isBlue;
    private GameState1 previousState;

    public PacmanGraphicsNonText(Double zoom, Double frameTime, Boolean capture) {
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

    public void initialize(final GameState1 state, Boolean isBlue) {
        this.isBlue = isBlue == null ? false : isBlue;
        
        this.startGraphics(state);

        // this.drawDistributions(state)
        this.distributionImages = null;  // Initialized lazily
        this.drawStaticObjects(state);
        this.drawAgentObjects(state);

        // Information
        this.previousState = state;
    }
    

    public void startGraphics(final GameState1 state) {
        this.layout = state.layout;
        layout = this.layout;
        this.width = layout.width;
        this.height = layout.height;
        this.make_window(this.width, this.height);
        this.infoPane = InfoPane(layout, this.gridSize);
        this.currentState = layout;
    }

    def drawDistributions(self, state):
        walls = state.layout.walls
        dist = []
        for x in range(walls.width):
            distx = []
            dist.append(distx)
            for y in range(walls.height):
                ( screen_x, screen_y ) = this.to_screen( (x, y) )
                block = square( (screen_x, screen_y),
                                0.5 * this.gridSize,
                                color = BACKGROUND_COLOR,
                                filled = 1, behind=2)
                distx.append(block)
        this.distributionImages = dist

    def drawStaticObjects(self, state):
        layout = this.layout
        this.drawWalls(layout.walls)
        this.food = this.drawFood(layout.food)
        this.capsules = this.drawCapsules(layout.capsules)
        refresh()

    def drawAgentObjects(self, state):
        this.agentImages = [] # (agentState, image)
        for index, agent in enumerate(state.agentStates):
            if agent.isPacman:
                image = this.drawPacman(agent, index)
                this.agentImages.append( (agent, image) )
            else:
                image = this.drawGhost(agent, index)
                this.agentImages.append( (agent, image) )
        refresh()

    def swapImages(self, agentIndex, newState):
        """
          Changes an image from a ghost to a pacman or vis versa (for capture)
        """
        prevState, prevImage = this.agentImages[agentIndex]
        for item in prevImage: remove_from_screen(item)
        if newState.isPacman:
            image = this.drawPacman(newState, agentIndex)
            this.agentImages[agentIndex] = (newState, image )
        else:
            image = this.drawGhost(newState, agentIndex)
            this.agentImages[agentIndex] = (newState, image )
        refresh()

    def update(self, newState):
        agentIndex = newState._agentMoved
        agentState = newState.agentStates[agentIndex]

        if this.agentImages[agentIndex][0].isPacman != agentState.isPacman: this.swapImages(agentIndex, agentState)
        prevState, prevImage = this.agentImages[agentIndex]
        if agentState.isPacman:
            this.animatePacman(agentState, prevState, prevImage)
        else:
            this.moveGhost(agentState, agentIndex, prevState, prevImage)
        this.agentImages[agentIndex] = (agentState, prevImage)

        if newState._foodEaten != None:
            this.removeFood(newState._foodEaten, this.food)
        if newState._capsuleEaten != None:
            this.removeCapsule(newState._capsuleEaten, this.capsules)
        this.infoPane.updateScore(newState.score)
        if 'ghostDistances' in dir(newState):
            this.infoPane.updateGhostDistances(newState.ghostDistances)

    def make_window(self, width, height):
        grid_width = (width-1) * this.gridSize
        grid_height = (height-1) * this.gridSize
        screen_width = 2*this.gridSize + grid_width
        screen_height = 2*this.gridSize + grid_height + INFO_PANE_HEIGHT

        begin_graphics(screen_width,
                       screen_height,
                       BACKGROUND_COLOR,
                       "CS188 Pacman")

    def drawPacman(self, pacman, index):
        position = this.getPosition(pacman)
        screen_point = this.to_screen(position)
        endpoints = this.getEndpoints(this.getDirection(pacman))

        width = PACMAN_OUTLINE_WIDTH
        outlineColor = PACMAN_COLOR
        fillColor = PACMAN_COLOR

        if this.capture:
            outlineColor = TEAM_COLORS[index % 2]
            fillColor = GHOST_COLORS[index]
            width = PACMAN_CAPTURE_OUTLINE_WIDTH

        return [circle(screen_point, PACMAN_SCALE * this.gridSize,
                       fillColor = fillColor, outlineColor = outlineColor,
                       endpoints = endpoints,
                       width = width)]

    def getEndpoints(self, direction, position=(0,0)):
        x, y = position
        pos = x - int(x) + y - int(y)
        width = 30 + 80 * math.sin(math.pi* pos)

        delta = width / 2
        if (direction == 'West'):
            endpoints = (180+delta, 180-delta)
        elif (direction == 'North'):
            endpoints = (90+delta, 90-delta)
        elif (direction == 'South'):
            endpoints = (270+delta, 270-delta)
        else:
            endpoints = (0+delta, 0-delta)
        return endpoints

    def movePacman(self, position, direction, image):
        screenPosition = this.to_screen(position)
        endpoints = this.getEndpoints( direction, position )
        r = PACMAN_SCALE * this.gridSize
        moveCircle(image[0], screenPosition, r, endpoints)
        refresh()

    def animatePacman(self, pacman, prevPacman, image):
        if this.frameTime < 0:
            print 'Press any key to step forward, "q" to play'
            keys = wait_for_keys()
            if 'q' in keys:
                this.frameTime = 0.1
        if this.frameTime > 0.01 or this.frameTime < 0:
            start = time.time()
            fx, fy = this.getPosition(prevPacman)
            px, py = this.getPosition(pacman)
            frames = 4.0
            for i in range(1,int(frames) + 1):
                pos = px*i/frames + fx*(frames-i)/frames, py*i/frames + fy*(frames-i)/frames
                this.movePacman(pos, this.getDirection(pacman), image)
                refresh()
                sleep(abs(this.frameTime) / frames)
        else:
            this.movePacman(this.getPosition(pacman), this.getDirection(pacman), image)
        refresh()

    def getGhostColor(self, ghost, ghostIndex):
        if ghost.scaredTimer > 0:
            return SCARED_COLOR
        else:
            return GHOST_COLORS[ghostIndex]

    def drawGhost(self, ghost, agentIndex):
        pos = this.getPosition(ghost)
        dir = this.getDirection(ghost)
        (screen_x, screen_y) = (this.to_screen(pos) )
        coords = []
        for (x, y) in GHOST_SHAPE:
            coords.append((x*this.gridSize*GHOST_SIZE + screen_x, y*this.gridSize*GHOST_SIZE + screen_y))

        colour = this.getGhostColor(ghost, agentIndex)
        body = polygon(coords, colour, filled = 1)
        WHITE = formatColor(1.0, 1.0, 1.0)
        BLACK = formatColor(0.0, 0.0, 0.0)

        dx = 0
        dy = 0
        if dir == 'North':
            dy = -0.2
        if dir == 'South':
            dy = 0.2
        if dir == 'East':
            dx = 0.2
        if dir == 'West':
            dx = -0.2
        leftEye = circle((screen_x+this.gridSize*GHOST_SIZE*(-0.3+dx/1.5), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy/1.5)), this.gridSize*GHOST_SIZE*0.2, WHITE, WHITE)
        rightEye = circle((screen_x+this.gridSize*GHOST_SIZE*(0.3+dx/1.5), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy/1.5)), this.gridSize*GHOST_SIZE*0.2, WHITE, WHITE)
        leftPupil = circle((screen_x+this.gridSize*GHOST_SIZE*(-0.3+dx), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy)), this.gridSize*GHOST_SIZE*0.08, BLACK, BLACK)
        rightPupil = circle((screen_x+this.gridSize*GHOST_SIZE*(0.3+dx), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy)), this.gridSize*GHOST_SIZE*0.08, BLACK, BLACK)
        ghostImageParts = []
        ghostImageParts.append(body)
        ghostImageParts.append(leftEye)
        ghostImageParts.append(rightEye)
        ghostImageParts.append(leftPupil)
        ghostImageParts.append(rightPupil)

        return ghostImageParts

    def moveEyes(self, pos, dir, eyes):
        (screen_x, screen_y) = (this.to_screen(pos) )
        dx = 0
        dy = 0
        if dir == 'North':
            dy = -0.2
        if dir == 'South':
            dy = 0.2
        if dir == 'East':
            dx = 0.2
        if dir == 'West':
            dx = -0.2
        moveCircle(eyes[0],(screen_x+this.gridSize*GHOST_SIZE*(-0.3+dx/1.5), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy/1.5)), this.gridSize*GHOST_SIZE*0.2)
        moveCircle(eyes[1],(screen_x+this.gridSize*GHOST_SIZE*(0.3+dx/1.5), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy/1.5)), this.gridSize*GHOST_SIZE*0.2)
        moveCircle(eyes[2],(screen_x+this.gridSize*GHOST_SIZE*(-0.3+dx), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy)), this.gridSize*GHOST_SIZE*0.08)
        moveCircle(eyes[3],(screen_x+this.gridSize*GHOST_SIZE*(0.3+dx), screen_y-this.gridSize*GHOST_SIZE*(0.3-dy)), this.gridSize*GHOST_SIZE*0.08)

    def moveGhost(self, ghost, ghostIndex, prevGhost, ghostImageParts):
        old_x, old_y = this.to_screen(this.getPosition(prevGhost))
        new_x, new_y = this.to_screen(this.getPosition(ghost))
        delta = new_x - old_x, new_y - old_y

        for ghostImagePart in ghostImageParts:
            move_by(ghostImagePart, delta)
        refresh()

        if ghost.scaredTimer > 0:
            color = SCARED_COLOR
        else:
            color = GHOST_COLORS[ghostIndex]
        edit(ghostImageParts[0], ('fill', color), ('outline', color))
        this.moveEyes(this.getPosition(ghost), this.getDirection(ghost), ghostImageParts[-4:])
        refresh()

    def getPosition(self, agentState):
        if agentState.configuration == None: return (-1000, -1000)
        return agentState.getPosition()

    def getDirection(self, agentState):
        if agentState.configuration == None: return Directions.STOP
        return agentState.configuration.getDirection()

    def finish(self):
        end_graphics()

    def to_screen(self, point):
        ( x, y ) = point
        #y = this.height - y
        x = (x + 1)*this.gridSize
        y = (this.height  - y)*this.gridSize
        return ( x, y )

    # Fixes some TK issue with off-center circles
    def to_screen2(self, point):
        ( x, y ) = point
        #y = this.height - y
        x = (x + 1)*this.gridSize
        y = (this.height  - y)*this.gridSize
        return ( x, y )

    def drawWalls(self, wallMatrix):
        wallColor = WALL_COLOR
        for xNum, x in enumerate(wallMatrix):
            if this.capture and (xNum * 2) < wallMatrix.width: wallColor = TEAM_COLORS[0]
            if this.capture and (xNum * 2) >= wallMatrix.width: wallColor = TEAM_COLORS[1]

            for yNum, cell in enumerate(x):
                if cell: # There's a wall here
                    pos = (xNum, yNum)
                    screen = this.to_screen(pos)
                    screen2 = this.to_screen2(pos)

                    # draw each quadrant of the square based on adjacent walls
                    wIsWall = this.isWall(xNum-1, yNum, wallMatrix)
                    eIsWall = this.isWall(xNum+1, yNum, wallMatrix)
                    nIsWall = this.isWall(xNum, yNum+1, wallMatrix)
                    sIsWall = this.isWall(xNum, yNum-1, wallMatrix)
                    nwIsWall = this.isWall(xNum-1, yNum+1, wallMatrix)
                    swIsWall = this.isWall(xNum-1, yNum-1, wallMatrix)
                    neIsWall = this.isWall(xNum+1, yNum+1, wallMatrix)
                    seIsWall = this.isWall(xNum+1, yNum-1, wallMatrix)

                    # NE quadrant
                    if (not nIsWall) and (not eIsWall):
                        # inner circle
                        circle(screen2, WALL_RADIUS * this.gridSize, wallColor, wallColor, (0,91), 'arc')
                    if (nIsWall) and (not eIsWall):
                        # vertical line
                        line(add(screen, (this.gridSize*WALL_RADIUS, 0)), add(screen, (this.gridSize*WALL_RADIUS, this.gridSize*(-0.5)-1)), wallColor)
                    if (not nIsWall) and (eIsWall):
                        # horizontal line
                        line(add(screen, (0, this.gridSize*(-1)*WALL_RADIUS)), add(screen, (this.gridSize*0.5+1, this.gridSize*(-1)*WALL_RADIUS)), wallColor)
                    if (nIsWall) and (eIsWall) and (not neIsWall):
                        # outer circle
                        circle(add(screen2, (this.gridSize*2*WALL_RADIUS, this.gridSize*(-2)*WALL_RADIUS)), WALL_RADIUS * this.gridSize-1, wallColor, wallColor, (180,271), 'arc')
                        line(add(screen, (this.gridSize*2*WALL_RADIUS-1, this.gridSize*(-1)*WALL_RADIUS)), add(screen, (this.gridSize*0.5+1, this.gridSize*(-1)*WALL_RADIUS)), wallColor)
                        line(add(screen, (this.gridSize*WALL_RADIUS, this.gridSize*(-2)*WALL_RADIUS+1)), add(screen, (this.gridSize*WALL_RADIUS, this.gridSize*(-0.5))), wallColor)

                    # NW quadrant
                    if (not nIsWall) and (not wIsWall):
                        # inner circle
                        circle(screen2, WALL_RADIUS * this.gridSize, wallColor, wallColor, (90,181), 'arc')
                    if (nIsWall) and (not wIsWall):
                        # vertical line
                        line(add(screen, (this.gridSize*(-1)*WALL_RADIUS, 0)), add(screen, (this.gridSize*(-1)*WALL_RADIUS, this.gridSize*(-0.5)-1)), wallColor)
                    if (not nIsWall) and (wIsWall):
                        # horizontal line
                        line(add(screen, (0, this.gridSize*(-1)*WALL_RADIUS)), add(screen, (this.gridSize*(-0.5)-1, this.gridSize*(-1)*WALL_RADIUS)), wallColor)
                    if (nIsWall) and (wIsWall) and (not nwIsWall):
                        # outer circle
                        circle(add(screen2, (this.gridSize*(-2)*WALL_RADIUS, this.gridSize*(-2)*WALL_RADIUS)), WALL_RADIUS * this.gridSize-1, wallColor, wallColor, (270,361), 'arc')
                        line(add(screen, (this.gridSize*(-2)*WALL_RADIUS+1, this.gridSize*(-1)*WALL_RADIUS)), add(screen, (this.gridSize*(-0.5), this.gridSize*(-1)*WALL_RADIUS)), wallColor)
                        line(add(screen, (this.gridSize*(-1)*WALL_RADIUS, this.gridSize*(-2)*WALL_RADIUS+1)), add(screen, (this.gridSize*(-1)*WALL_RADIUS, this.gridSize*(-0.5))), wallColor)

                    # SE quadrant
                    if (not sIsWall) and (not eIsWall):
                        # inner circle
                        circle(screen2, WALL_RADIUS * this.gridSize, wallColor, wallColor, (270,361), 'arc')
                    if (sIsWall) and (not eIsWall):
                        # vertical line
                        line(add(screen, (this.gridSize*WALL_RADIUS, 0)), add(screen, (this.gridSize*WALL_RADIUS, this.gridSize*(0.5)+1)), wallColor)
                    if (not sIsWall) and (eIsWall):
                        # horizontal line
                        line(add(screen, (0, this.gridSize*(1)*WALL_RADIUS)), add(screen, (this.gridSize*0.5+1, this.gridSize*(1)*WALL_RADIUS)), wallColor)
                    if (sIsWall) and (eIsWall) and (not seIsWall):
                        # outer circle
                        circle(add(screen2, (this.gridSize*2*WALL_RADIUS, this.gridSize*(2)*WALL_RADIUS)), WALL_RADIUS * this.gridSize-1, wallColor, wallColor, (90,181), 'arc')
                        line(add(screen, (this.gridSize*2*WALL_RADIUS-1, this.gridSize*(1)*WALL_RADIUS)), add(screen, (this.gridSize*0.5, this.gridSize*(1)*WALL_RADIUS)), wallColor)
                        line(add(screen, (this.gridSize*WALL_RADIUS, this.gridSize*(2)*WALL_RADIUS-1)), add(screen, (this.gridSize*WALL_RADIUS, this.gridSize*(0.5))), wallColor)

                    # SW quadrant
                    if (not sIsWall) and (not wIsWall):
                        # inner circle
                        circle(screen2, WALL_RADIUS * this.gridSize, wallColor, wallColor, (180,271), 'arc')
                    if (sIsWall) and (not wIsWall):
                        # vertical line
                        line(add(screen, (this.gridSize*(-1)*WALL_RADIUS, 0)), add(screen, (this.gridSize*(-1)*WALL_RADIUS, this.gridSize*(0.5)+1)), wallColor)
                    if (not sIsWall) and (wIsWall):
                        # horizontal line
                        line(add(screen, (0, this.gridSize*(1)*WALL_RADIUS)), add(screen, (this.gridSize*(-0.5)-1, this.gridSize*(1)*WALL_RADIUS)), wallColor)
                    if (sIsWall) and (wIsWall) and (not swIsWall):
                        # outer circle
                        circle(add(screen2, (this.gridSize*(-2)*WALL_RADIUS, this.gridSize*(2)*WALL_RADIUS)), WALL_RADIUS * this.gridSize-1, wallColor, wallColor, (0,91), 'arc')
                        line(add(screen, (this.gridSize*(-2)*WALL_RADIUS+1, this.gridSize*(1)*WALL_RADIUS)), add(screen, (this.gridSize*(-0.5), this.gridSize*(1)*WALL_RADIUS)), wallColor)
                        line(add(screen, (this.gridSize*(-1)*WALL_RADIUS, this.gridSize*(2)*WALL_RADIUS-1)), add(screen, (this.gridSize*(-1)*WALL_RADIUS, this.gridSize*(0.5))), wallColor)

    def isWall(self, x, y, walls):
        if x < 0 or y < 0:
            return False
        if x >= walls.width or y >= walls.height:
            return False
        return walls[x][y]

    def drawFood(self, foodMatrix ):
        foodImages = []
        color = FOOD_COLOR
        for xNum, x in enumerate(foodMatrix):
            if this.capture and (xNum * 2) <= foodMatrix.width: color = TEAM_COLORS[0]
            if this.capture and (xNum * 2) > foodMatrix.width: color = TEAM_COLORS[1]
            imageRow = []
            foodImages.append(imageRow)
            for yNum, cell in enumerate(x):
                if cell: # There's food here
                    screen = this.to_screen((xNum, yNum ))
                    dot = circle( screen,
                                  FOOD_SIZE * this.gridSize,
                                  outlineColor = color, fillColor = color,
                                  width = 1)
                    imageRow.append(dot)
                else:
                    imageRow.append(None)
        return foodImages

    def drawCapsules(self, capsules ):
        capsuleImages = {}
        for capsule in capsules:
            ( screen_x, screen_y ) = this.to_screen(capsule)
            dot = circle( (screen_x, screen_y),
                              CAPSULE_SIZE * this.gridSize,
                              outlineColor = CAPSULE_COLOR,
                              fillColor = CAPSULE_COLOR,
                              width = 1)
            capsuleImages[capsule] = dot
        return capsuleImages

    def removeFood(self, cell, foodImages ):
        x, y = cell
        remove_from_screen(foodImages[x][y])

    def removeCapsule(self, cell, capsuleImages ):
        x, y = cell
        remove_from_screen(capsuleImages[(x, y)])

    def drawExpandedCells(self, cells):
        """
        Draws an overlay of expanded grid positions for search agents
        """
        n = float(len(cells))
        baseColor = [1.0, 0.0, 0.0]
        this.clearExpandedCells()
        this.expandedCells = []
        for k, cell in enumerate(cells):
            screenPos = this.to_screen( cell)
            cellColor = formatColor(*[(n-k) * c * .5 / n + .25 for c in baseColor])
            block = square(screenPos,
                     0.5 * this.gridSize,
                     color = cellColor,
                     filled = 1, behind=2)
            this.expandedCells.append(block)
            if this.frameTime < 0:
                refresh()

    def clearExpandedCells(self):
        if 'expandedCells' in dir(self) and len(this.expandedCells) > 0:
            for cell in this.expandedCells:
                remove_from_screen(cell)


    def updateDistributions(self, distributions):
        "Draws an agent's belief distributions"
        if this.distributionImages == None:
            this.drawDistributions(this.previousState)
        for x in range(len(this.distributionImages)):
            for y in range(len(this.distributionImages[0])):
                image = this.distributionImages[x][y]
                weights = [dist[ (x,y) ] for dist in distributions]

                if sum(weights) != 0:
                    pass
                # Fog of war
                color = [0.0,0.0,0.0]
                colors = GHOST_VEC_COLORS[1:] # With Pacman
                if this.capture: colors = GHOST_VEC_COLORS
                for weight, gcolor in zip(weights, colors):
                    color = [min(1.0, c + 0.95 * g * weight ** .3) for c,g in zip(color, gcolor)]
                changeColor(image, formatColor(*color))
        refresh()    
}
