package main.entity;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class PolygonShapeEntity extends ShapeEntity {

    protected final int tileSize;
    protected Point2D center;
    protected double angle;
    protected Color color;
    private static final Color DEFAULT_COLOR = Color.WHITE;

    public PolygonShapeEntity(int startX, int startY, int tileSize) {
        center = new Point2D.Double(startX, startY);
        this.tileSize = tileSize;
        color = DEFAULT_COLOR;
        angle = 0;
    }

    protected void move(Point2D newCenter) {
        center = newCenter;
    }
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        Rectangle2D rectangle = new Rectangle2D.Double(center.getX() - (double) tileSize / 2, center.getY() - (double) tileSize / 2, tileSize, tileSize);
        AffineTransform transform = AffineTransform.getRotateInstance(angle, center.getX(), center.getY());
        g2.fill(transform.createTransformedShape(rectangle));
    }

    @Override
    public Shape getCollisionBounds() {
        Rectangle2D rectangle = new Rectangle2D.Double(center.getX() - (double) tileSize / 2, center.getY() - (double) tileSize / 2, tileSize, tileSize);
        AffineTransform transform = AffineTransform.getRotateInstance(angle, center.getX(), center.getY());
        return transform.createTransformedShape(rectangle);
    }

}
