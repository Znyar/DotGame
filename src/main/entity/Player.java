package main.entity;

import main.resources.ResourceLoader;
import main.window.GamePanel;

import java.awt.geom.Point2D;
import java.util.Optional;

public class Player extends PolygonShapeEntity {

    private final GamePanel gamePanel;

    private int tileSize;
    private double speed;
    private double rotationSpeed;
    private double maxSpeed;
    private double minSpeed;
    private int projectileSize;
    private double projectileSpeed;
    private int ammoCount;
    private int projectileCount;
    private int shootingCooldown;
    private long lastShootTime;
    private static final int DEFAULT_SHOOTING_COOLDOWN = 1000;
    private static final int DEFAULT_AMMO_COUNT = 10;
    private final static double DEFAULT_PROJECTILE_SPEED = 10;
    private final static int DEFAULT_PROJECTILE_SIZE = 20;
    private final static double DEFAULT_SPEED = 1;
    private final static double DEFAULT_MAX_SPEED = DEFAULT_SPEED * 2;
    private final static double DEFAULT_MIN_SPEED = DEFAULT_SPEED;
    private static final int DEFAULT_TILE_SIZE = 48;
    private final static double MAX_ROTATION_SPEED = 0.05;

    public Player(GamePanel gamePanel) {
        super(gamePanel.getWidth() / 2, gamePanel.getHeight() / 2, DEFAULT_TILE_SIZE, ResourceLoader.getPlayerImage());
        tileSize = DEFAULT_TILE_SIZE;
        speed = DEFAULT_MIN_SPEED;
        maxSpeed = DEFAULT_MAX_SPEED;
        minSpeed = DEFAULT_MIN_SPEED;
        this.gamePanel = gamePanel;
        projectileSpeed = DEFAULT_PROJECTILE_SPEED;
        projectileSize = DEFAULT_PROJECTILE_SIZE;
        ammoCount = DEFAULT_AMMO_COUNT;
        projectileCount = 10;
        shootingCooldown = DEFAULT_SHOOTING_COOLDOWN;
        lastShootTime = System.currentTimeMillis();
        rotationSpeed = MAX_ROTATION_SPEED;
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

    public Optional<Projectile> fire() {
        if (projectileCount > 0 && System.currentTimeMillis() - lastShootTime > shootingCooldown)
        {
            lastShootTime = System.currentTimeMillis();
            projectileCount--;
            Projectile projectile = new Projectile((int) center.getX(),
                (int) center.getY(),
                projectileSize,
                angle,
                projectileSpeed,
                gamePanel.getDrawableGarbage());
            projectile.setAngle(angle);
            return Optional.of(projectile);
        }
        return Optional.empty();
    }

    public void rearm() {
        projectileCount = ammoCount;
    }

    public int getAmmoCount() {
        return ammoCount;
    }

    public int getProjectileCount() {
        return projectileCount;
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

    public double getRotationSpeed() {
        return rotationSpeed;
    }
}
