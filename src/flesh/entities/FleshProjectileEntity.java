/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flesh.entities;

import flesh.FleshPoint;
import java.awt.Color;
//import java.awt.Graphics2D;
import java.util.Objects;

/**
 *
 * @author Rexford
 */
public abstract class FleshProjectileEntity extends FleshEntity {

    public int speed;
    public boolean isVolitile = true;
    public double direction;
    public double damage;
    public double range;
    public Color color;
    
    public FleshProjectileEntity(int size, int speed, double direction, double damage, FleshPoint location, Color color) {
        super(size, location);
        this.speed = speed;
        this.direction = direction;
        this.damage = damage;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FleshProjectileEntity) {
            FleshProjectileEntity projectile = (FleshProjectileEntity) o;
            if (this.speed == projectile.speed && this.damage == projectile.damage
                    && this.color == projectile.color && this.size == projectile.size
                    && this.range == projectile.range) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.speed;
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.damage) ^ (Double.doubleToLongBits(this.damage) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.range) ^ (Double.doubleToLongBits(this.range) >>> 32));
        hash = 67 * hash + Objects.hashCode(this.color);
        return hash;
    }
    
    public abstract void hit();
}
