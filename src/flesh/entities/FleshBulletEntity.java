package flesh.entities;

import flesh.Flesh;
import static flesh.Flesh.isGamePaused;
import static flesh.Flesh.slowMo;
import flesh.FleshMath;
import flesh.FleshPoint;
import java.awt.Color;
import java.awt.Graphics2D;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Rexford
 */
public class FleshBulletEntity extends FleshProjectileEntity {
     Media sound = new Media(getClass().getResource("/flesh/audio/pistol_fire.mp3").toString());
    //MediaPlayer mediaPlayer = new MediaPlayer(sound);
    FleshPoint destination;
    private boolean skipTurn;

    public FleshBulletEntity(int size, int speed, double range, double direction,
            double damage, FleshPoint location, Color color) {
        super(size, speed, direction, damage, location, color);
        this.range = range;
        this.isVolitile = true;
            MediaPlayer mediaPlayer1 = new MediaPlayer(sound);
            mediaPlayer1.play();
        destination = FleshMath.translatePoint(location, direction, range);
    }

    /**
     *
     * @param g2d
     * Draws the screen
     */
    @Override
    public void tickerUp(Graphics2D g2d) {
        if (!slowMo){
            this.skipTurn = false;
        }
        
        if (!isGamePaused && !this.skipTurn) {
            location = FleshMath.translatePoint(location, direction, speed);
            if (slowMo)
                this.skipTurn = true;
        } else if (skipTurn && slowMo) {
            this.skipTurn = false;
        }
        // Removes out of bounds entitites?
        if (Flesh.outOfBounds(this) || (range > 0 && FleshMath.distanceBetweenPoints(location, destination) < 5)) {
            Flesh.projectileEntities.remove(this);
        } else {
            g2d.fillOval(location.x() - size / 2,
                    location.y() - size / 2,
                    size,
                    size
            );
        }
    }

    @Override
    public void hit() {
        Flesh.projectileEntities.remove(this);
    }
}
