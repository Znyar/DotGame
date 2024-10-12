package main.entity;

import main.game.DrawableGarbage;

import java.awt.geom.Point2D;

public class Projectile extends PolygonShapeEntity {

    private final DrawableGarbage drawableGarbage;
    private final double angle;
    private double speed;

    public Projectile(int x, int y, int tileSize, double angle, double speed, DrawableGarbage drawableGarbage, String imagePath) {
        super(x, y, tileSize, imagePath);
        this.drawableGarbage = drawableGarbage;
        this.angle = angle;
        this.speed = speed;
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
