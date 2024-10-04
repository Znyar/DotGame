package main.entity;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class ShapeEntity implements Drawable, Collidable {

    protected Point2D center;
    protected Color color;
}
