package flesh.entities;

import flesh.Flesh;
import static flesh.Flesh.isGamePaused;
import static flesh.Flesh.skipTurn;
import static flesh.Flesh.slowMo;
import flesh.FleshMath;
import flesh.FleshPoint;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FleshBeastEntity extends FleshNPCEntity {

    FleshNPCEntity target;
    int picture;
    private boolean skipTurn;
    
    public FleshBeastEntity(double speed, double health, FleshPoint location, FleshNPCEntity target) {
        super(400, speed, health, location, Color.BLACK);
        this.target = target;
        readImage();
    }
    
    public void readImage(){
        try {
            image = ImageIO.read(FleshPlayerEntity.class.getResource("/flesh/images/zombie_beast.png"));
        } catch (IOException e) {
        }
    }
    
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
        at.scale(2,2);
        // 1. translate the object so that you rotate it around the 
        //    center 
        at.translate(-image.getWidth(null) / 2, -image.getHeight(null) / 2);

        g2d.drawImage(image, at, null);
    }

    public static FleshBeastEntity summonBeast(boolean beast) {
        FleshPoint location = randomZombieLocation(400);
        return new FleshBeastEntity(2, 2000, location, Flesh.player);
    }

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
        //System.out.println("Health: " + health + "Size: " + size);
        return health;
    }
    
    @Override
    public void dieNPC(FleshProjectileEntity projectileEntity) {
        health -= projectileEntity.damage;
        if (health <= 0) {
            for (int i = 0; i < 5; i++){
                FleshPoint p1 = new FleshPoint(this.location.x - (Math.random()*100), this.location.y - (Math.random()*100));
                flesh.Flesh.resources.add(FleshMath.randomResource(p1));
            }
            Flesh.score += baseHealth;
            flesh.Flesh.NPCEntities.remove(this);
        }
    }
}
