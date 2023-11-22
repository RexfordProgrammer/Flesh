package flesh.resources;

import flesh.FleshPoint;
import flesh.entities.FleshBulletEntity;
import java.awt.Color;

public class FleshMachinegunResource extends FleshWeaponResource{
    public FleshMachinegunResource(int ammo){
        super(50, 1, new FleshBulletEntity(5, 20, 0, 0, 50, new FleshPoint(0, 0), Color.BLACK), "Machinegun");
        this.ammo = ammo;
    }
}
