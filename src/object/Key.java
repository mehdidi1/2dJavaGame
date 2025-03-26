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
public class Key extends utilityObject {
    private static final Logger logger = LogManager.getLogger(Key.class);

    public Key(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        super.name = "Key";
        try {
            super.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/keys_1_3.png")));
            super.image = DrawingUtils.scaleImage(super.image,GameConstants.TILE_SIZE,GameConstants.TILE_SIZE);
            super.setScaledDownImage();
        } catch (IOException e) {
            logger.error("Failed to load key image: {}", e.getMessage());
        }

        super.price = 30;

    }

    @Override
    public void performAttack() {
        // Trigger the win condition
        gamePanel.winGame();
    }


}
