package main.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public abstract class CircleShapeEntity extends ShapeEntity {

    protected final double radius;
    private double angle;

    public CircleShapeEntity(int x, int y, double radius, double angle, String imagePath) {
        center = new Point(x, y);
        this.radius = radius;
        this.angle = angle;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void move(Point2D newCenter) {
        center = newCenter;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (image != null) {
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            double scaleX = radius * 2 / imgWidth;
            double scaleY = radius * 2 / imgHeight;

            AffineTransform transform = new AffineTransform();

            transform.translate(center.getX(), center.getY());

            transform.rotate(angle, 0, 0);

            transform.translate(-radius, -radius);

            transform.scale(scaleX, scaleY);

            g2.drawImage(image, transform, null);
        }
    }

    @Override
    public Shape getCollisionBounds() {
        return new Ellipse2D.Double(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
    }

}
