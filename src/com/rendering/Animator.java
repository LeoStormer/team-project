package com.rendering;

import com.rendering.Animation.Frame;

/**
 * A class that handles the playback of {@link Animation Animations}
 */
public class Animator {

    private Animation animation;

    private double accumulator;

    private int currentFrameIndex;

    private int numFrames;

    /**
     * Whether the animator is currently playing
     */
    public boolean playing;

    public Animator() {
        accumulator = 0;
        currentFrameIndex = 0;
        numFrames = 0;
        playing = false;
    }

    /**
     * Loads the input animation.
     * 
     * @param animation
     */
    public void load(Animation animation) {
        if (this.animation == animation && this.animation.looped) {
            return;
        }

        this.animation = animation;
        accumulator = 0;
        currentFrameIndex = 0;
        numFrames = animation.getFrameCount();
    }

    /**
     * Loads an {@link Animation} then plays it.
     * 
     * @param animation
     */
    public void play(Animation animation) {
        load(animation);
        playing = true;
    }

    /**
     * Starts playback of an {@link Animation} if one is loaeded.
     */
    public void play() {
        playing = animation != null;
    }

    /**
     * Stops playback of an {@link Animation}.
     */
    public void pause() {
        playing = false;
    }

    /**
     * Moves along the timeline of the loaded animation. Looping to the start if possible.
     * 
     * @param deltaTime the increment to move by
     */
    public void update(double deltaTime) {
        if (!playing || animation == null)
            return;

        Frame currentFrame = animation.getFrame(currentFrameIndex);
        accumulator += deltaTime;

        if (accumulator < currentFrame.delay) {
            return;
        } else if (!animation.looped && currentFrameIndex == numFrames - 1) {
            pause();
            return;
        }

        accumulator -= currentFrame.delay;
        currentFrameIndex = (currentFrameIndex + 1) % numFrames;
    }

    /**
     * @return The currently loaded animation
     */
    public Animation getAnimation() {
        return animation;
    }

    /**
     * @return The current frame of the loaeded animation.
     */
    public Tile getCurrentFrame() {
        return animation.getFrame(currentFrameIndex).tile;
    }

    /**
     * @return whether the animator is currently playing
     */
    public boolean isPlaying() {
        return playing;
    }

}
