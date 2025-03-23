package object;

import main.GameConstants;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Super class for the objects of the game
 */
public abstract class SuperObject {
    protected BufferedImage image;
    protected String name;
    protected int worldX, worldY;
    protected GamePanel gamePanel;
    protected Rectangle collider = new Rectangle(0,0,48,48);
    protected boolean solid = false;
    protected boolean collisionOn = false;

    public SuperObject(int x, int y, GamePanel gamePanel) {
        worldX = x;
        worldY = y;
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D g2) {
        int playerWorldX = gamePanel.getPlayer().getWorldX();
        int playerWorldY = gamePanel.getPlayer().getWorldY();
        int playerScreenX = gamePanel.getPlayer().getScreenX();
        int playerScreenY = gamePanel.getPlayer().getScreenY();

        int offsetX = Math.min(Math.max(playerWorldX - playerScreenX, 0), GameConstants.LEVEL_WIDTH - GameConstants.SCREEN_WIDTH);
        int offsetY = Math.min(Math.max(playerWorldY - playerScreenY, 0), GameConstants.LEVEL_HEIGHT - GameConstants.SCREEN_HEIGHT);

        int screenX = worldX - offsetX;
        int screenY = worldY - offsetY;

        // Only draw the key if it is within the screen bounds
        if (screenX + GameConstants.TILE_SIZE > 0 && screenX < GameConstants.SCREEN_WIDTH &&
                screenY + GameConstants.TILE_SIZE > 0 && screenY < GameConstants.SCREEN_HEIGHT) {
            g2.drawImage(image, screenX, screenY, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
        }
    }

    private int calculateScreenY(GamePanel gamePanel) {
        return worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();
    }

    private boolean isVisibleOnScreen(GamePanel gamePanel) {
        return worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX() &&
               worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX() &&
               worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY() &&
               worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();
    }

    public int getWorldY() {
        return worldY;
    }

    public int getWorldX() {
        return worldX;
    }

    public Rectangle getCollider() {
        return collider;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }
}
