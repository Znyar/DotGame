package main.window;

import main.entity.Drawable;
import main.entity.Player;
import main.game.*;
import main.resources.ResourceLoader;
import main.sound.SoundManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends Canvas implements Runnable {

    private static final double FPS = 60;
    private static final double UPS = 60;

    private Thread gameThread;
    private boolean running;

    private Camera camera;
    private PlayerUI playerUI;

    private PlayerControlHandler playerControlHandler;
    private CollisionHandler collisionHandler;
    private EnemyControlHandler enemyControlHandler;
    private EnemyGenerator enemyGenerator;
    private ProjectileHandler projectileHandler;
    private DrawableGarbage drawableGarbage;

    private final SoundManager soundManager;

    private Player player;
    private final List<Drawable> drawables = new CopyOnWriteArrayList<>();

    private BufferStrategy bufferStrategy;
    private final BufferedImage backgroundImage = ResourceLoader.getBackgroundImage();

    public GamePanel(SoundManager soundManager) {
        this.setBackground(Color.BLACK);
        this.setIgnoreRepaint(true);
        this.setFocusable(true);
        this.soundManager = soundManager;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        this.createBufferStrategy(2);
        bufferStrategy = this.getBufferStrategy();

        camera = new Camera(this);
        playerUI = new PlayerUI(500, 100, this);

        initPlayer();

        playerControlHandler = new PlayerControlHandler(this);
        collisionHandler = new CollisionHandler(this);
        enemyControlHandler = new EnemyControlHandler(this);
        projectileHandler = new ProjectileHandler(this);

        drawableGarbage = new DrawableGarbage();

        enemyGenerator = new EnemyGenerator(this);
        enemyGenerator.start(1000);

        this.addKeyListener(playerControlHandler);
        this.addMouseListener(playerControlHandler);
        this.addMouseMotionListener(playerControlHandler);

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

        enemyGenerator.stop();

        gameThread.interrupt();
        System.exit(0);
    }

    public void scheduleRestartGame(int delay) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                restartGameThread();
            }
        }, delay);
    }

    private void restartGameThread() {
        gameThread.interrupt();
        drawables.clear();
        initPlayer();
        playerUI.reset();

        enemyGenerator.start(2000);

        startGameThread();
    }

    private void initPlayer() {
        player = new Player(this);
        drawables.add(player);
    }

    @Override
    public void run() {
        long initialTime = System.nanoTime();
        final double timeU = 1000000000 / UPS;
        final double timeF = 1000000000 / FPS;
        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();

        while (running) {

            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if (deltaU >= 1) {
                update();
                ticks++;
                deltaU--;
            }

            if (deltaF >= 1) {
                render();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 10000) {
                System.out.printf("UPS: %s, FPS: %s%n", ticks, frames);
                frames = 0;
                ticks = 0;
                timer += 1000;
            }
        }
    }

    private void update() {
        camera.update(player);
        player.update();
        projectileHandler.handle();
        collisionHandler.handleCollisions();
        playerUI.update();
        playerControlHandler.handle();
        enemyControlHandler.handle();
        drawables.removeAll(drawableGarbage.getDrawables());
        drawableGarbage.clear();
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

                    if (backgroundImage != null) {
                        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                    }

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
        drawables.forEach(drawable -> {
            Point2D position = drawable.getPosition();
            double cameraX = camera.getXOffset();
            double cameraY = camera.getYOffset();
            double drawableX = position.getX();
            double drawableY = position.getY();
            if (drawableX > cameraX || drawableX < cameraX + camera.getScreenWidth()
                    || drawableY > cameraY || drawableY < cameraY + camera.getScreenHeight())
            {
                drawable.draw(g2);
            }
        });
        playerUI.draw(g2);
        g2.translate(camera.getXOffset(), camera.getYOffset());
    }

    public Camera getCamera() {
        return camera;
    }

    public List<Drawable> getDrawables() {
        return drawables;
    }

    public Player getPlayer() {
        return player;
    }

    public DrawableGarbage getDrawableGarbage() {
        return drawableGarbage;
    }

    public PlayerUI getPlayerUI() {
        return playerUI;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

}
