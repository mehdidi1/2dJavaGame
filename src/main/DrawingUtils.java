package main;

import java.awt.*;
import main.GameConstants;
import main.GamePanel;

public class DrawingUtils {

    public static int calculateScreenX(int worldX, GamePanel gamePanel) {
        int playerWorldX = gamePanel.getPlayer().getWorldX();
        int playerScreenX = gamePanel.getPlayer().getScreenX();
        int offsetX = Math.min(Math.max(playerWorldX - playerScreenX, 0), GameConstants.LEVEL_WIDTH - GameConstants.SCREEN_WIDTH);
        return worldX - offsetX;
    }

    public static int calculateScreenY(int worldY, GamePanel gamePanel) {
        int playerWorldY = gamePanel.getPlayer().getWorldY();
        int playerScreenY = gamePanel.getPlayer().getScreenY();
        int offsetY = Math.min(Math.max(playerWorldY - playerScreenY, 0), GameConstants.LEVEL_HEIGHT - GameConstants.SCREEN_HEIGHT);
        return worldY - offsetY;
    }

    public static boolean isWithinScreenBounds(int screenX, int screenY) {
        return screenX + GameConstants.TILE_SIZE > 0 && screenX < GameConstants.SCREEN_WIDTH &&
               screenY + GameConstants.TILE_SIZE > 0 && screenY < GameConstants.SCREEN_HEIGHT;
    }
}