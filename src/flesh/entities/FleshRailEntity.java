package flesh.entities;

import flesh.Flesh;
import static flesh.Flesh.isGamePaused;
import static flesh.Flesh.slowMo;
import flesh.FleshMath;
import flesh.FleshPoint;
import java.awt.Color;
import java.awt.Graphics2D;

public class FleshRailEntity extends FleshProjectileEntity {

    FleshPoint destination;
    private boolean skipTurn;

    public FleshRailEntity(int size, int speed, double range, double direction,
            double damage, FleshPoint location, Color color) {
        super(size, speed, direction, damage, location, color);
        this.range = range;
        this.isVolitile = true;

        destination = FleshMath.translatePoint(location, direction, range);
    }

    @Override
    public void tickerUp(Graphics2D g2d) {
        if (!slowMo){
            this.skipTurn = false;
        }
        
        if (!isGamePaused && !this.skipTurn) {
            location = FleshMath.translatePoint(location, direction, speed);
            if (slowMo) {
                this.skipTurn = true;
            }
        } else if (skipTurn && slowMo) {
            this.skipTurn = false;
        }

        if (Flesh.outOfBounds(this) || (range > 0 && FleshMath.distanceBetweenPoints(location, destination) < 5)) {
            Flesh.projectileEntities.remove(this);
        } else {
            g2d.setColor(color);
            g2d.fillOval(location.x() - size / 2,
                    location.y() - size / 2,
                    size,
                    size
            );
        }
    }

    @Override
    public void hit() {
    }
}
