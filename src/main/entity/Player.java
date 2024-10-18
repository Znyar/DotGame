package main.entity;

import main.animations.Explosion;
import main.resources.ResourceLoader;
import main.window.GamePanel;

import java.awt.geom.Point2D;
import java.util.Optional;

public class Player extends PolygonShapeEntity {

    private final GamePanel gamePanel;

    private double speed;
    private double rotationSpeed;
    private int projectileSize;
    private int damage;
    private double projectileSpeed;
    private int ammoCount;
    private int projectileCount;
    private int shootingCooldown;
    private int rearmingCooldown;
    private static final int DEFAULT_SHOOTING_COOLDOWN = 1000;
    private static final int DEFAULT_AMMO_COUNT = 10;
    private static final int DEFAULT_REARMING_COOLDOWN = DEFAULT_SHOOTING_COOLDOWN * DEFAULT_AMMO_COUNT;
    private final static double DEFAULT_PROJECTILE_SPEED = 10;
    private final static int DEFAULT_PROJECTILE_SIZE = 20;
    private final static int DEFAULT_DAMAGE = 1;
    private final static double DEFAULT_SPEED = 1;
    private static final int DEFAULT_TILE_SIZE = 48;
    private final static double MAX_ROTATION_SPEED = 0.05;
    private final static double DEFAULT_BACKWARD_MOVEMENT = 70;
    private final static double DEFAULT_BACKWARD_SPEED = DEFAULT_SPEED * 3;
    private final static double BACKWARD_MOVEMENT_SLOW_DOWN_FACTOR = 0.1;

    private double backwardMovement;
    private double backwardSpeed;

    public Player(GamePanel gamePanel) {
        super(gamePanel.getWidth() / 2, gamePanel.getHeight() / 2, DEFAULT_TILE_SIZE, ResourceLoader.getPlayerImage());
        speed = DEFAULT_SPEED;
        this.gamePanel = gamePanel;
        projectileSpeed = DEFAULT_PROJECTILE_SPEED;
        projectileSize = DEFAULT_PROJECTILE_SIZE;
        ammoCount = DEFAULT_AMMO_COUNT;
        projectileCount = 10;
        shootingCooldown = DEFAULT_SHOOTING_COOLDOWN;
        rearmingCooldown = DEFAULT_REARMING_COOLDOWN;
        damage = DEFAULT_DAMAGE;
        lastShootTime = System.currentTimeMillis();
        lastRearmTime = System.currentTimeMillis();
        rotationSpeed = MAX_ROTATION_SPEED;
        backwardMovement = 0;
        backwardSpeed = DEFAULT_BACKWARD_SPEED;
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

    public void update() {
        if (backwardMovement > 0) {
            moveBack();
            backwardMovement -= backwardSpeed;

            if (backwardMovement < 0) {
                backwardMovement = 0;
            } else if (backwardMovement < DEFAULT_BACKWARD_MOVEMENT * 0.4) {
                backwardSpeed *= (1 - BACKWARD_MOVEMENT_SLOW_DOWN_FACTOR);
            }
        }
    }

    private void moveBack() {
        double currentX = center.getX();
        double currentY = center.getY();

        double newX = currentX - backwardSpeed * Math.cos(angle);
        double newY = currentY - backwardSpeed * Math.sin(angle);

        super.move(new Point2D.Double(newX, newY));
    }

    private long lastShootTime;

    public Optional<Projectile> fire() {
        if (projectileCount <= 0)
        {
            gamePanel.getSoundManager().playNoAmmoSound(shootingCooldown);
            return Optional.empty();
        }

        if (System.currentTimeMillis() - lastShootTime < shootingCooldown) {
            return Optional.empty();
        }

        lastShootTime = System.currentTimeMillis();
        projectileCount--;
        Projectile projectile = new Projectile((int) center.getX(),
                (int) center.getY(),
                projectileSize,
                damage,
                angle,
                projectileSpeed,
                gamePanel.getDrawableGarbage());
        projectile.setAngle(angle);
        backwardSpeed = DEFAULT_BACKWARD_SPEED;
        backwardMovement = DEFAULT_BACKWARD_MOVEMENT;

        gamePanel.getSoundManager().playShotSound();

        return Optional.of(projectile);
    }

    private long lastRearmTime;

    public void rearm() {
        if (System.currentTimeMillis() - lastRearmTime > rearmingCooldown) {
            projectileCount = ammoCount;
            lastRearmTime = System.currentTimeMillis();
            gamePanel.getSoundManager().playRearmingSound();
        }
    }

    public int getAmmoCount() {
        return ammoCount;
    }

    public int getProjectileCount() {
        return projectileCount;
    }

    public Point2D getPosition() {
        return center;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy) {
            Explosion explosion = new Explosion(this.getPosition(), gamePanel);
            gamePanel.getSoundManager().playExplosionSound();
            gamePanel.getDrawables().add(explosion);
            gamePanel.getDrawableGarbage().add(this);
            gamePanel.scheduleRestartGame(2000);
        }
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

}
