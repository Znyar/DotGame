package main.sound;

import javax.sound.sampled.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundManager {

    private static Clip backgroundMusicClip;
    private static byte[] shotSoundData;
    private static byte[] explosionSoundData;
    private static byte[] noAmmoSoundData;
    private static byte[] rearmingSoundData;
    private static byte[] impactSoundData;

    private static final ExecutorService soundExecutor = Executors.newCachedThreadPool();

    public static void loadSounds() {
        loadBackgroundMusic();
        loadShotSound();
        loadNoAmmoSound();
        loadRearmingSound();
        loadExplosionSound();
        loadImpactSound();
    }

    private static void loadBackgroundMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/audio/background.wav"));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioInputStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void loadShotSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/audio/shooting.wav"));
            shotSoundData = audioStreamToByteArray(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadExplosionSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/audio/explosion.wav"));
            explosionSoundData = audioStreamToByteArray(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadNoAmmoSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/audio/no-ammo.wav"));
            noAmmoSoundData = audioStreamToByteArray(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadRearmingSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/audio/rearming.wav"));
            rearmingSoundData = audioStreamToByteArray(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadImpactSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/audio/impact.wav"));
            impactSoundData = audioStreamToByteArray(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] audioStreamToByteArray(AudioInputStream audioInputStream) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    public void playBackgroundMusic() {
        if (backgroundMusicClip != null && !backgroundMusicClip.isRunning()) {
            backgroundMusicClip.start();
        }
    }

    public void playShotSound() {
        soundExecutor.submit(() -> playSound(shotSoundData));
    }

    public void playExplosionSound() {
        soundExecutor.submit(() -> playSound(explosionSoundData));
    }

    public void playImpactSound() {
        soundExecutor.submit(() -> playSound(impactSoundData));
    }

    private long lastNoAmmoSoundTime;

    public void playNoAmmoSound(int cooldown) {
        if (lastNoAmmoSoundTime < System.currentTimeMillis() - cooldown) {
            lastNoAmmoSoundTime = System.currentTimeMillis();
            soundExecutor.submit(() -> playSound(noAmmoSoundData));
        }
    }

    public void playRearmingSound() {
        soundExecutor.submit(() -> playSound(rearmingSoundData));
    }

    private void playSound(byte[] soundData) {
        if (soundData == null) return;

        try (AudioInputStream audioInputStream = new AudioInputStream(
                new ByteArrayInputStream(soundData),
                getAudioFormat(),
                soundData.length / getAudioFormat().getFrameSize())) {

            SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(getAudioFormat());
            sourceDataLine.open(getAudioFormat());
            sourceDataLine.start();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                sourceDataLine.write(buffer, 0, bytesRead);
            }

            sourceDataLine.drain();
            sourceDataLine.close();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private AudioFormat getAudioFormat() {
        return new AudioFormat(44100, 16, 2, true, false);
    }

}
