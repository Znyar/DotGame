package main.game;

import main.entity.Drawable;
import main.entity.Enemy;
import main.entity.Player;

import java.util.List;

public class EnemyControlHandler {

    public void handle(List<Drawable> drawables, Player player) {
        List<Enemy> enemies = drawables.stream()
                .filter(d -> d instanceof Enemy)
                .map(d -> (Enemy) d)
                .toList();
        for (Enemy enemy : enemies) {
            double x = player.getPosition().getX() - enemy.getPosition().getX();
            double y = player.getPosition().getY() - enemy.getPosition().getY();
            double angle = Math.atan2(y, x);
            enemy.moveTo(angle);
        }
    }

}
