package main.game;

import main.entity.Player;
import main.window.GamePanel;

public class PlayerControlHandler extends InputHandler {

    private final GamePanel gamePanel;

    public PlayerControlHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void handle() {
        Player player = gamePanel.getPlayer();

        setPlayerPosition(player);
        setPlayerRotation(player);
        setPlayerOptions(player);
        handleShooting(player);
        handleWindowControls();
    }

    private void setPlayerRotation(Player player) {
        double worldMouseX = mouseX + gamePanel.getCamera().getXOffset();
        double worldMouseY = mouseY + gamePanel.getCamera().getYOffset();

        double deltaX = worldMouseX - player.getPosition().getX();
        double deltaY = worldMouseY - player.getPosition().getY();
        double targetAngle = Math.atan2(deltaY, deltaX);

        double currentAngle = player.getAngle();

        double angleDifference = targetAngle - currentAngle;

        angleDifference = (angleDifference + Math.PI) % (2 * Math.PI) - Math.PI;

        if (angleDifference > player.getRotationSpeed()) {
            angleDifference = player.getRotationSpeed();
        } else if (angleDifference < -player.getRotationSpeed()) {
            angleDifference = -player.getRotationSpeed();
        }

        double newAngle = currentAngle + angleDifference;
        player.setAngle(newAngle);
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
            player.fire().ifPresent(projectile -> gamePanel.getDrawables().add(projectile));
        }
    }
}
