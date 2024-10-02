package main.game;

import main.entity.Player;
import main.window.GamePanel;

public class PlayerControlHandler extends InputHandler {

    private final GamePanel gamePanel;

    public PlayerControlHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void handle(Player player) {
        setPlayerPosition(player);
        setPlayerOptions(player);
        handleShooting(player);
        handleWindowControls();
    }

    private void handleWindowControls() {
        if (escPressed) {
            gamePanel.stopGameThread();
        }
    }

    private void setPlayerOptions(Player player) {
        if (shiftPressed) {
            player.speedUp();
        } else {
            player.slowDown();
        }
        if (rPressed) {
            player.rearm();
        }
    }

    private void setPlayerPosition(Player player) {
        if (upPressed) {
            player.moveUp();
        }
        if (downPressed) {
            player.moveDown();
        }
        if (leftPressed) {
            player.moveLeft();
        }
        if (rightPressed) {
            player.moveRight();
        }
    }

    private void handleShooting(Player player) {
        if (leftMousePressed) {
            double worldMouseX = mouseX + gamePanel.getCamera().getXOffset();
            double worldMouseY = mouseY + gamePanel.getCamera().getYOffset();

            double deltaX = worldMouseX - player.getPosition().getX();
            double deltaY = worldMouseY - player.getPosition().getY();

            double angle = Math.atan2(deltaY, deltaX);

            player.fire(angle).ifPresent(projectile -> gamePanel.getDrawables().add(projectile));

        }
    }
}
