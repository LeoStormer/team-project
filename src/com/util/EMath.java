package com.util;

import com.query.Vector2;

public class EMath {
    
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static Vector2 clamp(Vector2 value, Vector2 min, Vector2 max) {
        return new Vector2(clamp(value.getX(), min.getX(), max.getX()), clamp(value.getY(), min.getY(), max.getY()));
    }

    public static double interpolate(double start, double end, double alpha) {
        return start + (end - start) * alpha;
    }

}
