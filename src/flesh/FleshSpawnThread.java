/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flesh;

import flesh.entities.FleshBeastEntity;
import flesh.entities.FleshZombieEntity;

public class FleshSpawnThread extends Thread {

    @Override
    public void run() {
        while (true) {
            if (Flesh.NPCEntities.isEmpty()) {
                nextWave();
                if (Flesh.numWave % 5 != 0) {
                    for (int i = 0; i < Math.pow(1.3, Flesh.numWave); i++) {
                        Flesh.NPCEntities.add(FleshZombieEntity.randomZombie(false));
                        waveBasedSleep();
                    }
                } else {
                    waveBasedSleep();
                    Flesh.NPCEntities.add(FleshBeastEntity.summonBeast(true));
                }
                Flesh.numWave++;
            }
        }
    }

    private void nextWave() {
        Flesh.isLevelDisplayed = true;
        try {
            Thread.sleep(Flesh.timeRelease);
        } catch (InterruptedException v) {
            System.out.println(v);
        }
        Flesh.isLevelDisplayed = false;
    }

    private void waveBasedSleep() {
        try {
            while (Flesh.isGamePaused) {
                Thread.sleep(15);
            }

            if (Math.pow(2, Flesh.numWave) < 1500) {
                Thread.sleep(2000 - (int) Math.pow(2, Flesh.numWave));
            } else {
                Thread.sleep(250);
            }
        } catch (InterruptedException v) {
            System.out.println(v);
        }
    }
}
