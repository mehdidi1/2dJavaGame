package object;

import entity.Enemy;
import entity.Soldier;
import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import object.weapons.Gun;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Button extends utilityObject {

    private static final Logger logger = LogManager.getLogger(Button.class);
    private int enemyCount = 3; // Number of enemies to spawn (doubles after each push)
    private boolean roomBlocked = false; // Whether the room entrance is blocked
    private int enemiesIInBegin;

    public Button(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        this.name = "Button";
        this.collider = new Rectangle(0, 0, 32, 32); // Example collider size
        loadButtonImage();
    }

    @Override
    public void performAttack() {

        // Spawn enemies in the room
        spawnEnemies();

        // Block the room entrance
        roomBlocked = true;

        // Double the number of enemies for the next push
        enemyCount *= 2;
    }

    private void loadButtonImage(){
        try {
            super.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/button.png")));
            super.image = DrawingUtils.scaleImage(super.image, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            super.setScaledDownImage();
        } catch (IOException e) {
            logger.error("Failed to load gun image: {}", e.getMessage());
        }
    }

    private void spawnEnemies() {
        enemiesIInBegin = gamePanel.getEntities().size();
        List<Enemy> newEnemies = new ArrayList<>(); // Temporary list for new enemies
        for (int i = 0; i < enemyCount; i++) {
            int spawnX = (int) (Math.random() * 15 * GameConstants.TILE_SIZE + 45 * GameConstants.TILE_SIZE); // Random spawn position in the room
            int spawnY = (int) (Math.random() * 20 * GameConstants.TILE_SIZE + 20 * GameConstants.TILE_SIZE);
            Enemy enemy = new Soldier(spawnX, spawnY, gamePanel); // Example enemy type
            newEnemies.add(enemy);
        }
        gamePanel.entitiesToAdd.addAll(newEnemies); // Add new enemies to a separate list
    }

    public boolean isRoomBlocked() {
        return roomBlocked;
    }

    public void unblockRoom() {
        roomBlocked = false;
    }

    public int getEnemiesIInBegin() {
        return enemiesIInBegin;
    }
}