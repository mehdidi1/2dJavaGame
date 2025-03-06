package entity;

import main.GamePanel;

import java.awt.*;

/**
 * Class that represents the entities of the game
 */
public abstract class Entity {
    private int x, y;
    private int speed;
    private GamePanel gamePanel;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Function called inside the game loop to update the necessary fields
     */
    public abstract void update();

    /**
     * Draw the sprite of the entity inside the game loop
     */
    public abstract void draw(Graphics2D g2d);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
