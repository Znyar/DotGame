package main.game;

import main.entity.Drawable;
import main.entity.Projectile;
import main.window.Camera;
import main.window.GamePanel;

import java.util.ArrayList;
import java.util.Collection;

public class ProjectileHandler {

    private final GamePanel gamePanel;

    public ProjectileHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void handle() {
        Collection<Drawable> projectilesToRemove = new ArrayList<>();
        gamePanel.getDrawables().forEach(drawable -> {
            if (drawable instanceof Projectile projectile) {
                Camera camera = gamePanel.getCamera();
                double cameraX = camera.getXOffset();
                double cameraY = camera.getYOffset();
                double projectileX = projectile.getPosition().getX();
                double projectileY = projectile.getPosition().getY();
                if (projectileX < cameraX || projectileX > cameraX + camera.getScreenWidth()
                        || projectileY < cameraY || projectileY > cameraY + camera.getScreenHeight()) {
                    projectilesToRemove.add(projectile);
                } else {
                    projectile.move();
                }
            }
        });
        gamePanel.getDrawableGarbage().addAll(projectilesToRemove);
    }

}
