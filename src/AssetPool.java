import java.io.IOException;

import query.Rect;
import rendering.TileAtlas;

/**
 * A class which stores all assets of the game. Any asset that needs to be used,
 * should be obtained from this class.
 */
public final class AssetPool {
    private static TileAtlas[] entityAtlases;
    private static TileAtlas[] objectAtlases;
    private static TileAtlas[] landscapeAtlases;
    private static boolean loaded = false;

    public static void load() {
        if (loaded) {
            return;
        }

        entityAtlases = new TileAtlas[5];
        objectAtlases = new TileAtlas[5];
        landscapeAtlases = new TileAtlas[6];

        try {
            loadAtlases();
            createAnimationSets();
            loaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load all assets in the res folder as TileAtlases.
     * 
     * @throws IOException
     */
    private static void loadAtlases() throws IOException {
        String entitiesFolder = "./res/sprites/entities/";
        String objectsFolder = "./res/sprites/objects/";
        String landscapeFolder = "./res/sprites/landscape/";
        int entityTileWidth = 32;
        int entityTileHeight = 32;
        int entityTileSpacing = 0;
        Rect[] entitySampleAreas = { new Rect(0, 0, 512, 128), new Rect(0, 128, 384, 128) };
        int[][] entityTileDimensions = { { entityTileWidth, entityTileHeight, entityTileSpacing },
                { entityTileWidth, entityTileHeight, entityTileSpacing } };

        entityAtlases[0] = new TileAtlas(entitiesFolder + "character01.png", entitySampleAreas, entityTileDimensions);
        entityAtlases[1] = new TileAtlas(entitiesFolder + "Character05.png", entitySampleAreas, entityTileDimensions);
        entityAtlases[2] = new TileAtlas(entitiesFolder + "character10.png", entitySampleAreas, entityTileDimensions);
        entityAtlases[3] = new TileAtlas(entitiesFolder + "Knight_10.png", entitySampleAreas, entityTileDimensions);
        entityAtlases[4] = new TileAtlas(entitiesFolder + "Knight_11.png", entitySampleAreas, entityTileDimensions);

        objectAtlases[0] = new TileAtlas(objectsFolder + "FG_Crystals.png", 16, 16, 0);
        objectAtlases[1] = new TileAtlas(objectsFolder + "FG_Rocks.png", 16, 16, 0);

        Rect[] signSampleAreas = { new Rect(0, 0, 64, 128), new Rect(80, 0, 128, 128) };
        int[][] signTileDimensions = { { 16, 16, 0 }, { 16, 32, 0 } };
        objectAtlases[2] = new TileAtlas(objectsFolder + "FG_Signs.png", signSampleAreas, signTileDimensions);

        Rect[] treasureSampleAreas = { new Rect(0, 0, 64, 64), new Rect(0, 64, 64, 32) };
        int[][] treasureTileDimensions = { { 16, 16, 0 }, { 32, 32, 0 } };
        objectAtlases[3] = new TileAtlas(objectsFolder + "FG_Treasure.png", treasureSampleAreas,
                treasureTileDimensions);

        Rect[] grassSampleAreas = { new Rect(0, 0, 128, 64), new Rect(0, 64, 192, 32) };
        int[][] grassTileDimensions = { { 16, 16, 0 }, { 32, 32, 0 } };
        objectAtlases[4] = new TileAtlas(objectsFolder + "FG_Grass.png", grassSampleAreas, grassTileDimensions);

        landscapeAtlases[0] = new TileAtlas(landscapeFolder + "FG_Fences.png", 16, 16, 0);
        landscapeAtlases[1] = new TileAtlas(landscapeFolder + "FG_Grounds.png", 16, 16, 0);
        landscapeAtlases[2] = new TileAtlas(landscapeFolder + "FG_Logs.png", 16, 16, 0);
        landscapeAtlases[3] = new TileAtlas(landscapeFolder + "FG_Mushrooms.png", 16, 16, 0);
        landscapeAtlases[4] = new TileAtlas(landscapeFolder + "FG_Trees.png", 16, 16, 0);
        landscapeAtlases[5] = new TileAtlas(landscapeFolder + "FG_Wild_Flowers.png", 16, 16, 0);
    }

    /**
     * Create entity animation sets
     */
    private static void createAnimationSets() {

    }

    public static TileAtlas[] getEntityAtlases() {
        return entityAtlases;
    }

    public static TileAtlas getEntityAtlas(int index) {
        return entityAtlases[index];
    }

    public static TileAtlas[] getObjectAtlases() {
        return objectAtlases;
    }

    public static TileAtlas getObjectAtlas(int index) {
        return objectAtlases[index];
    }

    public static TileAtlas[] getLandscapeAtlases() {
        return landscapeAtlases;
    }

    public static TileAtlas getLandscapeAtlas(int index) {
        return landscapeAtlases[index];
    }

    public static boolean isLoaded() {
        return loaded;
    }
}
