package flesh.entities;

import flesh.Flesh;
import static flesh.Flesh.isGamePaused;
import static flesh.Flesh.slowMo;
import flesh.FleshMath;
import flesh.FleshPoint;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FleshZombieEntity extends FleshNPCEntity {

    FleshNPCEntity target;
    
    int picture;
    private boolean skipTurn;
    
    public FleshZombieEntity(double speed, double health, FleshPoint location, int picture, FleshNPCEntity target) {
        super(50, speed, health, location, Color.BLACK);
        this.target = target;
        this.picture = picture;
        try {
            image = ImageIO.read(FleshPlayerEntity.class.getResource("/flesh/images/zombie"+picture+".png"));
        } catch (IOException e) {
        }
    }
   
    //Paints zombie on Graphics 2D object
    @Override
    public void tickerUp(Graphics2D g2d) {
        if (!slowMo){
            this.skipTurn = false;
        }
        
        if (!isGamePaused && !this.skipTurn) {
            direction = FleshMath.angleBetweenPoints(location, target.location);
            location = FleshMath.translatePoint(location, direction, speed);
            if (slowMo)
                this.skipTurn = true;
        } else if (skipTurn && slowMo) {
            this.skipTurn = false;
        }

        AffineTransform at = new AffineTransform();

        // 4. translate it to the center of the component
        at.translate(location.x(), location.y());

        // 3. do the actual rotation
        at.rotate(Math.toRadians(FleshMath.angleToPlayer(location) + 90));
        // 1. translate the object so that you rotate it around the 
        //    center 
        at.translate(-image.getWidth(null) / 2, -image.getHeight(null) / 2);

        g2d.drawImage(image, at, null);
    }

    public static FleshZombieEntity randomZombie(boolean beast) {
        int size = randomZombieSize();
        double speed = waveZombieSpeed(size);
        double health = waveZombieHealth(size);
        FleshPoint location = randomZombieLocation(size);
        int picture = randomZombiePicture();
        if (!beast) {
            return new FleshZombieEntity(speed, health, location, picture, Flesh.player);
        } else {
            location = randomZombieLocation(size * 10);
            return new FleshZombieEntity(speed * 4, health * 7, location, picture, Flesh.player);
        }
    }
   //Generates locatiuon of zombie spawn
    private static FleshPoint randomZombieLocation(int size) {
        double randomX = Flesh.j1.getWidth() + size;
        double randomY = Flesh.j1.getHeight() + size;
        int randomState = (int) (Math.random() * 4);

        switch (randomState) {
            case 0:
                randomX = Math.random() * Flesh.j1.getWidth();
                break;
            case 1:
                randomY = Math.random() * Flesh.j1.getHeight();
                break;
            case 2:
                randomX = Math.random() * Flesh.j1.getWidth();
                randomY = -size;
                break;
            case 3:
                randomX = -size;
                randomY = Math.random() * Flesh.j1.getHeight();
                break;
        }

        return new FleshPoint(randomX, randomY);
    }

    private static double waveZombieSpeed(int size) {
        return (double) (1 - Math.pow(.5, Flesh.numWave) * (size / 50));
    }

    private static int randomZombiePicture() {
        return  (int) (Math.random() * 10+1);
    }

    private static int randomZombieSize() {
        return (int) (Math.random() * 30 + 21);
    }

    private static double waveZombieHealth(int size) {
        double health = ((int) (Math.random() * 200 + 101)) * (size / 50.0);
        return health;
    }
}
 
    //public void readImage(){
    //    try {
    //        image = ImageIO.read(FleshPlayerEntity.class.getResource("/flesh/images/zombie"+picture+".png"));
    //    } catch (IOException e) {
    //    }
    //}
    