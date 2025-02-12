package main.game;

import main.entity.Collidable;
import main.window.GamePanel;

public class CollisionHandler {

    private final GamePanel gamePanel;

    public CollisionHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void handleCollisions() {
        Collidable[] collidables = gamePanel.getDrawables().stream()
                .filter(drawable -> drawable instanceof Collidable)
                .map(drawable -> (Collidable) drawable)
                .filter(collidable -> collidable.getCollisionBounds().intersects(gamePanel.getCamera().getBounds()))
                .toArray(Collidable[]::new);

        for (int i = 0; i < collidables.length; i++) {
            Collidable current = collidables[i];
            for (int j = i + 1; j < collidables.length; j++) {
                Collidable other = collidables[j];
                if (current.isColliding(other)) {
                    current.onCollision(other);
                    other.onCollision(current);
                }
            }
        }
    }

}
