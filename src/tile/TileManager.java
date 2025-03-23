package tile;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

/**
 * Class that manages displaying the current level map using tiles
 */
public class TileManager {

    private static final Logger logger = LogManager.getLogger(TileManager.class);

    GamePanel gamePanel;
    Tile[] tiles;
    int[][] mapTileNum;
    HashSet<Integer> collidingTiles = new HashSet<>();

    public TileManager(GamePanel gamePanel) {
        collidingTiles.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 10, 20, 30, 40, 41, 42, 43, 44, 45, 15, 25, 35));
        this.gamePanel = gamePanel;
        tiles = new Tile[10];
        mapTileNum = new int[GameConstants.MAX_WORLD_COL][GameConstants.MAX_WORLD_ROW];
        loadTileImages();
        loadMap();
    }

    /**
     * loads the images of each tile
     */
    public void loadTileImages() {
        DrawingUtils drawingUtils = new DrawingUtils();
        int tileNum = 100;
        tiles = new Tile[tileNum];

        for (int i = 0; i < tileNum; i++) {
            try {
                tiles[i] = new Tile();
                tiles[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/tiles/tile_" + i + ".png")));
                tiles[i].image = drawingUtils.scaleImage(tiles[i].image, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
                if (collidingTiles.contains(i)) {
                    tiles[i].collision = true;
                }
            } catch (Exception e) {
                logger.error("Failed to load tile: {}", i, e);
            }
        }
    }

    /**
     * Loads csv map file created using Tiled into mapTileNum
     */
    public void loadMap() {
        try (InputStream is = getClass().getResourceAsStream("/maps/biggerBaseMap.csv");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            int row = 0;
            String line;

            while ((line = br.readLine()) != null && row < GameConstants.MAX_WORLD_ROW) {
                line = line.trim(); // Remove leading & trailing spaces

                if (line.isEmpty()) continue; // Skip empty lines

                // Split by commas instead of spaces
                String[] numbers = line.split(",");

                for (int col = 0; col < numbers.length && col < GameConstants.MAX_WORLD_COL; col++) {
                    if (!numbers[col].isEmpty()) { // Prevent parsing empty values
                        mapTileNum[col][row] = Integer.parseInt(numbers[col].trim());
                    }
                }
                row++;
            }
        } catch (Exception e) {
            logger.error("Error loading map", e);
        }
    }

    /**
     * draws the tiles of the map seen by the camera on the screen
     *
     * @param g2
     */
    public void draw(Graphics2D g2) {
        for (int worldRow = 0; worldRow < GameConstants.MAX_WORLD_ROW; worldRow++) {
            for (int worldCol = 0; worldCol < GameConstants.MAX_WORLD_COL; worldCol++) {
                int tileNum = mapTileNum[worldCol][worldRow]; // Get the tile number from the map array

                int worldX = worldCol * GameConstants.TILE_SIZE;
                int worldY = worldRow * GameConstants.TILE_SIZE;
                int screenX = DrawingUtils.calculateScreenX(worldX, gamePanel);
                int screenY = DrawingUtils.calculateScreenY(worldY, gamePanel);

                // Only draw tiles that are within the screen bounds
                if (DrawingUtils.isWithinScreenBounds(screenX, screenY)) {
                    g2.drawImage(tiles[tileNum].image, screenX, screenY, null); // Draw the tile
                }
            }
        }
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }

    public Tile[] getTiles() {
        return tiles;
    }
}
