package main;

import main.window.GamePanel;

import javax.swing.*;
import java.awt.*;

public class App {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Game");

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);

        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(window);
        } else {
            window.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            window.setVisible(true);
        }

        gamePanel.startGameThread();
    }

}