package object;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import main.InputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Class that represents the key object
 */
public class Key extends SuperObject {
    private static final Logger logger = LogManager.getLogger(Key.class);

    public Key(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        DrawingUtils drawingUtils = new DrawingUtils();
        super.name = "Key";
        try {
            super.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/keys_1_3.png")));
            super.image = drawingUtils.scaleImage(super.image,GameConstants.TILE_SIZE,GameConstants.TILE_SIZE);
            super.setScaledDownImage();
        } catch (IOException e) {
            logger.error("Failed to load key image: {}", e.getMessage());
        }
    }


}
