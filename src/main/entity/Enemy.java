package main.entity;

import java.awt.geom.Point2D;

import static java.awt.Color.*;

public class Enemy extends PolygonShape {

    private int tileSize;
    private double speed;
    private double maxSpeed;
    private double minSpeed;
    private final static double DEFAULT_SPEED = 0.5;
    private final static double DEFAULT_MAX_SPEED = DEFAULT_SPEED * 2;
    private final static double DEFAULT_MIN_SPEED = DEFAULT_SPEED;
    private static final int DEFAULT_TILE_SIZE = 48;

    public Enemy(int startX, int startY) {
        super(startX, startY, DEFAULT_TILE_SIZE);
        tileSize = DEFAULT_TILE_SIZE;
        color = RED;
        speed = DEFAULT_MIN_SPEED;
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

    }

}
