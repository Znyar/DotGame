package main.window;

import main.entity.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PlayerUI implements Drawable {

    private final int panelWidth;
    private final int panelHeight;

    private final GamePanel gamePanel;

    public PlayerUI(int panelWidth, int panelHeight, GamePanel gamePanel) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g) {
        Rectangle2D bounds = gamePanel.getPlayer().getCollisionBounds().getBounds2D();
        double windowHeight = gamePanel.getCamera().getScreenHeight();
        double windowWidth = gamePanel.getCamera().getScreenWidth();
        double cameraX = bounds.getCenterX();
        double cameraY = bounds.getCenterY();
        Rectangle rect = new Rectangle((int) (cameraX + windowWidth / 2 * 0.9 - panelWidth), (int) (cameraY + windowHeight / 2 * 0.9 - panelHeight), panelWidth, panelHeight);
        g.setColor(Color.WHITE);
        g.fill(rect);
    }

    @Override
    public Point2D getPosition() {
        return null;
    }
}
