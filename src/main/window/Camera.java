package main.window;

import main.entity.Player;

import java.awt.geom.Rectangle2D;

public class Camera {
    private double xOffset;
    private double yOffset;

    private final int screenWidth;
    private final int screenHeight;

    private static final double SMOOTHING_FACTOR = 0.05;

    public Camera(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.xOffset = 0;
        this.yOffset = 0;
    }

    public void update(Player player) {
        double targetXOffset = player.getPosition().getX() - screenWidth / 2.0;
        double targetYOffset = player.getPosition().getY() - screenHeight / 2.0;

        xOffset += (targetXOffset - xOffset) * SMOOTHING_FACTOR;
        yOffset += (targetYOffset - yOffset) * SMOOTHING_FACTOR;
    }

    public double getXOffset() {
        return xOffset;
    }

    public double getYOffset() {
        return yOffset;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(xOffset, yOffset, screenWidth, screenHeight);
    }

}
