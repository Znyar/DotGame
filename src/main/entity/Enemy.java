package main.entity;

import main.game.DrawableGarbage;

import java.awt.geom.Point2D;

import static java.awt.Color.*;

public class Enemy extends CircleShapeEntity {

    private final DrawableGarbage drawableGarbage;
    private int radius;
    private double speed;
    private final static double DEFAULT_SPEED = 0.5;
    private static final int DEFAULT_RADIUS = 30;

    public Enemy(int startX, int startY, DrawableGarbage drawableGarbage) {
        super(startX, startY, DEFAULT_RADIUS);
        this.drawableGarbage = drawableGarbage;
        radius = DEFAULT_RADIUS;
        color = RED;
        speed = DEFAULT_SPEED;
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
        if (other instanceof Projectile) {
            drawableGarbage.add(this);
        }
    }

}
