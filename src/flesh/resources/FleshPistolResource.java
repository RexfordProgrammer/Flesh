/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flesh.resources;

import flesh.FleshPoint;
import flesh.entities.FleshBulletEntity;
import java.awt.Color;

/**
 *
 * @author School
 */
public class FleshPistolResource extends FleshWeaponResource{
    
    public FleshPistolResource(int ammo){
        super(250, 1, new FleshBulletEntity(4, 12, 0, 0, 50, new FleshPoint(0, 0), Color.BLACK), "Pistol");
        this.ammo = ammo;
    }
}
