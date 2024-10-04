package main.entity;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class PolygonShapeEntity extends ShapeEntity {
    private final List<Point2D> vertices;
    protected final int tileSize;
    protected Point2D center;

    protected Color color;
    private static final Color DEFAULT_COLOR = Color.WHITE;

    public PolygonShapeEntity(int startX, int startY, int tileSize) {
        this.vertices = initVertices(startX, startY, tileSize);
        center = initCenter();
        this.tileSize = tileSize;
        color = DEFAULT_COLOR;
    }

    private Point2D initCenter() {
        double x = 0, y = 0;
        for (Point2D vertex : vertices) {
            x += vertex.getX();
            y += vertex.getY();
        }
        return new Point2D.Double(x / vertices.size(), y / vertices.size());
    }

    protected void move(Point2D newCenter) {
        double deltaX = newCenter.getX() - center.getX();
        double deltaY = newCenter.getY() - center.getY();
        center = newCenter;
        vertices.forEach(vertex -> vertex.setLocation(vertex.getX() + deltaX, vertex.getY() + deltaY));
    }

    private static List<Point2D> initVertices(int startX, int startY, int tileSize) {
        List<Point2D> vertices = new ArrayList<>();
        vertices.add(new Point2D.Double(startX - (double) tileSize / 2, startY - (double) tileSize / 2));
        vertices.add(new Point2D.Double(startX + (double) tileSize / 2, startY - (double) tileSize / 2));
        vertices.add(new Point2D.Double(startX + (double) tileSize / 2, startY + (double) tileSize / 2));
        vertices.add(new Point2D.Double(startX - (double) tileSize / 2, startY + (double) tileSize / 2));
        return vertices;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        Polygon polygon = new Polygon();

        for (Point2D vertex : vertices) {
            polygon.addPoint((int) vertex.getX(), (int) vertex.getY());
        }

        g2.fill(polygon);
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
        Polygon bounds = new Polygon();
        for (Point2D vertex : vertices) {
            bounds.addPoint((int) vertex.getX(), (int) vertex.getY());
        }
        return bounds;
    }

}
