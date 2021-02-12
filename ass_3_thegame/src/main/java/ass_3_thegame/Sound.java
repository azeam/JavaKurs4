package ass_3_thegame;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private Clip musicClip;
    private Clip clip;
    // in theory it should not be necessary to use two near identical methods for this, in practice it makes everything a lot easier...
    public void musicPlayer(String action, String file, boolean loop) {
        try {
            if (action.equals("stop")) {
                musicClip.stop();
                musicClip.close();
                musicClip = null;
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(file));
            musicClip = AudioSystem.getClip();
            musicClip.open(audioInputStream);
            if (loop) {
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            musicClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void soundPlayer(String string) {
        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(string));
            clip = null; // attempt at fixing LUE in certain occassions
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        } 
    }
}
