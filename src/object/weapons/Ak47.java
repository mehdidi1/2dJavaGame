package object.weapons;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Ak47 extends RangedWeapon {
    private static final Logger logger = LogManager.getLogger(Ak47.class);

    public Ak47(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        super.name = "Ak47";
        try {
            super.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/weapons/ak47.png")));
            super.image = DrawingUtils.scaleImage(super.image, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            super.setScaledDownImage();
        } catch (IOException e) {
            logger.error("Failed to load Ak47 image: {}", e.getMessage());
        }
        this.attackCooldown = 0.2;
        this.energyCost = 3;
        super.price = 20;
    }


}
