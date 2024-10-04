package main.game;

import main.entity.*;
import main.window.GamePanel;

import java.util.List;

public class CollisionHandler {

    private final GamePanel gamePanel;

    public CollisionHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void handleCollisions() {
        List<Drawable> drawables = gamePanel.getDrawables();

        List<Collidable> collidables = drawables.stream()
                .filter(drawable -> drawable instanceof Collidable)
                .map(drawable -> (Collidable) drawable)
                .filter(collidable -> collidable.getCollisionBounds().intersects(gamePanel.getCamera().getBounds()))
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
