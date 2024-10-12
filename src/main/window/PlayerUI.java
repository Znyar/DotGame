package main.window;

import main.entity.Drawable;
import main.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class PlayerUI implements Drawable {

    private final int panelWidth;
    private final int panelHeight;

    private final GamePanel gamePanel;

    private final Font font;
    private int score;
    private final Instant startTime;

    private final BufferedImage rButtonIcon;

    public PlayerUI(int panelWidth, int panelHeight, GamePanel gamePanel) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.gamePanel = gamePanel;
        this.score = 0;
        this.startTime = Instant.now();
        try {
            rButtonIcon = ImageIO.read(new File("resources/r-button.png"));
            font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/space age.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void increaseScore(int amount) {
        score += amount;
    }

    @Override
    public void draw(Graphics2D g) {
        Duration elapsedTime = Duration.between(startTime, Instant.now());
        long minutes = elapsedTime.toMinutes();
        long seconds = elapsedTime.getSeconds() % 60;
        String time = String.format("%02d:%02d", minutes, seconds);

        Rectangle2D cameraRect = gamePanel.getPlayer().getCollisionBounds().getBounds2D();
        double windowHeight = gamePanel.getCamera().getScreenHeight();
        double windowWidth = gamePanel.getCamera().getScreenWidth();
        double cameraX = cameraRect.getCenterX();
        double cameraY = cameraRect.getCenterY();
        Rectangle rect = new Rectangle((int) (cameraX + windowWidth / 2 * 0.9 - panelWidth),
                (int) (cameraY + windowHeight / 2 * 0.9 - panelHeight),
                panelWidth,
                panelHeight);
        drawText(g, rect, time);
    }

    private void drawText(Graphics2D g, Rectangle rect, String time) {
        g.setColor(Color.WHITE);
        g.setFont(font.deriveFont(24f));

        FontMetrics metrics = g.getFontMetrics(g.getFont());
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
                    (int) (rect.x + rect.width * 0.3),
                    rButtonY,
                    null);
        }

        g.drawString("Ammo : " + player.getProjectileCount() + "/" + player.getAmmoCount(),
                (int) (rect.x + rect.width * 0.5),
                ammoY);

        g.setColor(Color.WHITE);
        g.drawString("Score : " + score, (int) (rect.x + rect.width * 0.5), scoreY);
        g.drawString("Time : " + time, (int) (rect.x + rect.width * 0.5), timeY);
    }

    @Override
    public Point2D getPosition() {
        return null;
    }
}
