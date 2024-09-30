package main.entity;

import java.awt.geom.Point2D;

import static java.awt.Color.*;

public class Enemy extends PolygonShape {

    private int speed;
    private int maxSpeed;
    private int minSpeed;
    private final static int DEFAULT_SPEED = 1;
    private final static int DEFAULT_MAX_SPEED = DEFAULT_SPEED * 2;
    private final static int DEFAULT_MIN_SPEED = DEFAULT_SPEED;

    public Enemy(int startX, int startY, int tileSize) {
        super(startX, startY, tileSize);
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
