package main;

import main.resources.ResourceLoader;
import main.resources.sound.SoundManager;
import main.window.GamePanel;

import javax.swing.*;
import java.awt.*;

public class App {

    public static void main(String[] args) {
        ResourceLoader.loadResources();
        SoundManager.loadSounds();
        SoundManager soundManager = new SoundManager();

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Game");

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(window);
        } else {
            window.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        }

        GamePanel gamePanel = new GamePanel(soundManager);
        gamePanel.setSize(window.getWidth(), window.getHeight());

        window.add(gamePanel);

        window.setVisible(true);

        soundManager.playBackgroundMusic();

        gamePanel.startGameThread();
    }

}