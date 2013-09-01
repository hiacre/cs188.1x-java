package common;



public class Position {

    private final double x;
    private final double y;
    
    private Position(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    public static Position newInstance(final double x, final double y) {
        return new Position(x, y);
    }
    
    /**
     * Finds the nearest grid point to a position (discretizes)
     */
    public static Position nearestPoint(final double x, final double y) {
        final int gridRow = (int)(x+0.5);
        final int gridCol = (int)(y+0.5);
        return new Position(gridRow, gridCol);
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double manhattanDistance(final Position point) {
        return Math.abs(getX()-point.getX()) + Math.abs(getY()-point.getY());
    }

    public int getRoundedX() {
        return (int)(x+0.5);
    }

    public int getRoundedY() {
        return (int)(y+0.5);
    }

    public int getFloorX() {
        return (int)x;
    }

    public int getFloorY() {
        return (int)y;
    }
}
