package main.entity;

import main.window.GamePanel;

import java.awt.geom.Point2D;
import java.util.Optional;

public class Player extends PolygonShapeEntity {

    private final GamePanel gamePanel;

    private int tileSize;
    private double speed;
    private double maxSpeed;
    private double minSpeed;
    private double projectTileRadius;
    private double projectTileSpeed;
    private int ammoCount;
    private int projectileCount;
    private int shootingCooldown;
    private long lastShootTime;
    private static final int DEFAULT_SHOOTING_COOLDOWN = 1000;
    private static final int DEFAULT_AMMO_COUNT = 10;
    private final static double DEFAULT_PROJECTILE_SPEED = 10;
    private final static double DEFAULT_PROJECTILE_RADIUS = 3;
    private final static double DEFAULT_SPEED = 3;
    private final static double DEFAULT_MAX_SPEED = DEFAULT_SPEED * 2;
    private final static double DEFAULT_MIN_SPEED = DEFAULT_SPEED;
    private static final int DEFAULT_TILE_SIZE = 48;

    public Player(GamePanel gamePanel) {
        super(gamePanel.getWidth() / 2, gamePanel.getHeight() / 2, DEFAULT_TILE_SIZE);
        tileSize = DEFAULT_TILE_SIZE;
        speed = DEFAULT_MIN_SPEED;
        maxSpeed = DEFAULT_MAX_SPEED;
        minSpeed = DEFAULT_MIN_SPEED;
        this.gamePanel = gamePanel;
        projectTileSpeed = DEFAULT_PROJECTILE_SPEED;
        projectTileRadius = DEFAULT_PROJECTILE_RADIUS;
        ammoCount = DEFAULT_AMMO_COUNT;
        projectileCount = 10;
        shootingCooldown = DEFAULT_SHOOTING_COOLDOWN;
        lastShootTime = System.currentTimeMillis();
    }

    public void moveUp() {
        super.move(new Point2D.Double(center.getX(), center.getY() - speed));
    }
    public void moveDown() {
        super.move(new Point2D.Double(center.getX(), center.getY() + speed));
    }
    public void moveLeft() {
        super.move(new Point2D.Double(center.getX() - speed, center.getY()));
    }
    public void moveRight() {
        super.move(new Point2D.Double(center.getX() + speed, center.getY()));
    }

    public Optional<Projectile> fire(double angle) {
        if (projectileCount > 0 && System.currentTimeMillis() - lastShootTime > shootingCooldown)
        {
            lastShootTime = System.currentTimeMillis();
            projectileCount--;
            return Optional.of(new Projectile((int) center.getX(),
                    (int) center.getY(),
                    projectTileRadius,
                    angle,
                    projectTileSpeed,
                    gamePanel.getDrawableGarbage()));
        }
        return Optional.empty();
    }

    public void rearm() {
        projectileCount = ammoCount;
    }

    public void speedUp() {
        if (speed < maxSpeed)
            speed += 0.1;
    }

    public Point2D getPosition() {
        return center;
    }

    public void slowDown() {
        if (speed > minSpeed)
            speed -= 0.1;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy) {
            gamePanel.restartGameThread();
        }
    }

}
