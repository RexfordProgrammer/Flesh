package flesh.entities;

import static flesh.Flesh.player;
import flesh.FleshPoint;
import flesh.resources.FleshWeaponResource;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class FleshResourceEntity extends FleshEntity {

    double additiveHealth = 0;
    public Color color;
    public FleshWeaponResource weapon;
    public Image image;

    public FleshResourceEntity(FleshPoint location, FleshWeaponResource weapon, double health, Color color, Image image) {
        super(30, location);
        this.additiveHealth = health;
        this.color = color;
        this.image = image;
        this.weapon = weapon;
    }

    @Override
    public void tickerUp(Graphics2D g2d) {
        g2d.setColor(color);
        AffineTransform at = new AffineTransform();
        at.translate(location.x(), location.y());
        at.translate(-image.getWidth(null) / 2, -image.getHeight(null) / 2);
        g2d.drawImage(image, at, null);
    }

    public void hit() {
        int index = player.weapons.indexOf(weapon);
        if (weapon != null) {
            if (index < 0) {
                player.weapons.add(weapon);
            } else {
                player.weapons.get(index).ammo += weapon.ammo;
            }
        } else {
            if (player.health + additiveHealth < 100) {
                player.health += additiveHealth;
            } else {
                player.health = 100.0;
            }
        }

        flesh.Flesh.resources.remove(this);
    }
}
