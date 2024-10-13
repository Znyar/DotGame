package main.animations;

import main.entity.Drawable;
import main.resources.ResourceLoader;
import main.window.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class Explosion implements Drawable {

    private final GamePanel gamePanel;
    private static final List<BufferedImage> frames = ResourceLoader.getExplosionFrames();
    private int currentFrame;
    private final Point2D position;
    private boolean finished;

    private long lastFrameTime;

    public Explosion(Point2D position, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.position = position;
        this.currentFrame = 0;
        this.finished = false;
        this.lastFrameTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Graphics2D g) {
        if (finished) {
            gamePanel.getDrawableGarbage().add(this);
            return;
        }

        long currentTime = System.currentTimeMillis();
        long frameDuration = 10;
        if (currentTime - lastFrameTime >= frameDuration) {
            currentFrame++;
            lastFrameTime = currentTime;

            if (currentFrame >= frames.size()) {
                finished = true;
                return;
            }
        }

        BufferedImage frame = frames.get(currentFrame);
        int imgWidth = frame.getWidth();
        int imgHeight = frame.getHeight();

        double worldX = position.getX();
        double worldY = position.getY();

        double scaleX = (double) 480 / imgWidth;
        double scaleY = (double) 270 / imgHeight;

        float alpha = 1.0f;
        int fadeStartFrame = (int) (frames.size() * 0.7);
        if (currentFrame >= fadeStartFrame) {
            int remainingFrames = frames.size() - currentFrame;
            alpha = (float) remainingFrames / (frames.size() - fadeStartFrame);
        }

        Composite originalComposite = g.getComposite();

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        AffineTransform transform = new AffineTransform();
        transform.translate(worldX - 240, worldY - 135);
        transform.scale(scaleX, scaleY);

        g.drawImage(frame, transform, null);

        g.setComposite(originalComposite);
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

}
