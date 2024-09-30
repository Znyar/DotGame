package main.entity;

import main.window.GamePanel;

import java.awt.geom.Point2D;

public class Player extends PolygonShape {

    private final GamePanel gamePanel;

    private int tileSize;
    private double speed;
    private double maxSpeed;
    private double minSpeed;
    private final static double DEFAULT_SPEED = 3;
    private final static double DEFAULT_MAX_SPEED = DEFAULT_SPEED * 2;
    private final static double DEFAULT_MIN_SPEED = DEFAULT_SPEED;
    private static final int DEFAULT_TILE_SIZE = 48;

    public Player(GamePanel gamePanel, int startX, int startY) {
        super(startX, startY, DEFAULT_TILE_SIZE);
        tileSize = DEFAULT_TILE_SIZE;
        speed = DEFAULT_MIN_SPEED;
        maxSpeed = DEFAULT_MAX_SPEED;
        minSpeed = DEFAULT_MIN_SPEED;
        this.gamePanel = gamePanel;
    }

    public void moveUp() {
        super.move(new Point2D.Double(center.getX(), center.getY() - speed));
    }
    public void moveDown() {
        super.move(new Point2D.Double(center.getX(), center.getY() + speed));
    }
    public void moveLeft() {
        super.move(new Point2D.Double(center.getX() - speed, center.getY()));
    }
    public void moveRight() {
        super.move(new Point2D.Double(center.getX() + speed, center.getY()));
    }

    public void speedUp() {
        if (speed < maxSpeed)
            speed += 2;
    }

    public Point2D getPosition() {
        return center;
    }

    public void slowDown() {
        if (speed > minSpeed)
            speed -= 5;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy) {
            gamePanel.restartGameThread();
        }
    }

}
