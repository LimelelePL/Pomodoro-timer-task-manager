package timer;


import javafx.scene.media.AudioClip;

import java.util.Objects;

public class SoundNotification {
    public static void playSound(String filePath) {
        AudioClip sound = new AudioClip(Objects.requireNonNull(SoundNotification.class.getResource(filePath)).toExternalForm());
        sound.play();
    }
}
