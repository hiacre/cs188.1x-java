package util;

public class PositionStandard implements Position {

    private final double x;
    private final double y;
    
    private PositionStandard(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    public static PositionStandard newInstance(final double x, final double y) {
        return new PositionStandard(x, y);
    }
    
    /**
     * Finds the nearest grid point to a position (discretizes)
     */
    public static Position nearestPoint(final double x, final double y) {
        final int gridRow = (int)(x+0.5);
        final int gridCol = (int)(y+0.5);
        return new PositionStandard(gridRow, gridRow);
    }
    
    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double manhattanDistance(final Position point) {
        return Math.abs(getX()-point.getX()) + Math.abs(getY()-point.getY());
    }

    @Override
    public int getRoundedX() {
        return (int)(x+0.5);
    }

    @Override
    public int getRoundedY() {
        return (int)(y+0.5);
    }

    @Override
    public int getFloorX() {
        return (int)x;
    }

    @Override
    public int getFloorY() {
        return (int)y;
    }
    
}
