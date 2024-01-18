package com.rendering;

/**
 * This class represents an animation that can be played.
 */
public class Animation {

    /**
     * This class represents a point in time in an animation.
     */
    public class Frame {

        public Tile tile;

        /**
         * The time in seconds before moving onto the next frame
         */
        public double delay;

        public Frame(Tile tile, double delay) {
            this.tile = tile;
            this.delay = delay;
        }

    }

    private Frame[] frames;

    /**
     * Determines whether this Animation loops.
     */
    public boolean looped;

    /**
     * Constructs an animation that does not loop from a tile atlas.
     * 
     * @param atlas             the {@link TileAtlas}
     * @param spriteIndices     the indices of the tiles in the atlas
     * @param animationDuration
     */
    public Animation(TileAtlas atlas, int[] spriteIndices, double animationDuration) {
        this(atlas.getTiles(spriteIndices), animationDuration, false);
    }

    /**
     * Constructs an animation from a tile atlas.
     * 
     * @param atlas             the {@link TileAtlas}
     * @param spriteIndices     the indices of the tiles in the atlas
     * @param animationDuration
     * @param looped            whether the animation loops or not
     */
    public Animation(TileAtlas atlas, int[] spriteIndices, double animationDuration, boolean looped) {
        this(atlas.getTiles(spriteIndices), animationDuration, looped);
    }

    /**
     * Constructs an animation from an array of tiles.
     * 
     * @param tiles
     * @param animationDuration
     */
    public Animation(Tile[] tiles, double animationDuration) {
        this(tiles, animationDuration, false);
    }

    /**
     * Constructs an Animation from an array of tiles.
     * 
     * @param tiles
     * @param animationDuration
     * @param looped            whether the animation loops or not
     */
    public Animation(Tile[] tiles, double animationDuration, boolean looped) {
        double frameDelay = animationDuration / tiles.length;
        this.frames = new Frame[tiles.length];

        for (int i = 0; i < tiles.length; i++)
            frames[i] = new Frame(tiles[i], frameDelay);

        this.looped = looped;
    }

    /**
     * @param index
     * @return The {@link Frame} at index
     */
    public Frame getFrame(int index) {
        return frames[index];
    }

    /**
     * @return The number of frames that make up this animation.
     */
    public int getFrameCount() {
        return frames.length;
    }

    /**
     * @return whether or not this animation loops
     */
    public boolean isLooped() {
        return looped;
    }

    /**
     * @param looped whether or not this animation loops
     */
    public void setLooped(boolean looped) {
        this.looped = looped;
    }

}
