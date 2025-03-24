package object.weapons;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Gun extends RangedWeapon {
    private static final Logger logger = LogManager.getLogger(Gun.class);

    public Gun(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        DrawingUtils drawingUtils = new DrawingUtils();
        super.name = "Gun";
        try {
            super.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/weapons/gun.png")));
            super.image = drawingUtils.scaleImage(super.image, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            super.setScaledDownImage();
        } catch (IOException e) {
            logger.error("Failed to load gun image: {}", e.getMessage());
        }
    }


}
