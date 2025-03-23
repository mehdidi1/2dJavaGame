package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.Iterator;

/**
 * Class to check collision between entities
 */
public class CollisionChecker {

    private static final Logger logger = LogManager.getLogger(CollisionChecker.class);

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Checks collision between moving entities and tiles
     *
     * @param entity
     */
    public void checkTileCollision(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getCollider().x;
        int entityRightWorldX = entity.getWorldX() + entity.getCollider().x + entity.getCollider().width;
        int entityTopWorldY = entity.getWorldY() + entity.getCollider().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getCollider().y + entity.getCollider().height;

        int entityLeftCol = entityLeftWorldX / GameConstants.TILE_SIZE;
        int entityRightCol = entityRightWorldX / GameConstants.TILE_SIZE;
        int entityTopRow = entityTopWorldY / GameConstants.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / GameConstants.TILE_SIZE;

        int tileNum1, tileNum2;

        switch (entity.getDirection()) {
            case UP:
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / GameConstants.TILE_SIZE;
                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
                if (gamePanel.getTileManager().getTiles()[tileNum1].collision || gamePanel.getTileManager().getTiles()[tileNum2].collision) {
                    entity.setColliding(true);
                }
                break;
            case DOWN:
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / GameConstants.TILE_SIZE;
                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];
                if (gamePanel.getTileManager().getTiles()[tileNum1].collision || gamePanel.getTileManager().getTiles()[tileNum2].collision) {
                    entity.setColliding(true);
                }
                break;
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / GameConstants.TILE_SIZE;
                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
                if (gamePanel.getTileManager().getTiles()[tileNum1].collision || gamePanel.getTileManager().getTiles()[tileNum2].collision) {
                    entity.setColliding(true);
                }
                break;
            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / GameConstants.TILE_SIZE;
                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];
                if (gamePanel.getTileManager().getTiles()[tileNum1].collision || gamePanel.getTileManager().getTiles()[tileNum2].collision) {
                    entity.setColliding(true);
                }
                break;
        }
    }

    /**
     * Checks collision between entity and object
     *
     * @param entity
     * @param player
     * @return
     */
    public int checkObject(Entity entity, boolean player) {
        int index = -1; // Default index indicating no collision

        Iterator<SuperObject> iterator = gamePanel.getObjects().iterator();
        int i = 0;

        while (iterator.hasNext()) {
            SuperObject object = iterator.next();
            if (object != null) {
                // Get entity's solid area position
                Rectangle entitySolidArea = new Rectangle(entity.getWorldX() + entity.getCollider().x,
                        entity.getWorldY() + entity.getCollider().y,
                        entity.getCollider().width,
                        entity.getCollider().height);

                // Get the object's solid area position
                Rectangle objectSolidArea = new Rectangle(object.getWorldX() + object.getCollider().x,
                        object.getWorldY() + object.getCollider().y,
                        object.getCollider().width,
                        object.getCollider().height);

                switch (entity.getDirection()) {
                    case UP:
                        entitySolidArea.y -= entity.getSpeed();
                        if (entitySolidArea.intersects(objectSolidArea)) {
                            if (player) {

                                logger.trace("up collision with object");
                                index = i; // Collision detected, store the index
                            }

                        }
                        break;
                    case DOWN:
                        entitySolidArea.y += entity.getSpeed();
                        if (entitySolidArea.intersects(objectSolidArea)) {
                            if (player) {

                                logger.trace("down collision with object");
                                index = i; // Collision detected, store the index
                            }
                        }
                        break;
                    case LEFT:
                        entitySolidArea.x -= entity.getSpeed();
                        if (entitySolidArea.intersects(objectSolidArea)) {
                            if (player) {

                                logger.trace("left collision with object");
                                index = i; // Collision detected, store the index
                            }
                        }
                        break;
                    case RIGHT:
                        entitySolidArea.x += entity.getSpeed();
                        if (entitySolidArea.intersects(objectSolidArea)) {
                            if (player) {

                                logger.trace("right collision with object");
                                index = i; // Collision detected, store the index
                            }
                        }
                        break;
                }

            }
            i++;
        }

        return index; // Return the index of the collided object, or 999 if no collision
    }
}
