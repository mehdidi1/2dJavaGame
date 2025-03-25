package object.weapons;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;


public class Knife extends MeleeWeapon {
    private static final Logger logger = LogManager.getLogger(Knife.class);


    public Knife(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel, 30, 1); //SET HITBOX SIZE
        super.name = "Knife";
        super.price = 15;
        super.damage = 3;
        this.attackCooldown = 0.3;
        try {
            super.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/weapons/knife.png")));
            super.image = DrawingUtils.scaleImage(super.image, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            super.setScaledDownImage();
        } catch (IOException e) {
            logger.error("Failed to load knife image: {}", e.getMessage());
        }
    }
}
