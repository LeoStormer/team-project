package com;

import java.io.IOException;

import com.rendering.Animation;
import com.rendering.AnimationSet;
import com.rendering.TileAtlas;

/**
 * A class which stores all assets of the game. Any asset that needs to be used,
 * should be obtained from this class.
 */
public final class AssetPool {

    private static TileAtlas[] entityAtlases;

    private static TileAtlas[] objectAtlases;

    private static TileAtlas[] landscapeAtlases;

    private static AnimationSet[] animationSets;

    private static boolean loaded = false;

    public static void load() {
        if (loaded) {
            return;
        }

        entityAtlases = new TileAtlas[5];
        objectAtlases = new TileAtlas[5];
        landscapeAtlases = new TileAtlas[6];
        animationSets = new AnimationSet[5];

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

        entityAtlases[0] = new TileAtlas(entitiesFolder + "character01.png", 32, 32, 0);
        entityAtlases[1] = new TileAtlas(entitiesFolder + "Character05.png", 32, 32, 0);
        entityAtlases[2] = new TileAtlas(entitiesFolder + "character10.png", 32, 32, 0);
        entityAtlases[3] = new TileAtlas(entitiesFolder + "Knight_10.png", 32, 32, 0);
        entityAtlases[4] = new TileAtlas(entitiesFolder + "Knight_11.png", 32, 32, 0);

        objectAtlases[0] = new TileAtlas(objectsFolder + "FG_Crystals.png", 16, 16, 0);
        objectAtlases[1] = new TileAtlas(objectsFolder + "FG_Rocks.png", 16, 16, 0);

        objectAtlases[2] = new TileAtlas(objectsFolder + "FG_Signs.png", 16, 16, 0);
        objectAtlases[3] = new TileAtlas(objectsFolder + "FG_Treasure.png", 16, 16, 0);
        objectAtlases[4] = new TileAtlas(objectsFolder + "FG_Grass.png", 16, 16, 0);

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
        int[] walkDown = { 0, 1, 2, 3 };
        int[] walkLeft = { 16, 17, 18, 19 };
        int[] walkRight = { 32, 33, 34, 35 };
        int[] walkUp = { 48, 49, 50, 51 };

        int[] swordDown = { 4, 5, 6, 7 };
        int[] swordLeft = { 20, 21, 22, 23 };
        int[] swordRight = { 36, 37, 38, 39 };
        int[] swordUp = { 52, 53, 54, 55 };

        int[] axeDown = { 8, 9, 10, 11 };
        int[] axeLeft = { 24, 25, 26, 27 };
        int[] axeRight = { 40, 41, 42, 43 };
        int[] axeUp = { 56, 57, 58, 59 };
        int[] dead = { 64, 65, 66, 67 };

        for (int i = 0; i < entityAtlases.length; i++) {
            TileAtlas atlas = entityAtlases[i];
            AnimationSet animSet = new AnimationSet();
            animSet.addAnimation("walkDown", new Animation(atlas, walkDown, 0.6d, true));
            animSet.addAnimation("walkLeft", new Animation(atlas, walkLeft, 0.6d, true));
            animSet.addAnimation("walkRight", new Animation(atlas, walkRight, 0.6d, true));
            animSet.addAnimation("walkUp", new Animation(atlas, walkUp, 0.6d, true));

            animSet.addAnimation("swordDown", new Animation(atlas, swordDown, 0.5d));
            animSet.addAnimation("swordLeft", new Animation(atlas, swordLeft, 0.5d));
            animSet.addAnimation("swordRight", new Animation(atlas, swordRight, 0.5d));
            animSet.addAnimation("swordUp", new Animation(atlas, swordUp, 0.5d));

            animSet.addAnimation("axeDown", new Animation(atlas, axeDown, 0.5d));
            animSet.addAnimation("axeLeft", new Animation(atlas, axeLeft, 0.5d));
            animSet.addAnimation("axeRight", new Animation(atlas, axeRight, 0.5d));
            animSet.addAnimation("axeUp", new Animation(atlas, axeUp, 0.5d));

            animSet.addAnimation("dead", new Animation(atlas, dead, 0.5, true));

            animationSets[i] = animSet;
        }
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

    public static AnimationSet getAnimationSet(int index) {
        return animationSets[index];
    }

    public static AnimationSet[] getAnimationSets() {
        return animationSets;
    }

    public static boolean isLoaded() {
        return loaded;
    }

}
