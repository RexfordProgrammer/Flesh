package flesh.resources;

import flesh.Flesh;
import flesh.FleshMath;
import flesh.FleshPoint;
import flesh.entities.FleshBulletEntity;
import flesh.entities.FleshGrenadeEntity;
import flesh.entities.FleshProjectileEntity;
import flesh.entities.FleshRailEntity;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class FleshWeaponResource {

    ArrayList<FleshProjectileEntity> projectiles = new ArrayList<>();
    public double fireDelay;
    public int ammo;
    public int numProjectiles;
    public String weapName;
    public FleshProjectileEntity baseProjectile;

    public FleshWeaponResource(double fireDelay, int numProjectiles, FleshProjectileEntity projectileType, String weapName) {
        this.fireDelay = fireDelay;
        this.numProjectiles = numProjectiles;
        this.baseProjectile = projectileType;
        this.weapName = weapName;
    }

    public void fire() {
        if (ammo < 0 || ammo > 0) {
            if (baseProjectile instanceof FleshBulletEntity) {
                fireBullet();
            } else if (baseProjectile instanceof FleshRailEntity) {
                fireRail();
            } else if (baseProjectile instanceof FleshGrenadeEntity) {
                fireGrenade();
            }
            ammo--;
        }
    }
    private void fireRail() {
        double cursorAngle = FleshMath.angleToMouse();
        double angle;

        for (int i = 1; i <= numProjectiles; i++) {
            if (numProjectiles == 1) {
                angle = cursorAngle;
            } else {
                angle = (cursorAngle - 15) + ((30 / numProjectiles) * i) - (15 / numProjectiles);
            }

            FleshProjectileEntity projectile = new FleshRailEntity(baseProjectile.size,
                    baseProjectile.speed, baseProjectile.range, angle,
                    baseProjectile.damage, FleshPoint.polymorph(Flesh.player.location), Color.BLACK);

            Flesh.projectileEntities.add(projectile);
        }
    }

    private void fireGrenade() {
        FleshGrenadeEntity g1 = (FleshGrenadeEntity) baseProjectile;
        double range = FleshMath.distanceBetweenPoints(FleshPoint.polymorph(Flesh.player.location),
                FleshPoint.polymorph(Flesh.fleshInstance.getMousePosition()));

        if (range > baseProjectile.range) {
            range = baseProjectile.range;
        }

        Flesh.projectileEntities.add(new FleshGrenadeEntity(baseProjectile.size,
                baseProjectile.speed, FleshMath.angleToMouse(), baseProjectile.damage,
                range, g1.explosionSize, FleshPoint.polymorph(Flesh.player.location), Color.BLACK));
    }

    private void fireBullet() {
        double cursorAngle = FleshMath.angleToMouse();
        double angle;

        for (int i = 1; i <= numProjectiles; i++) {
            if (numProjectiles == 1) {
                angle = cursorAngle;
            } else {
                angle = (cursorAngle - 15) + ((30 / numProjectiles) * i) - (15 / numProjectiles);
            }

            FleshProjectileEntity projectile = new FleshBulletEntity(baseProjectile.size,
                    baseProjectile.speed, baseProjectile.range, angle,
                    baseProjectile.damage, FleshPoint.polymorph(Flesh.player.location), Color.BLACK);

            Flesh.projectileEntities.add(projectile);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FleshWeaponResource) {
            FleshWeaponResource weap = (FleshWeaponResource) o;
            if (this.weapName.equalsIgnoreCase(weap.weapName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.weapName);
        return hash;
    }

    @Override
    public String toString() {
        return weapName;
    }
}
