package main.window;

import main.entity.Drawable;
import main.entity.Player;
import main.game.PlayerManager;
import main.resources.ResourceLoader;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;

public class PlayerUI implements Drawable {

    private final int panelWidth;
    private final int panelHeight;

    private final GamePanel gamePanel;

    private final BufferedImage rButtonIcon = ResourceLoader.getRButtonImage();
    private final Font font = ResourceLoader.getUIFont();

    public PlayerUI(int panelWidth, int panelHeight, GamePanel gamePanel) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g) {
        Duration elapsedTime = Duration.between(gamePanel.getPlayerManager().getStartTime(), Instant.now());
        long minutes = elapsedTime.toMinutes();
        long seconds = elapsedTime.getSeconds() % 60;
        String time = String.format("%02d:%02d", minutes, seconds);

        Rectangle2D cameraRect = gamePanel.getPlayer().getCollisionBounds().getBounds2D();
        double windowHeight = gamePanel.getCamera().getScreenHeight();
        double windowWidth = gamePanel.getCamera().getScreenWidth();
        double cameraX = cameraRect.getCenterX();
        double cameraY = cameraRect.getCenterY();
        Rectangle rect = new Rectangle((int) (cameraX + windowWidth / 2 * 0.8 - panelWidth),
                (int) (cameraY + windowHeight / 2 * 0.9 - panelHeight),
                panelWidth,
                panelHeight);
        drawText(g, rect, time);
    }

    private void drawText(Graphics2D g, Rectangle rect, String time) {
        g.setColor(Color.WHITE);

        Font textFont = font.deriveFont(24f);
        g.setFont(textFont);

        FontMetrics metrics = g.getFontMetrics(textFont);
        int textHeight = metrics.getHeight();

        int centerY = rect.y + rect.height / 2;

        int scoreY = centerY + textHeight / 4;
        int ammoY = scoreY - textHeight;
        int timeY = scoreY + textHeight;

        Player player = gamePanel.getPlayer();

        if (player.getProjectileCount() == 0) {
            g.setColor(Color.RED);
            int rButtonY = centerY - rButtonIcon.getHeight() / 2;
            g.drawImage(rButtonIcon,
                    (int) (rect.x + rect.width * 0.1),
                    rButtonY,
                    null);
        }

        g.drawString("Ammo : " + player.getProjectileCount() + "/" + player.getAmmoCount(),
                (int) (rect.x + rect.width * 0.3),
                ammoY);

        g.setColor(Color.WHITE);

        PlayerManager playerManager = gamePanel.getPlayerManager();
        g.drawString("Score : " + playerManager.getScore() + " Best: " + playerManager.getBestScore(), (int) (rect.x + rect.width * 0.3), scoreY);
        g.drawString("Time : " + time, (int) (rect.x + rect.width * 0.3), timeY);

        textFont = font.deriveFont(80f);
        metrics = g.getFontMetrics(textFont);
        textHeight = metrics.getHeight();
        int levelY = centerY + textHeight / 4;
        g.setFont(textFont);
        g.drawString(String.valueOf(playerManager.getLevel()), rect.x + rect.width, levelY);
    }

    @Override
    public Point2D getPosition() {
        return null;
    }

}
