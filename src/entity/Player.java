package entity;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import main.InputHandler;
import object.SuperObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Class representing the player
 */
public class Player extends Entity {

    private static final Logger logger = LogManager.getLogger(Player.class);

    InputHandler inputHandler;
    private final int screenX, screenY;
    Inventory inventory = new Inventory(getGamePanel());

    //PLAYER ATTRIBUTES
    private int maxHealth = 3;
    private int health = 3;
    private int shields = 3;
    private int maxShields = 3;

    public Player(GamePanel gamePanel, InputHandler inputHandler, int x, int y) {
        super(x,y,gamePanel);
        isPlayer = true;
        this.inputHandler = inputHandler;
        setSpeed(GameConstants.PLAYER_SPEED);
        getPlayerImage();
        screenX = GameConstants.SCREEN_WIDTH / 2 - GameConstants.TILE_SIZE / 2;
        screenY = GameConstants.SCREEN_HEIGHT / 2 - GameConstants.TILE_SIZE / 2;
        collider = new Rectangle(8, 12, 32, 32); //Collider box settings
    }

    private void getPlayerImage() {
        try {
            for (int i = 0; i < 8; i++) {
                idleFrames[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/player/idle/frame_" + i + "_delay-0.05s.png")));
                idleFrames[i] = DrawingUtils.scaleImage(idleFrames[i], GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
                walkingFrames[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/player/walking/Knight_7-" + (i + 1) + ".png.png")));
                walkingFrames[i] = DrawingUtils.scaleImage(walkingFrames[i], GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            }

        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void update() {
        updateMovement();
        updateSelectedItem();
        updateAttack();
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
                inventory.pickUp(objIndex);
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

    private void updateAttack(){
        if (inputHandler.isAttackPressed()) {
            inventory.getSelectedObject().performAttack();
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage img = currentAnimationFrames[getCurrentAnimationFrameIndex()];
        boolean facingLeft = isFacingLeft();
        if (facingLeft) {
            img = flipImage(img);
        }

        int screenX = calculateScreenX();
        int screenY = calculateScreenY();

        // Draw the player
        g2d.drawImage(img, screenX, screenY, null);

        // Draw the selected item in the player's hands
        SuperObject selectedItem = inventory.getInventoryTab()[inventory.getSelectedSlot()];
        if (selectedItem != null) {
            BufferedImage selectedItemIcon = selectedItem.getScaledDownImage();
            if (selectedItemIcon != null) {
                int itemX = screenX + (img.getWidth() / 2) - (selectedItemIcon.getWidth() / 2) + 10;
                int itemY = screenY + img.getHeight() - selectedItemIcon.getHeight();

                // Calculate the angle between the player and the mouse cursor
                Point mousePosition = getGamePanel().getMousePosition();
                if (mousePosition != null) {
                    double angle = Math.atan2(mousePosition.y - (screenY + (double) img.getHeight() / 2), mousePosition.x - (screenX + (double) img.getWidth() / 2));
                    if (Math.abs(angle) > Math.PI / 2) {
                        // Flip the image horizontally and adjust the angle
                        selectedItemIcon = flipImage(selectedItemIcon);
                        angle = -(angle > 0 ? Math.PI - angle : -Math.PI - angle);
                        itemX = itemX - 20;
                    }
                    drawRotatedImage(g2d, selectedItemIcon, itemX , itemY, angle);
                } else {
                    g2d.drawImage(selectedItemIcon, itemX, itemY, null);
                }
            }
        }
    }

    private void drawRotatedImage(Graphics2D g2d, BufferedImage image, int x, int y, double angle) {
        AffineTransform backup = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.translate(x + (double) image.getWidth() / 2, y + (double) image.getHeight() / 2);
        transform.rotate(angle);
        transform.translate((double) -image.getWidth() / 2, (double) -image.getHeight() / 2);
        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.setTransform(backup);
    }

    private void updateSelectedItem(){
        if (inputHandler.isItem1Pressed()) {
            inventory.setSelectedSlot(0);
        }
        if (inputHandler.isItem2Pressed()) {
            inventory.setSelectedSlot(1);
        }
        if (inputHandler.isItem3Pressed()) {
            inventory.setSelectedSlot(2);
        }
    }

    public int calculateScreenX() {

        int screenX = Math.min(getWorldX(), this.screenX);
        int rightOffset = GameConstants.LEVEL_WIDTH - getWorldX();
        if (rightOffset < GameConstants.SCREEN_WIDTH - this.screenX) {
            screenX = GameConstants.SCREEN_WIDTH - rightOffset;
        }

        return screenX;
    }

    public int calculateScreenY() {

        int screenY = Math.min(getWorldY(), this.screenY);
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

    public Inventory getInventory() {
        return inventory;
    }
}
