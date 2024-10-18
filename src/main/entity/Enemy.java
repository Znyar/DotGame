package main.entity;

import main.animations.Explosion;
import main.resources.ResourceLoader;
import main.window.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Enemy extends CircleShapeEntity {

    private final GamePanel gamePanel;
    private double speed;
    private int hp;
    private int maxHp;
    private final static double DEFAULT_SPEED = 0.5;
    private static final int DEFAULT_RADIUS = 30;
    private static final int DEFAULT_HP = 3;

    public Enemy(int startX, int startY, GamePanel gamePanel) {
        super(startX, startY, DEFAULT_RADIUS, 0, ResourceLoader.getEnemyImage());
        speed = DEFAULT_SPEED;
        this.gamePanel = gamePanel;
        hp = DEFAULT_HP;
        maxHp = DEFAULT_HP;
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
        if (other instanceof Projectile projectile) {
            gamePanel.getSoundManager().playImpactSound();
            hp = hp - projectile.getDamage();
            if (hp <= 0) {
                gamePanel.getDrawableGarbage().add(this);
                gamePanel.getPlayerUI().increaseScore(1);
                Explosion explosion = new Explosion(this.getPosition(), gamePanel);
                gamePanel.getSoundManager().playExplosionSound();
                gamePanel.getDrawables().add(explosion);
            }
        }
        if (other instanceof Player) {
            gamePanel.getDrawableGarbage().add(this);
            Explosion explosion = new Explosion(this.getPosition(), gamePanel);
            gamePanel.getSoundManager().playExplosionSound();
            gamePanel.getDrawables().add(explosion);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if (hp < maxHp) {
            g2.setColor(Color.BLACK);
            Point2D center = this.center;
            Rectangle2D rect = new Rectangle2D.Double(center.getX() - radius, center.getY() + radius / 1.5, radius * 2, radius / 5);
            g2.fill(rect);
            g2.setColor(Color.RED);
            rect = new Rectangle2D.Double(center.getX() - radius, center.getY() + radius / 1.5, (double) hp / maxHp * radius * 2, radius / 5);
            g2.fill(rect);
        }
    }

}
