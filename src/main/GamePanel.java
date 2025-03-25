package main;

import entity.Entity;
import entity.Inventory;
import entity.Player;
import object.SuperObject;
import object.weapons.Bullet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tile.TileManager;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    private static final Logger logger = LogManager.getLogger(GamePanel.class);

    Thread gameThread;
    InputHandler inputHandler = new InputHandler();
    TileManager tileManager = new TileManager(this);
    CollisionChecker collisionChecker = new CollisionChecker(this);
    AssetSetter assetSetter = new AssetSetter(this);
    public List<SuperObject> objects = new ArrayList<>();
    public List<Entity> entities = new LinkedList<>();
    public List<Bullet> bullets = new ArrayList<>();
    UI ui = new UI(this);

    Player player = new Player(this, inputHandler, 500, 500);

    public GamePanel() {
        this.setPreferredSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        this.setBackground(Color.BLUE);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.addMouseListener(inputHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObjects();
        assetSetter.setEntities();
    }

    public void startGameThread() {
        try {
            gameThread = new Thread(this);
            gameThread.start();
        } catch (Exception e) {
            logger.error("Failed to start game thread: {}", e.getMessage());
        }
    }

    public void stopGameThread() {
        if (gameThread != null) {
            try {
                gameThread.join();
                gameThread = null;
            } catch (InterruptedException e) {
                logger.error("Error stopping game thread: {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / GameConstants.FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        int lastDrawCount = 60;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                if (drawCount != lastDrawCount) {
                    logger.info("Fps variation: {}", drawCount);
                }
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        Iterator<Entity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            Entity entity = entityIterator.next();
            entity.update();
            if (entity.isDead()) {
                entityIterator.remove();
            }
        }

        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();
            if (!bullet.isActive()) {
                bulletIterator.remove();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        try {
            tileManager.draw(g2d);
            for (SuperObject object : objects) {
                object.draw(g2d);
            }
            for (Entity entity : entities) {
                entity.draw(g2d);
            }
            for (Bullet bullet : bullets) {
                bullet.draw(g2d);
            }
            ui.draw(g2d);
        } finally {
            g2d.dispose();
        }
    }

    public int getTileSize() {
        return GameConstants.TILE_SIZE;
    }

    public double getFps() {
        return GameConstants.FPS;
    }

    public Player getPlayer() {
        return player;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public List<SuperObject> getObjects() {
        return objects;
    }

    public Inventory getInventory() {
        return player.getInventory();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
}