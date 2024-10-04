package main.entity;

import main.game.DrawableGarbage;

import java.awt.geom.Point2D;

import static java.awt.Color.*;

public class Projectile extends CircleShapeEntity {

    private final DrawableGarbage drawableGarbage;
    private final double angle;
    private double speed;

    public Projectile(int x, int y, double radius, double angle, double speed, DrawableGarbage drawableGarbage) {
        super(x, y, radius);
        this.drawableGarbage = drawableGarbage;
        this.angle = angle;
        this.speed = speed;
        super.color = YELLOW;
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

}
