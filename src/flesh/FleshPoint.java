package flesh;

import java.awt.Point;

/**
 *
 * @author Rexford
 */
public class FleshPoint {

    public static double screenScrollX;
    public static double screenScrollY;
    
    public double x;
    public double y;

    public FleshPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return (int) x + (int) screenScrollX;
    }

    public int y() {
        return (int) y - (int) screenScrollY;
    }

    public static FleshPoint polymorph(Object o) {
        if (o instanceof FleshPoint) {
            FleshPoint f1 = (FleshPoint)o;
            return new FleshPoint(f1.x, f1.y);
        } else if (o instanceof Point) {
            Point f1 = (Point)o;
            return new FleshPoint(f1.x, f1.y);
        }
        return null;
    }
    
    @Override
    public String toString(){
        return "(" + x + "," + y +")";
    }
}
