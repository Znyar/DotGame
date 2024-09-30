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
        handleWindowControls();
    }

    private void handleWindowControls() {
        if (super.escPressed) {
            gamePanel.stopGameThread();
        }
    }

    private void setPlayerOptions(Player player) {
        if (shiftPressed) {
            player.speedUp();
        } else {
            player.slowDown();
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

}
