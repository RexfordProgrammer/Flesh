package flesh;

import flesh.entities.FleshPlayerEntity;
import flesh.entities.FleshResourceEntity;
import flesh.resources.FleshGrenadeResource;
import flesh.resources.FleshMachinegunResource;
import flesh.resources.FleshPistolResource;
import flesh.resources.FleshRailgunResource;
import flesh.resources.FleshShotgunResource;
import flesh.resources.FleshWeaponResource;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FleshMath {

    private static FleshPoint mouseLocation = new FleshPoint(0, 0);
    public static double angleToMouse = 0;

    public static double angleBetweenPoints(FleshPoint p1, FleshPoint p2) {
        if (p1 != null && p2 != null) {
            double rise = p1.y - p2.y;
            double run = p1.x - p2.x;
            if (p1.x >= p2.x) {
                return Math.toDegrees(Math.atan((double) (rise) / run)) + 180;
            }
            return Math.toDegrees(Math.atan((double) (rise) / run));
        }
        return 0;
    }

    public static FleshPoint epicenter(FleshPoint p1, int size) {
        return new FleshPoint(p1.x - size / 2, p1.y - size / 2);
    }

    public static FleshPoint translatePoint(FleshPoint p1, double angle, double distance) {
        return new FleshPoint(distance * Math.cos(Math.toRadians(angle)) + p1.x, distance * Math.sin(Math.toRadians(angle)) + p1.y);
    }

    public static double distanceBetweenPoints(FleshPoint p1, FleshPoint p2) {
        if (p1 != null && p2 != null) {
            return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
        }
        return 69;
    }

    public static double angleToMouse() {
        if (Flesh.fleshInstance.getMousePosition() != null) {
            mouseLocation = FleshPoint.polymorph(Flesh.fleshInstance.getMousePosition());
        }
        angleToMouse = FleshMath.angleBetweenPoints(FleshPoint.polymorph(Flesh.player.location), mouseLocation);
        return angleToMouse;
    }

    public static double angleToPlayer(FleshPoint location) {
        return FleshMath.angleBetweenPoints(location, Flesh.player.location);
    }

    public static FleshResourceEntity randomResource(FleshPoint location) {
        int randomAmmo;
        int randomAdditiveHealth = 0;
        Color color = Color.BLACK;
        FleshWeaponResource weaponType = new FleshPistolResource(0);

        int i = (int) (Math.random() * 5);
        Image image = null;
        switch (i) {
            case 0:
                randomAdditiveHealth = 0;
                color = Color.BLUE;
                try {
                    image = ImageIO.read(FleshPlayerEntity.class.getResource("/flesh/images/ResourceImage.png"));
                } catch (IOException e) {
                }
                
                randomAmmo = (int) (Math.random() * 60 + 1);
                weaponType = new FleshShotgunResource(randomAmmo);
                break;
            case 1:
                randomAdditiveHealth = 0;
                color = Color.BLUE;
                try {
                    image = ImageIO.read(FleshPlayerEntity.class.getResource("/flesh/images/ResourceImage.png"));
                } catch (IOException e) {
                }

                randomAmmo = (int) (Math.random() * 10 + 1);
                weaponType = new FleshGrenadeResource(randomAmmo);
                break;
            case 2:
                randomAdditiveHealth = 0;
                color = Color.BLUE;
                try {
                    image = ImageIO.read(FleshPlayerEntity.class.getResource("/flesh/images/ResourceImage.png"));
                } catch (IOException e) {
                }

                randomAmmo = (int) (Math.random() * 150 + 1);
                weaponType = new FleshMachinegunResource(randomAmmo);
                break;
            case 3:
                randomAdditiveHealth = 0;
                color = Color.BLUE;
                try {
                    image = ImageIO.read(FleshPlayerEntity.class.getResource("/flesh/images/ResourceImage.png"));
                } catch (IOException e) {
                }

                randomAmmo = (int) (Math.random() * 30 + 1);
                weaponType = new FleshRailgunResource(randomAmmo);
                break;
            case 4:
                randomAdditiveHealth = (int) (Math.random() * 20 + 1);
                color = Color.RED;
                try {
                    image = ImageIO.read(FleshPlayerEntity.class.getResource("/flesh/images/HealthImage.png"));
                } catch (IOException e) {
                }
                weaponType = null;
                break;
        }
               
        return new FleshResourceEntity(location, weaponType, randomAdditiveHealth, color, image);
    }
}
