package main.game;

import main.entity.Enemy;
import main.entity.Player;
import main.window.GamePanel;

import java.util.List;

public class EnemyControlHandler {

    private final GamePanel gamePanel;

    public EnemyControlHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void handle() {
        List<Enemy> enemies = gamePanel.getDrawables().stream()
                .filter(d -> d instanceof Enemy)
                .map(d -> (Enemy) d)
                .toList();
        for (Enemy enemy : enemies) {
            Player player = gamePanel.getPlayer();
            double x = player.getPosition().getX() - enemy.getPosition().getX();
            double y = player.getPosition().getY() - enemy.getPosition().getY();
            double angle = Math.atan2(y, x);
            enemy.setAngle(angle);
            enemy.moveTo(angle);
        }
    }

}
