package com.rendering;

public class Animation {
    public class Frame {
        public Tile tile;
        public double delay;

        public Frame(Tile tile, double delay) {
            this.tile = tile;
            this.delay = delay;
        }
    }

    /**
     * Determins whether an {@link Animator} will loop an Animation.
     */
    public boolean looped;
    private Frame[] frames;

    public Animation(Tile[] tiles, double animationDuration) {
        this(tiles, animationDuration, false);
    }

    /**
     * Constructs an Animation where each frame
     * @param tiles
     * @param animationDuration
     * @param looped
     */
    public Animation(Tile[] tiles, double animationDuration, boolean looped) {
        double frameDelay = animationDuration / tiles.length;
        this.frames = new Frame[tiles.length];

        for (int i = 0; i < tiles.length; i++)
            frames[i] = new Frame(tiles[i], frameDelay);
        
        this.looped = looped;
    }

    public Animation(Tile[] tiles, double[] frameDelay) {
        this(tiles, frameDelay, false);
    }

    public Animation(Tile[] tiles, double[] frameDelay, boolean looped) {
        this.frames = new Frame[tiles.length];
        for (int i = 0; i < tiles.length; i++)
            frames[i] = new Frame(tiles[i], frameDelay[i]);
    }

    /**
     * @param index
     * @return The {@link Frame} at index
     * @throws IndexOutOfBoundsException
     */
    public Frame getFrame(int index) throws IndexOutOfBoundsException {
        return frames[index];
    }

    /**
     * @return the number of frames that make up this animation.
     */
    public int getFrameCount() {
        return frames.length;
    }

    /**
     * @return {@link #looped}
     */
    public boolean isLooped() {
        return looped;
    }

    /**
     * Sets the value of {@link #looped}
     * @param looped
     */
    public void setLooped(boolean looped) {
        this.looped = looped;
    }
    
}
