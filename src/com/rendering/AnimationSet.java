package com.rendering;

import java.util.HashMap;

public class AnimationSet {

    HashMap<String, Animation> animations;

    public AnimationSet() {
        super();
        animations = new HashMap<>();
    }

    public void addAnimation(String animationName, Animation animation) {
        animations.put(animationName, animation);
    }

    public Animation getAnimation(String animationName) {
        return animations.get(animationName);
    }

}
