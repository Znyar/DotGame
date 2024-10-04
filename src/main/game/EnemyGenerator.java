package main.game;

import main.entity.Enemy;
import main.window.Camera;
import main.window.GamePanel;

import java.util.Random;

public class EnemyGenerator {

    private static final int SPAWN_INTERVAL = 1000;
    private static final int SPAWN_MARGIN = 100;
    private final Random random = new Random();
    private final GamePanel gamePanel;
    private long lastSpawnTime;

    public EnemyGenerator(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        lastSpawnTime = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSpawnTime >= SPAWN_INTERVAL) {
            spawnEnemy();
            lastSpawnTime = currentTime;
        }
    }

    private void spawnEnemy() {
        Camera camera = gamePanel.getCamera();
        int cameraWidth = camera.getScreenWidth();
        int cameraHeight = camera.getScreenHeight();

        double xOffset = camera.getXOffset();
        double yOffset = camera.getYOffset();

        int x, y;

        boolean isHorizontal = random.nextBoolean();

        if (isHorizontal) {
            boolean isLeft = random.nextBoolean();
            x = isLeft ? (int) (xOffset - SPAWN_MARGIN) : (int) (xOffset + cameraWidth + SPAWN_MARGIN);
            y = random.nextInt(cameraHeight + 2 * SPAWN_MARGIN) + (int) yOffset - SPAWN_MARGIN;
        } else {
            boolean isUpper = random.nextBoolean();
            y = isUpper ? (int) (yOffset - SPAWN_MARGIN) : (int) (yOffset + cameraHeight + SPAWN_MARGIN);
            x = random.nextInt(cameraWidth + 2 * SPAWN_MARGIN) + (int) xOffset - SPAWN_MARGIN;
        }

        Enemy enemy = new Enemy(x, y, gamePanel.getDrawableGarbage());
        gamePanel.getDrawables().add(enemy);
    }
}
