package main.game;

import main.entity.Collidable;
import main.entity.Drawable;

import java.util.List;

public class CollisionHandler {

    public void handleCollisions(List<Drawable> drawables) {
        List<Collidable> collidables = drawables.stream()
                .filter(d -> d instanceof Collidable)
                .map(d -> (Collidable) d)
                .toList();
        for (int i = 0; i < collidables.size(); i++) {
            Collidable current = collidables.get(i);
            for (int j = i + 1; j < collidables.size(); j++) {
                Collidable other = collidables.get(j);
                if (current.isColliding(other)) {
                    current.onCollision(other);
                    other.onCollision(current);
                }
            }
        }
    }

}
