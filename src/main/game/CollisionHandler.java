package main.game;

import main.entity.*;

import java.util.List;

public class CollisionHandler {

    public void handleCollisions(List<Drawable> drawables) {
        List<Collidable> collidables = drawables.stream()
                .filter(drawable -> drawable instanceof Collidable)
                .map(drawable -> (Collidable) drawable)
                .toList();

        for (int i = 0; i < collidables.size(); i++) {
            Collidable current = collidables.get(i);
            for (int j = i + 1; j < collidables.size(); j++) {
                Collidable other = collidables.get(j);
                if (current.isColliding(other)) {
                    current.onCollision(other);
                    other.onCollision(current);

                    if ((current instanceof Projectile && other instanceof Enemy)
                            || (current instanceof Enemy && other instanceof Projectile)) {
                        drawables.remove(other);
                        drawables.remove(current);
                    }
                }
            }
        }
    }

}
