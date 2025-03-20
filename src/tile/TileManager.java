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

    public void loadTileImages() {
        String[] tileNames = {
                "downLeftWall", "downRightWall",
                "downWall1", "downWall2", "downWall3", "downWall4",
                "leftWall1", "leftWall2", "leftWall3",
                "rightWall1", "rightWall2", "rightWall3",
                "upLeftWall", "upRightWall",
                "upWall1", "upWall2", "upWall3", "upWall4",
                "padding11", "padding12", "padding13", "padding14",
                "padding21", "padding22", "padding23", "padding24",
                "padding31", "padding32", "padding33", "padding34"
        };

        tiles = new Tile[tileNames.length];

        for (int i = 0; i < tileNames.length; i++) {
            try {
                tiles[i] = new Tile();
                tiles[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/tiles/" + tileNames[i] + ".png")));
            } catch (Exception e) {
                logger.error("Failed to load tile: {}", tileNames[i], e);
            }
        }
    }


    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/maps/baseMap.txt");
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;
            String line;

            while ((line = br.readLine()) != null && row < GameConstants.MAX_SCREEN_ROW) {
                line = line.trim(); // Remove leading & trailing spaces

                if (line.isEmpty()) continue; // Skip empty lines

                String[] numbers = line.split("\\s+"); // Splits by any spaces

                for (int col = 0; col < numbers.length && col < GameConstants.MAX_SCREEN_COL; col++) {
                    if (!numbers[col].isEmpty()) { // Prevent parsing empty strings
                        mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                    }
                }
                row++;
            }

            br.close();
        } catch (Exception e) {
            logger.error("Error loading map", e);
        }
    }


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
