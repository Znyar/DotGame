package main.entity;

import java.awt.*;
import java.awt.geom.Point2D;

public interface Drawable {

    void draw(Graphics2D g);

    Point2D getPosition();

}
