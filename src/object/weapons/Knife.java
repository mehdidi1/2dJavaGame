package object.weapons;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import object.MeleeObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;


public class Knife extends MeleeObject {
    private static final Logger logger = LogManager.getLogger(Knife.class);

    public Knife(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        DrawingUtils drawingUtils = new DrawingUtils();
        super.name = "Gun";
        try {
            super.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/weapons/knife.png")));
            super.image = drawingUtils.scaleImage(super.image, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            super.setScaledDownImage();
        } catch (IOException e) {
            logger.error("Failed to load knife image: {}", e.getMessage());
        }
    }
}
