/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author archie
 */
public class GameState2 {
class GameState:
    
    def __init__(self, position, size, visitedTopLeft, visitedTopRight, visitedBottomLeft, visitedBottomRight):
        self.position = position
        self.tl = visitedTopLeft
        self.tr = visitedTopRight
        self.bl = visitedBottomLeft
        self.br = visitedBottomRight
        self.size = size
        right = size[0]
        top = size[1]
        if position == (1,1):
            self.bl = True
        if position == (right, 1):
            self.br = True
        if position == (1, top):
            self.tl = True
        if position == (right, top):
            self.tr = True
        
    @staticmethod
    def fromGameState(gameState):
        gs = gameState
        return GameState(
                 gs.getPosition(),
                 gs.getSize(),
                 gs.getVisitedTopLeft(),
                 gs.getVisitedTopRight(),
                 gs.getVisitedBottomLeft(),
                 gs.getVisitedBottomRight())

    def getSize(self):
        return self.size
    def getPosition(self):
        return self.position
    def getVisitedTopLeft(self):
        return self.tl
    def getVisitedTopRight(self):
        return self.tr
    def getVisitedBottomLeft(self):
        return self.bl
    def getVisitedBottomRight(self):
        return self.br
    def copy(self, gameState):
        return (GameState(
                          gameState.getPosition(),
                          gameState.getSize(),
                          gameState.getVisitedTopLeft(),
                          gameState.getVisitedBottomLeft(),
                          gameState.getVisitedBottomRight()))
    def __str__(self):
        val = 'GameState Pos: ' + str(self.position) + ' Visited: '
        if self.tl:
            val += 'TL '
        if self.tr:
            val += 'TR '
        if self.bl:
            val += 'BL '
        if self.br:
            val += 'BR '
            
        return val
}
