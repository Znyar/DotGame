package main.window;

import main.entity.Drawable;
import main.entity.Player;
import main.game.CollisionHandler;
import main.game.EnemyControlHandler;
import main.game.EnemyGenerator;
import main.game.PlayerControlHandler;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends Canvas implements Runnable {

    private static final int TARGET_FPS = 120;
    private static final long NANOSECONDS_PER_SECOND = 1000000000L;

    private Thread gameThread;
    private boolean running;

    private Camera camera;

    private final PlayerControlHandler playerControlHandler;
    private final CollisionHandler collisionHandler;
    private final EnemyControlHandler enemyControlHandler;
    private final EnemyGenerator enemyGenerator;

    private Player player;
    private final List<Drawable> drawables;

    private BufferStrategy bufferStrategy;

    public GamePanel() {
        this.setBackground(Color.BLACK);
        this.setIgnoreRepaint(true);

        drawables = new ArrayList<>();

        playerControlHandler = new PlayerControlHandler(this);

        collisionHandler = new CollisionHandler();
        enemyGenerator = new EnemyGenerator(this, drawables);
        enemyControlHandler = new EnemyControlHandler();

        this.addKeyListener(playerControlHandler);
        this.addMouseListener(playerControlHandler);
        this.addMouseMotionListener(playerControlHandler);

        this.setFocusable(true);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        this.createBufferStrategy(2);
        bufferStrategy = this.getBufferStrategy();

        camera = new Camera(getWidth(), getHeight());
        initPlayer();

        startGameThread();
    }

    public void startGameThread() {
        if (gameThread == null || !running) {
            gameThread = new Thread(this);
            running = true;
            gameThread.start();
        }
    }

    public void stopGameThread() {
        running = false;
        gameThread.interrupt();
        System.exit(0);
    }

    public void restartGameThread() {
        gameThread.interrupt();
        drawables.clear();
        initPlayer();
        startGameThread();
    }

    private void initPlayer() {
        player = new Player(this, getWidth() / 2, getHeight() / 2);
        drawables.add(player);
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerFrame = (double) NANOSECONDS_PER_SECOND / TARGET_FPS;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerFrame;
            lastTime = now;

            while (delta >= 1) {
                update();
                delta--;
            }

            render();
        }
    }

    private void update() {
        camera.update(player);
        collisionHandler.handleCollisions(drawables);
        enemyGenerator.update();
        playerControlHandler.handle(player);
        enemyControlHandler.handle(drawables, player);
    }

    private void render() {
        if (bufferStrategy == null) {
            return;
        }

        do {
            do {
                Graphics2D g2 = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    g2.clearRect(0, 0, getWidth(), getHeight());
                    draw(g2);
                } finally {
                    g2.dispose();
                }
            } while (bufferStrategy.contentsRestored());
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }

    private void draw(Graphics2D g2) {
        g2.translate(-camera.getXOffset(), -camera.getYOffset());
        drawables.forEach(drawable -> drawable.draw(g2));
        g2.translate(camera.getXOffset(), camera.getYOffset());
    }

}
