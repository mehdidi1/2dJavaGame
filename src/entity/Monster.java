package entity;

import main.DrawingUtils;
import main.GamePanel;

import java.awt.*;

public class Monster extends Entity {
    private Rectangle collider;

    public Monster(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        collider = new Rectangle(0, 0, 50, 50); // Set the size of the collider
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2d) {
        // Calculate the screen coordinates based on the player's position and the camera offset
        int screenX = DrawingUtils.calculateScreenX(getWorldX(), getGamePanel());
        int screenY = DrawingUtils.calculateScreenY(getWorldY(), getGamePanel());

        // Draw the monster as a rectangle
        g2d.setColor(Color.RED);
        g2d.fillRect(screenX, screenY, collider.width, collider.height);
    }

    @Override
    public Rectangle getCollider() {
        return collider;
    }
}