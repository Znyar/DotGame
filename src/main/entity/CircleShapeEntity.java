package main.entity;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public abstract class CircleShapeEntity extends ShapeEntity {

    protected final double radius;

    private static final Color DEFAULT_COLOR = Color.WHITE;

    public CircleShapeEntity(int x, int y, double radius) {
        center = new Point(x, y);
        this.radius = radius;
        color = DEFAULT_COLOR;
    }

    protected void move(Point2D newCenter) {
        center = newCenter;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        Ellipse2D ellipse = new Ellipse2D.Double(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
        g2.fill(ellipse);
    }

    @Override
    public boolean isColliding(Collidable other) {
        Area areaA = new Area(getCollisionBounds());
        Area areaB = new Area(other.getCollisionBounds());

        areaA.intersect(areaB);
        return !areaA.isEmpty();
    }

    @Override
    public Shape getCollisionBounds() {
        return new Ellipse2D.Double(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
    }

}
