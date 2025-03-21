package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

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
}
