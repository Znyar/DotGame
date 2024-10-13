package main.entity;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class PolygonShapeEntity extends ShapeEntity {

    protected final int tileSize;
    protected Point2D center;
    protected double angle;

    public PolygonShapeEntity(int startX, int startY, int tileSize, BufferedImage image) {
        center = new Point2D.Double(startX, startY);
        this.tileSize = tileSize;
        angle = 0;
        this.image = image;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    protected void move(Point2D newCenter) {
        center = newCenter;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (image != null) {
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            double scaleX = (double) tileSize / imgWidth;
            double scaleY = (double) tileSize / imgHeight;

            AffineTransform transform = new AffineTransform();

            transform.translate(center.getX(), center.getY());

            transform.rotate(angle, 0, 0);

            transform.translate(-tileSize / 2.0, -tileSize / 2.0);

            transform.scale(scaleX, scaleY);

            g2.drawImage(image, transform, null);
        }
    }

    @Override
    public Shape getCollisionBounds() {
        Rectangle2D rectangle = new Rectangle2D.Double(center.getX() - (double) tileSize / 2, center.getY() - (double) tileSize / 2, tileSize, tileSize);
        AffineTransform transform = AffineTransform.getRotateInstance(angle, center.getX(), center.getY());
        return transform.createTransformedShape(rectangle);
    }
}
