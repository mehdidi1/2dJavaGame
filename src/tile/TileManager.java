package tile;

import main.GameConstants;
import main.GamePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    private static final Logger logger = LogManager.getLogger(TileManager.class);

    GamePanel gamePanel;
    Tile[] tiles;
    int[][] mapTileNum;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[10];
        mapTileNum = new int[GameConstants.MAX_SCREEN_COL][GameConstants.MAX_SCREEN_ROW];
        loadTileImages();
        loadMap();
    }

    /**
     * loads the images of each tile
     */
    public void loadTileImages() {
        int tileNum = 100;
        tiles = new Tile[tileNum];

        for (int i = 0; i < tileNum; i++) {
            try {
                tiles[i] = new Tile();
                tiles[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/tiles/tile_" + i + ".png")));
            } catch (Exception e) {
                logger.error("Failed to load tile: {}", i, e);
            }
        }
    }

    /**
     *Loads csv map file created using Tiled into mapTileNum
     */
    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/maps/baseMap.csv"); // Load CSV file
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;
            String line;

            while ((line = br.readLine()) != null && row < GameConstants.MAX_SCREEN_ROW) {
                line = line.trim(); // Remove leading & trailing spaces

                if (line.isEmpty()) continue; // Skip empty lines

                // Split by commas instead of spaces
                String[] numbers = line.split(",");

                for (int col = 0; col < numbers.length && col < GameConstants.MAX_SCREEN_COL; col++) {
                    if (!numbers[col].isEmpty()) { // Prevent parsing empty values
                        mapTileNum[col][row] = Integer.parseInt(numbers[col].trim());
                    }
                }
                row++;
            }

            br.close();
        } catch (Exception e) {
            logger.error("Error loading map", e);
        }
    }


    /**
     * draws the tiles of the map on the screen
     * @param g2
     */
    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < GameConstants.MAX_SCREEN_COL && row < GameConstants.MAX_SCREEN_ROW) {
            int tileNum = mapTileNum[col][row]; // Get the tile number from the map array

            g2.drawImage(tiles[tileNum].image, x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null); // Draw the tile

            col++;
            x += GameConstants.TILE_SIZE; // Move to the next tile position

            if (col == GameConstants.MAX_SCREEN_COL) {
                col = 0;
                x = 0;
                row++;
                y += GameConstants.TILE_SIZE; // Move to the next row
            }
        }
    }
}
