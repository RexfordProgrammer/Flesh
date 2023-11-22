package flesh;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class FleshSoundFXThread extends Thread {

    Media sound = new Media(getClass().getResource("/flesh/audio/pistol_fire.mp3").toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);

    @Override
    public void run() {
                mediaPlayer.play();
   
            if (!Flesh.player.isFiring) {
                mediaPlayer.stop();
                mediaPlayer.seek(Duration.ZERO);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException v) {
                System.out.println(v);
            }
        }
    }

