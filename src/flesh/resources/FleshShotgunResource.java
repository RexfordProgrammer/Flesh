package flesh.resources;

import flesh.FleshPoint;
import flesh.entities.FleshBulletEntity;
import java.awt.Color;

public class FleshShotgunResource extends FleshWeaponResource{
    
    public FleshShotgunResource(int ammo){
        super(400, 5, new FleshBulletEntity(4, 8, 300, 0, 50, new FleshPoint(0, 0), Color.BLACK), "Shotgun");
        this.ammo = ammo;
    }
}
