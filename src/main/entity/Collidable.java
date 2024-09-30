package main.entity;

import java.awt.*;

public interface Collidable {

    boolean isColliding(Collidable other);
    Shape getCollisionBounds();
    void onCollision(Collidable other);

}
