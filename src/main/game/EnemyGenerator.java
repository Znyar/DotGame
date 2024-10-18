package main.game;

import main.entity.Enemy;
import main.window.Camera;
import main.window.GamePanel;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EnemyGenerator implements Runnable {

    private static final long DEFAULT_SPAWN_INTERVAL = 2000;
    private static final int SPAWN_MARGIN = 100;

    private long spawnInterval;
    private final Random random = new Random();
    private final GamePanel gamePanel;

    private final ScheduledExecutorService scheduler;

    public EnemyGenerator(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        spawnInterval = DEFAULT_SPAWN_INTERVAL;
        scheduler = Executors.newSingleThreadScheduledExecutor();
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

        synchronized (gamePanel.getDrawables()) {
            Enemy enemy = new Enemy(x, y, gamePanel);
            gamePanel.getDrawables().add(enemy);
        }
    }

    public void start(long initialDelay) {
        scheduler.scheduleAtFixedRate(this, initialDelay, spawnInterval, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }

    @Override
    public void run() {
        spawnEnemy();
    }

}
