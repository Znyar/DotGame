package main.entity;

import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class ShapeEntity implements Drawable, Collidable {

    protected Point2D center;
    protected BufferedImage image;

    @Override
    public boolean isColliding(Collidable other) {
        Area areaA = new Area(getCollisionBounds());
        Area areaB = new Area(other.getCollisionBounds());

        areaA.intersect(areaB);
        return !areaA.isEmpty();
    }
}
