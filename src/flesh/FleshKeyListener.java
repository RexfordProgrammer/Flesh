package flesh;

import static flesh.Flesh.j1;
import static flesh.Flesh.player;
import flesh.entities.FleshPlayerEntity;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CopyOnWriteArrayList;

public class FleshKeyListener implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        switch (KeyEvent.getKeyText(e.getKeyCode()).toLowerCase()) {
            case "w":
                Flesh.player.dirUp = true;
                break;
            case "s":
                Flesh.player.dirDown = true;
                break;
            case "a":
                Flesh.player.dirLeft = true;
                break;
            case "d":
                Flesh.player.dirRight = true;
                break;
            case "space":
                Flesh.player.isFiring = true;
                break;
            case "shift":
                Flesh.player.speed = 6;
                break;
            case "q":
                cycleWeapons();
                break;
            case "r":
                Flesh.player = new FleshPlayerEntity(30, 3, 100,
                new FleshPoint(j1.getWidth() / 2, j1.getHeight() / 2), Color.MAGENTA);
                Flesh.NPCEntities = new CopyOnWriteArrayList<>();
                Flesh.resources = new CopyOnWriteArrayList<>();
                Flesh.numWave = 1;
                Flesh.isGamePaused = true;
                Flesh.player.health = 100;
                Flesh.score = 0;
                break;
            case "p":
                pauseAction();
                break;
            case "z":
                Flesh.numWave++;
                break;
            case "l":
                //Flesh.slowMo = !Flesh.slowMo;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (KeyEvent.getKeyText(e.getKeyCode()).toLowerCase()) {
            case "w":
                Flesh.player.dirUp = false;
                break;
            case "s":
                Flesh.player.dirDown = false;
                break;
            case "a":
                Flesh.player.dirLeft = false;
                break;
            case "d":
                Flesh.player.dirRight = false;
                break;
            case "space":
                Flesh.player.isFiring = false;
                break;
            case "shift":
                Flesh.player.speed = 3;
                break;
        }
    }

    //Changes weapon when q is pressed
    private void cycleWeapons() {
        if (player.currentWeapIndex < player.weapons.size() - 1) {
            player.currentWeapIndex++;
        } else {
            player.currentWeapIndex = 0;
        }
            player.loadImage();
            //System.out.println(player.weapons.get(player.currentWeapIndex).weapName.toLowerCase());
    }

    //pauses game
    private void pauseAction() {
        Flesh.isGamePaused = !Flesh.isGamePaused;
    }

    //not needed
    @Override
    public void keyTyped(KeyEvent e) {
    }
}
