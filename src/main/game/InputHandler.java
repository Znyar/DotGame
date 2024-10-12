package main.game;

import java.awt.event.*;

public abstract class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    protected static final double MOUSE_SENSITIVITY = 0.1;

    protected boolean upPressed, downPressed, leftPressed, rightPressed;
    protected boolean shiftPressed, rPressed;
    protected boolean leftMousePressed;
    protected boolean escPressed;
    protected double mouseX, mouseY;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightPressed = true;
            case KeyEvent.VK_SHIFT -> shiftPressed = true;
            case KeyEvent.VK_ESCAPE -> escPressed = true;
            case  KeyEvent.VK_R -> rPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightPressed = false;
            case KeyEvent.VK_SHIFT -> shiftPressed = false;
            case KeyEvent.VK_ESCAPE -> escPressed = false;
            case  KeyEvent.VK_R -> rPressed = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        switch (button) {
            case MouseEvent.BUTTON1 -> leftMousePressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        switch (button) {
            case MouseEvent.BUTTON1 -> leftMousePressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

}
