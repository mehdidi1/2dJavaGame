package main;

/**
 * Global game constants
 */
public final class GameConstants {
    private GameConstants() {} // Prevent instantiation

    // Screen settings
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    public static final double FPS = 60.0;

    // Player settings
    public static final int PLAYER_SPEED = 5;

    //Level settings
    public static final int MAX_WORLD_ROW = 50;
    public static final int MAX_WORLD_COL = 50;
    public static final int LEVEL_WIDTH = TILE_SIZE * MAX_WORLD_COL;
    public static final int LEVEL_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;
}