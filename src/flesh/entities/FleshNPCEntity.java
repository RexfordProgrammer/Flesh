package flesh.entities;

import flesh.Flesh;
import flesh.FleshMath;
import flesh.FleshPoint;
import java.awt.Color;
import java.awt.Image;

/**
 *
 * @author Rexford
 */
public abstract class FleshNPCEntity extends FleshEntity {

    public double baseHealth;
    public double health;
    public double speed;
    public double direction;
    public int currentWeapIndex = 0;
    public Color color;
    public Image image;

    
    public FleshNPCEntity(int size, double speed, double health, FleshPoint location, Color color) {
        super(size, location);
        this.speed = speed;
        this.baseHealth = health;
        this.health = health;
        this.color = color;
    }

    public void dieNPC(FleshProjectileEntity projectileEntity) {
        health -= projectileEntity.damage;
        if (health <= 0) {
            if (Math.random() > .8) {
                flesh.Flesh.resources.add(FleshMath.randomResource(this.location));
            }
            Flesh.score += baseHealth;
            flesh.Flesh.NPCEntities.remove(this);
        }
    }
}