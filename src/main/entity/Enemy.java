package main.entity;

import main.animations.Explosion;
import main.resources.ResourceLoader;
import main.window.GamePanel;

import java.awt.geom.Point2D;

public class Enemy extends CircleShapeEntity {

    private final GamePanel gamePanel;
    private double speed;
    private final static double DEFAULT_SPEED = 0.5;
    private static final int DEFAULT_RADIUS = 30;

    public Enemy(int startX, int startY, GamePanel gamePanel) {
        super(startX, startY, DEFAULT_RADIUS, 0, ResourceLoader.getEnemyImage());
        speed = DEFAULT_SPEED;
        this.gamePanel = gamePanel;
    }

    public Point2D getPosition() {
        return center;
    }

    public void moveTo(double angle) {
        double x = center.getX() + speed * Math.cos(angle);
        double y = center.getY() + speed * Math.sin(angle);
        super.move(new Point2D.Double(x, y));
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Projectile || other instanceof Player) {
            gamePanel.getDrawableGarbage().add(this);
            gamePanel.getPlayerUI().increaseScore(1);
            Explosion explosion = new Explosion(this.getPosition(), gamePanel);
            gamePanel.getSoundManager().playExplosionSound();
            gamePanel.getDrawables().add(explosion);
        }
    }
}
