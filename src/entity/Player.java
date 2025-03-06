package entity;

import main.GamePanel;
import main.InputHandler;

import java.awt.*;

public class Player extends Entity {

    InputHandler inputHandler;
    final int playerSpeed = 10;

    public Player(GamePanel gamePanel, InputHandler inputHandler, int x, int y) {
        super(gamePanel);
        this.inputHandler = inputHandler;
        setX(x);
        setY(y);
        setSpeed(playerSpeed);
    }

    @Override
    public void update() {
        if (inputHandler.isDownPressed()) {
            setY(getY() + playerSpeed);
        }
        else if (inputHandler.isUpPressed()) {
            setY(getY() - playerSpeed);
        }
        else if (inputHandler.isLeftPressed()) {
            setX(getX() - playerSpeed);
        }
        else if (inputHandler.isRightPressed()) {
            setX(getX() + playerSpeed);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(getX(), getY(), getGamePanel().getTileSize(), getGamePanel().getTileSize());
    }
}
