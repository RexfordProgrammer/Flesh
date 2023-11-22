/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flesh.resources;

import flesh.FleshPoint;
import flesh.entities.FleshRailEntity;
import java.awt.Color;

/**
 *
 * @author School
 */
public class FleshRailgunResource extends FleshWeaponResource{
    
    public FleshRailgunResource(int ammo){
        super(750, 1, new FleshRailEntity(6, 22, 0, 0, 150, new FleshPoint(0, 0), Color.BLACK), "Railgun");
        this.ammo = ammo;
    }
}
