/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;


public class PositionStandard implements Position {

    private final int x;
    private final int y;
    
    private PositionStandard(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    public static PositionStandard newInstance(final int x, final int y) {
        return new PositionStandard(x, y);
    }
    
    /**
     * Finds the nearest grid point to a position (discretizes)
     */
    public static Position nearestPoint(final double x, final double y) {
        final int newX = (int)(x+0.5);
        final int newY = (int)(y+0.5);
        return new PositionStandard(newX, newY);
    }
    
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int manhattanDistance(final Position point) {
        return Math.abs(getX()-point.getX()) + Math.abs(getY()-point.getY());
    }
    
}
