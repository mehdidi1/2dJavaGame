package entity;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import main.InputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Class representing the player
 */
public class Player extends Entity {

    private static final Logger logger = LogManager.getLogger(Player.class);

    InputHandler inputHandler;
    private final int screenX, screenY;

    //PLAYER ATTRIBUTES
    private int maxHealth = 3;
    private int health = 2;
    private int shields = 3;
    private int maxShields = 3;

    public Player(GamePanel gamePanel, InputHandler inputHandler, int x, int y) {
        super(gamePanel);
        isPlayer = true;
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
        DrawingUtils drawingUtils = new DrawingUtils();
        try {
            for (int i = 0; i < 8; i++) {
                idleFrames[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/player/idle/frame_" + i + "_delay-0.05s.png")));
                idleFrames[i] = drawingUtils.scaleImage(idleFrames[i], GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
                walkingFrames[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/player/walking/Knight_7-" + (i + 1) + ".png.png")));
                walkingFrames[i] = drawingUtils.scaleImage(walkingFrames[i], GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
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

            //CHECK TILE COLLISION
            setColliding(false);
            getGamePanel().getCollisionChecker().checkTileCollision(this);

            //CHECK OBJECT COLLISION
            int objIndex = getGamePanel().getCollisionChecker().checkObject(this,true);
            if (objIndex != -1) {
                System.out.println("deleting object" + objIndex);
                getGamePanel().getInventory().pickUp(objIndex);
            }


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

    public void draw(Graphics2D g2d) {
        BufferedImage img = currentAnimationFrames[getCurrentAnimationFrameIndex()];
        if (isFacingLeft()) {
            img = flipImage(img);
        }

        int screenX = calculateScreenX();
        int screenY = calculateScreenY();

        g2d.drawImage(img, screenX, screenY, null);
    }

    private int calculateScreenX() {
        int screenX = this.screenX;

        if (getWorldX() < this.screenX) {
            screenX = getWorldX();
        }
        int rightOffset = GameConstants.LEVEL_WIDTH - getWorldX();
        if (rightOffset < GameConstants.SCREEN_WIDTH - this.screenX) {
            screenX = GameConstants.SCREEN_WIDTH - rightOffset;
        }

        return screenX;
    }

    private int calculateScreenY() {
        int screenY = this.screenY;

        if (getWorldY() < this.screenY) {
            screenY = getWorldY();
        }
        int bottomOffset = GameConstants.LEVEL_HEIGHT - getWorldY();
        if (bottomOffset < GameConstants.SCREEN_HEIGHT - this.screenY) {
            screenY = GameConstants.SCREEN_HEIGHT - bottomOffset;
        }

        return screenY;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getShields() {
        return shields;
    }

    public int getMaxShields() {
        return maxShields;
    }
}
