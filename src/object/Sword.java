package object.weapons;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;


public class Sword extends MeleeWeapon {
    private static final Logger logger = LogManager.getLogger(Knife.class);


    public Sword(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel, 45, 2); //SET HITBOX SIZE
        super.name = "Sword";
        super.price = 25;
        super.damage = 6;
        this.attackCooldown = 0.5;
        try {
            super.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/weapons/sword.png")));
            super.image = DrawingUtils.scaleImage(super.image, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            super.setScaledDownImage();
        } catch (IOException e) {
            logger.error("Failed to load Sword image: {}", e.getMessage());
        }
    }
}
