package main.game;

import main.entity.Drawable;
import main.entity.Enemy;
import main.window.GamePanel;

import java.util.List;
import java.util.Random;

public class EnemyGenerator {

    private static final int SPAWN_INTERVAL = 3000;
    private final Random random = new Random();
    private final GamePanel gamePanel;
    private final List<Drawable> drawables;
    private long lastSpawnTime;

    public EnemyGenerator(GamePanel gamePanel, List<Drawable> drawables) {
        this.gamePanel = gamePanel;
        this.drawables = drawables;
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
        int x = gamePanel.getWidth() * random.nextInt(-1, 2) + random.nextInt(-100, 200);
        int y = gamePanel.getHeight() * random.nextInt(-1, 2) + random.nextInt(-100, 200);

        System.out.println(x);
        System.out.println(y);

        Enemy enemy = new Enemy(x, y);
        drawables.add(enemy);
    }
}
