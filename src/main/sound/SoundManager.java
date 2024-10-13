package main.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private Clip backgroundMusicClip;
    private Clip shotSoundClip;
    private Clip explosionSoundClip;

    public SoundManager() {
        loadBackgroundMusic();
        loadShotSound();
        loadExplosionSound();
    }

    private void loadBackgroundMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/audio/background.wav"));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioInputStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void loadShotSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/audio/shooting.wav"));
            shotSoundClip = AudioSystem.getClip();
            shotSoundClip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void loadExplosionSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/audio/explosion.wav"));
            explosionSoundClip = AudioSystem.getClip();
            explosionSoundClip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playBackgroundMusic() {
        if (backgroundMusicClip != null && !backgroundMusicClip.isRunning()) {
            backgroundMusicClip.start();
        }
    }

    public void playShotSound() {
        if (shotSoundClip != null) {
            shotSoundClip.setFramePosition(0);
            shotSoundClip.start();
        }
    }

    public void playExplosionSound() {
        if (explosionSoundClip != null) {
            explosionSoundClip.setFramePosition(0);
            explosionSoundClip.start();
        }
    }

}
