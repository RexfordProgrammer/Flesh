package flesh.entities;

import flesh.FleshMath;
import flesh.FleshPoint;
import java.awt.Graphics2D;
//import java.io.IOException;

public abstract class FleshEntity implements Comparable {

    public int size;
    public FleshPoint location;

    public FleshEntity(int size, FleshPoint location) {
        this.size = size;
        this.location = location;
    }

    //This returns if the two "hitboxes" are intersepting
    @Override
    public int compareTo(Object o) {
        if (o instanceof FleshEntity) {
            FleshEntity f1 = (FleshEntity) o;

            FleshPoint p1 = new FleshPoint(location.x - size / 2, location.y - size / 2);
            FleshPoint p2 = new FleshPoint(f1.location.x - size / 2, f1.location.y - size / 2);
            System.out.println(FleshMath.distanceBetweenPoints(p1, p2));

            return -(int) (FleshMath.distanceBetweenPoints(p1, p2)
                    - size / 2 - f1.size / 2);
        }
        return -1;
    }
    public int compareToResource(Object o) {
        if (o instanceof FleshEntity) {
            FleshEntity f1 = (FleshEntity) o;

            FleshPoint p1 = new FleshPoint(location.x - size / 2, location.y - size / 2);
            FleshPoint p2 = new FleshPoint(f1.location.x - size / 2, f1.location.y - size / 2);

            System.out.println(FleshMath.distanceBetweenPoints(p1, p2));
            return -(int) (FleshMath.distanceBetweenPoints(p1, p2) - size / 2 - f1.size / 2);
        }
        return -1;
    }
    public abstract void tickerUp(Graphics2D g2d);
}
