package object.weapons;

import main.DrawingUtils;
import main.GamePanel;
import entity.Entity;
import object.SuperObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet {
    private int x, y;
    private double angle;
    private int speed = 10;
    private BufferedImage image;
    private GamePanel gamePanel;
    private boolean active = true;

    public Bullet(int x, int y, double angle, BufferedImage image, GamePanel gamePanel) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.image = image;
        this.gamePanel = gamePanel;
    }

    public void update() {
        if (active) {
            x += speed * Math.cos(angle);
            y += speed * Math.sin(angle);
            checkCollision();
        }
    }

    public void draw(Graphics2D g2d) {
        if (active) {
            int screenX = DrawingUtils.calculateScreenX(x, gamePanel);
            int screenY = DrawingUtils.calculateScreenY(y, gamePanel);

            AffineTransform transform = new AffineTransform();
            transform.translate(screenX - image.getWidth() / 2, screenY - image.getHeight() / 2);
            transform.rotate(angle, image.getWidth() / 2, image.getHeight() / 2);
            g2d.drawImage(image, transform, null);
        }
    }

    public Rectangle getCollider() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    private void checkCollision() {
        Rectangle bulletCollider = getCollider();

        // Check collision with entities
        for (Entity entity : gamePanel.getEntities()) {
            if (entity.isPlayer()) continue; // Skip player
            Rectangle entityCollider = entity.getCollider();
            int defaultX = entityCollider.x;
            int defaultY = entityCollider.y;
            entityCollider.x = defaultX + entity.getWorldX();
            entityCollider.y = defaultY + entity.getWorldY();
            if (bulletCollider.intersects(entityCollider)) {
                entity.takeDamage(1); // Deal damage to the entity
                active = false; // Deactivate the bullet
                entityCollider.x = defaultX ;
                entityCollider.y = defaultY ;
                return;
            }
            entityCollider.x = defaultX ;
            entityCollider.y = defaultY ;
        }

        // Check collision with objects
        for (SuperObject object : gamePanel.getObjects()) {
            Rectangle objectCollider = object.getCollider();
            if (bulletCollider.intersects(objectCollider)) {
                active = false; // Deactivate the bullet
                return;
            }
        }
    }

    public boolean isActive() {
        return active;
    }
}