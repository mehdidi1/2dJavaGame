package entity;

import main.GamePanel;
import main.InputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Player extends Entity {

    private static final Logger logger = LogManager.getLogger(Player.class);

    InputHandler inputHandler;
    final int playerSpeed = 5;

    public Player(GamePanel gamePanel, InputHandler inputHandler, int x, int y) {
        super(gamePanel);
        this.inputHandler = inputHandler;
        setX(x);
        setY(y);
        setSpeed(playerSpeed);
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            for (int i = 0; i < 8; i++) {
                idleFrames[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/idle/frame_" + i + "_delay-0.05s.png")));
                walkingFrames[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walking/Knight_7-" + (i + 1) + ".png.png")));
            }

        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void update() {
        updateMovement();
        updateAnimation();
        updateFrame();
    }

    private void updateMovement() {
        if (inputHandler.isDownPressed()) {
            setY(getY() + playerSpeed);
            setWalking(true);
        } else if (inputHandler.isUpPressed()) {
            setY(getY() - playerSpeed);
            setWalking(true);
        } else if (inputHandler.isLeftPressed()) {
            setX(getX() - playerSpeed);
            setWalking(true);
            setFacingLeft(true);
        } else if (inputHandler.isRightPressed()) {
            setX(getX() + playerSpeed);
            setWalking(true);
            setFacingLeft(false);
        } else {
            setWalking(false);
        }
    }


    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage img = currentAnimationFrames[getCurrentAnimationFrameIndex()];
        if (isFacingLeft()) {
            img = flipImage(img);
        }
        g2d.drawImage(img, getX(), getY(), getGamePanel().getTileSize(), getGamePanel().getTileSize(), null);
    }
}
