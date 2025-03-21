package entity;

import main.GameConstants;
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
    private final int screenX, screenY;

    public Player(GamePanel gamePanel, InputHandler inputHandler, int x, int y) {
        super(gamePanel);
        this.inputHandler = inputHandler;
        setWorldX(x);
        setWorldY(y);
        setSpeed(GameConstants.PLAYER_SPEED);
        getPlayerImage();
        screenX = GameConstants.SCREEN_WIDTH / 2 - GameConstants.TILE_SIZE / 2;
        screenY = GameConstants.SCREEN_HEIGHT / 2 - GameConstants.TILE_SIZE / 2;
        collider = new Rectangle(8, 12, 32, 32); //Collider box settings
    }

    public void getPlayerImage() {
        try {
            for (int i = 0; i < 8; i++) {
                idleFrames[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/player/idle/frame_" + i + "_delay-0.05s.png")));
                walkingFrames[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/player/walking/Knight_7-" + (i + 1) + ".png.png")));
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
            setWalking(true);
            setDirection(Direction.DOWN);
        } else if (inputHandler.isUpPressed()) {
            setWalking(true);
            setDirection(Direction.UP);
        } else if (inputHandler.isLeftPressed()) {
            setWalking(true);
            setFacingLeft(true);
            setDirection(Direction.LEFT);
        } else if (inputHandler.isRightPressed()) {
            setWalking(true);
            setFacingLeft(false);
            setDirection(Direction.RIGHT);
        } else {
            setWalking(false);
        }

        if (isWalking()) {
            //Check tile collision
            setColliding(false);
            getGamePanel().getCollisionChecker().checkTileCollision(this);

            if (!colliding) {
                switch (getDirection()) {
                    case UP:
                        setWorldY(getWorldY() - GameConstants.PLAYER_SPEED);
                        break;
                    case DOWN:
                        setWorldY(getWorldY() + GameConstants.PLAYER_SPEED);
                        break;
                    case LEFT:
                        setWorldX(getWorldX() - GameConstants.PLAYER_SPEED);
                        break;
                    case RIGHT:
                        setWorldX(getWorldX() + GameConstants.PLAYER_SPEED);
                        break;
                }
            }
        }

    }

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage img = currentAnimationFrames[getCurrentAnimationFrameIndex()];
        if (isFacingLeft()) {
            img = flipImage(img);
        }
        g2d.drawImage(img, this.screenX, this.screenY, getGamePanel().getTileSize(), getGamePanel().getTileSize(), null);

    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}
