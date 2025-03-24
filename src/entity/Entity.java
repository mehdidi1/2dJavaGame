package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;



/**
 * Class that represents the entities of the game
 */
public abstract class Entity {


    //Attributes
    private int worldX, worldY;
    private int speed;
    private final GamePanel gamePanel;
    protected Rectangle collider;
    protected boolean colliding = false;
    private Direction direction = Direction.LEFT;

    //animation
    private static final int SPRITE_FRAME_COUNT = 8;
    private static final int SPRITE_UPDATE_RATE = 12;
    public BufferedImage[] idleFrames;
    public BufferedImage[] walkingFrames;
    private boolean isWalking = false;
    public BufferedImage[] currentAnimationFrames;
    private int currentAnimationFrameIndex = 0;


    //Verifiers
    private boolean facingLeft = false;
    protected boolean isPlayer = false;

    public Entity(int x,int y,GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        idleFrames = new BufferedImage[SPRITE_FRAME_COUNT];
        walkingFrames = new BufferedImage[SPRITE_FRAME_COUNT];
        currentAnimationFrames = idleFrames;
        this.worldX = x;
        this.worldY = y;
    }

    /**
     * Function called inside the game loop to update the necessary fields
     */
    public abstract void update();

    /**
     * Draw the sprite of the entity inside the game loop
     */
    public abstract void draw(Graphics2D g2d);


    /*
    Updates the frame of the current animation to be displayed at a rate that is independent of current fps
     */
    private int spriteCounter = 0;

    protected void updateFrame() {
        spriteCounter++;
        if (spriteCounter >= getGamePanel().getFps() / SPRITE_UPDATE_RATE) {
            spriteCounter = 0;
            setCurrentAnimationFrameIndex((getCurrentAnimationFrameIndex() + 1) % SPRITE_FRAME_COUNT);
        }
    }

    /**
     * Update the current animation
     */
    protected void updateAnimation() {
        if (isWalking) {
            currentAnimationFrames = walkingFrames;
        } else {
            currentAnimationFrames = idleFrames;
        }
    }

    /**
     * Internal function to flip the images of the animations depending on if the character is facing left or right
     * @param img
     * @return
     */
    protected BufferedImage flipImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        // Ensure the new BufferedImage supports transparency
        BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = flipped.createGraphics();

        // Enable transparency rendering
        g2d.setComposite(AlphaComposite.Src);

        // Apply horizontal flip transformation
        AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
        transform.translate(-width, 0);
        g2d.setTransform(transform);

        // Draw the original image onto the flipped canvas
        g2d.drawImage(img, 0, 0, null);

        g2d.dispose();
        return flipped;
    }


    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }


    public void setWalking(boolean walking) {
        isWalking = walking;
    }


    public int getCurrentAnimationFrameIndex() {
        return currentAnimationFrameIndex;
    }

    public void setCurrentAnimationFrameIndex(int currentAnimationFrameIndex) {
        this.currentAnimationFrameIndex = currentAnimationFrameIndex;
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }

    public Rectangle getCollider() {
        return collider;
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public boolean isColliding() {
        return colliding;
    }

    public boolean isPlayer() {
        return isPlayer;
    }
}

