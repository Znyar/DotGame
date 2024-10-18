package main.entity;

import main.game.DrawableGarbage;
import main.resources.ResourceLoader;

import java.awt.geom.Point2D;

public class Projectile extends PolygonShapeEntity {

    private final DrawableGarbage drawableGarbage;
    private final double angle;
    private double speed;
    private int damage;

    public Projectile(int x, int y, int tileSize, int damage, double angle, double speed, DrawableGarbage drawableGarbage) {
        super(x, y, tileSize, ResourceLoader.getProjectileImage());
        this.drawableGarbage = drawableGarbage;
        this.angle = angle;
        this.speed = speed;
        this.damage = damage;
    }

    public void move() {
        double newX = center.getX() + speed * Math.cos(angle);
        double newY = center.getY() + speed * Math.sin(angle);
        super.move(new Point2D.Double(newX, newY));
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy) {
            drawableGarbage.add(this);
        }
    }

    @Override
    public Point2D getPosition() {
        return center;
    }

    public int getDamage() {
        return damage;
    }

}
