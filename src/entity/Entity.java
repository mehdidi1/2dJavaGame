package entity;

import main.DrawingUtils;
import main.GamePanel;
import ui.DamageIndicator;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Class that represents the entities of the game
 */
public abstract class Entity {


    //Attributes
    private int worldX, worldY;
    protected int speed;
    private final GamePanel gamePanel;
    protected Rectangle collider;
    protected boolean colliding = false;
    private Direction direction = Direction.LEFT;
    protected int health;
    protected int maxHealth;
    protected boolean dead = false;

    //animation
    private static final int SPRITE_FRAME_COUNT = 8;
    private static final int SPRITE_UPDATE_RATE = 12;
    public BufferedImage[] idleFrames;
    public BufferedImage[] walkingFrames;
    private boolean isWalking = false;
    public BufferedImage[] currentAnimationFrames;
    private int currentAnimationFrameIndex = 0;
    List<DamageIndicator> damageIndicators = new ArrayList<>();


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

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }

        int screenX = DrawingUtils.calculateScreenX(worldX, gamePanel);
        int screenY = DrawingUtils.calculateScreenY(worldY, gamePanel);

        DamageIndicator damageIndicator = new DamageIndicator(screenX, screenY, damage);
        damageIndicators.add(damageIndicator);

    }

    void updateDamageIndicators() {
        Iterator<DamageIndicator> iterator = damageIndicators.iterator();
        while (iterator.hasNext()) {
            DamageIndicator indicator = iterator.next();
            if (indicator.isExpired()) {
                iterator.remove();
            } else {
                indicator.update();
            }
        }
    }
    public void drawDamageIndicators(Graphics2D g2d) {
        for (DamageIndicator damageIndicator : damageIndicators) {
            damageIndicator.draw(g2d);
        }
    }

    public void die(){
        dead = true;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDead() {
        return dead;
    }
}

