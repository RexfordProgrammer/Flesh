/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flesh.entities;

import flesh.FleshMath;
import flesh.FleshPoint;
import flesh.resources.FleshPistolResource;
import flesh.resources.FleshWeaponResource;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Rexford
 */
public class FleshPlayerEntity extends FleshNPCEntity {

    public boolean dirUp;
    public boolean dirDown;
    public boolean dirLeft;
    public boolean dirRight;
    public boolean isFiring;
    public long firePrevious = new Date().getTime();
    public CopyOnWriteArrayList<FleshWeaponResource> weapons = new CopyOnWriteArrayList<>();

    public FleshPlayerEntity(int size, double speed, double health, FleshPoint location, Color color) {
        super(size, speed, health, location, color);
        this.weapons.add(new FleshPistolResource(-1));       
        
        // Prev load image
        System.out.println(weapons.get(currentWeapIndex).weapName.toLowerCase());
        try {
            image = ImageIO.read(FleshPlayerEntity.class.getResource("/flesh/images/player_"+weapons.get(currentWeapIndex).weapName.toLowerCase()+".png"));
        } catch (IOException e) {
        }
    }
    public void loadImage(){
        System.out.println(weapons.get(currentWeapIndex).weapName.toLowerCase());
        try {
            image = ImageIO.read(FleshPlayerEntity.class.getResource("/flesh/images/player_"+weapons.get(currentWeapIndex).weapName.toLowerCase()+".png"));
        } catch (IOException e) {
        }
    }
    
    public FleshPlayerEntity(int size, double speed, FleshPoint location, Color color) {
        super(size, speed, 100.0, location, color);
        this.weapons.add(new FleshPistolResource(-1));
    }

    @Override
    public void tickerUp(Graphics2D g2d) {
        g2d.setColor(color);
        if (0 < health) {
            AffineTransform at = new AffineTransform();

            // 4. translate it to the center of the component
            at.translate(location.x(), location.y());

            // 3. do the actual rotation
            at.rotate(Math.toRadians(FleshMath.angleToMouse() + 90));
            // 1. translate the object so that you rotate it around the 
            //    center 
            at.translate(-image.getWidth(null) / 2, -image.getHeight(null) / 2);            
            
            g2d.drawImage(image, at, null);
        }
    }
}
