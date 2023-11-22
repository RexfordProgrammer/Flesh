package flesh.entities;

import flesh.Flesh;
import static flesh.Flesh.slowMo;
import flesh.FleshMath;
import flesh.FleshPoint;
import java.awt.Color;
import java.awt.Graphics2D;

public class FleshGrenadeEntity extends FleshProjectileEntity {

    FleshPoint destination;
    boolean isExploded = false;
    boolean skipTurn = false;
    public double explosionSize;

    public FleshGrenadeEntity(int size, int speed, double direction, double damage, double range, double explosionSize, FleshPoint location, Color color) {
        super(size, speed, direction, damage, location, color);
        this.range = range;
        this.explosionSize = explosionSize;
        this.isVolitile = false;

        destination = FleshMath.translatePoint(location, direction, range);
    }

    @Override
    public void tickerUp(Graphics2D g2d) {
        g2d.setColor(color);        
        
        if (!flesh.Flesh.isGamePaused && !this.skipTurn) {
            if (FleshMath.distanceBetweenPoints(FleshMath.epicenter(location, size), FleshMath.epicenter(destination, size)) < 5 && !isExploded) {
                isExploded = true;
                isVolitile = true;
                size = (int) explosionSize;
                color = Color.ORANGE;
            } else if (!isExploded) {
                location = FleshMath.translatePoint(location, direction, speed);
            }
            if (slowMo) {
                this.skipTurn = true;
            }
        }else if (skipTurn && slowMo) {
            this.skipTurn = false;
        }
        if (size < 3) {
            Flesh.projectileEntities.remove(this);
        } else {
            if (!isExploded) {
                g2d.fillOval(location.x() - size / 2,
                        location.y() - size / 2,
                        size,
                        size
                );
            } else if (isExploded) {
                if (!flesh.Flesh.isGamePaused) {
                    size -= 7;
                }
                g2d.fillOval((int) (location.x - size / 2),
                        (int) (location.y - size / 2),
                        (int) size,
                        (int) size
                );
            }
        }
    }
    
    @Override
    public void hit() {
    }
}
