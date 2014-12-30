package pacman;

public class DirectionVector {
    
    private double x;
    private double y;
    
    private DirectionVector(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    public static DirectionVector newInstance(final double x, final double y) {
        return new DirectionVector(x, y);
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public Direction toDirection() {
        if(y > 0) {
            return Direction.North;
        }
        if(y < 0) {
            return Direction.South;
        }
        if(x < 0) {
            return Direction.West;
        }
        if(x > 0) {
            return Direction.East;
        }
        return Direction.Stop;
    }
}
