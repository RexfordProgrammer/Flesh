package flesh.resources;

import flesh.FleshPoint;
import flesh.entities.FleshGrenadeEntity;
import java.awt.Color;

public class FleshGrenadeResource extends FleshWeaponResource{
    
    public FleshGrenadeResource(int ammo){
        super(1000, 1, new FleshGrenadeEntity(5, 3, 0.0, 500, 250, 200.0, new FleshPoint(0, 0), Color.BLACK), "Grenade");
        this.ammo = ammo;
    }
}
