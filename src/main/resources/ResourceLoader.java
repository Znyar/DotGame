package main.resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourceLoader {

    private static final List<BufferedImage> explosionFrames = new ArrayList<>();
    private static BufferedImage enemyImage;
    private static BufferedImage projectileImage;
    private static BufferedImage playerImage;
    private static BufferedImage backgroundImage;
    private static BufferedImage rButtonImage;
    private static Font UIFont;

    public static void loadResources() {
        try {
            for (int i = 1; i <= 60; i++) {
                String framePath = String.format("resources/animation/explosion/explosion_frame_%04d.png", i);
                BufferedImage frame = ImageIO.read(new File(framePath));
                explosionFrames.add(frame);
            }
            enemyImage = ImageIO.read(new File("resources/enemy.png"));
            projectileImage = ImageIO.read(new File("resources/projectile.png"));
            playerImage = ImageIO.read(new File("resources/player.png"));
            backgroundImage = ImageIO.read(new File("resources/background.jpg"));
            rButtonImage = ImageIO.read(new File("resources/r-button.png"));
            UIFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/space-age.ttf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(UIFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public static List<BufferedImage> getExplosionFrames() {
        return explosionFrames;
    }

    public static BufferedImage getEnemyImage() {
        return enemyImage;
    }

    public static BufferedImage getProjectileImage() {
        return projectileImage;
    }

    public static BufferedImage getPlayerImage() {
        return playerImage;
    }

    public static BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public static BufferedImage getRButtonImage() {
        return rButtonImage;
    }

    public static Font getUIFont() {
        return UIFont;
    }

}
