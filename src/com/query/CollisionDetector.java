package com.query;

import com.util.EMath;

/**
 * A helper class that provides methods to detect intersections between
 * {@link Collider colliders}.
 */
public class CollisionDetector {

    /**
     * @param rect1Min  top left corner of rect 1
     * @param rect1Size size of rect 1
     * @param rect2Min  top left corner of rect 2
     * @param rect2Size size of rect 2
     * @return Whether two rects are intersecting
     */
    public static boolean intersects(Vector2 rect1Min, Vector2 rect1Size, Vector2 rect2Min, Vector2 rect2Size) {
        Vector2 rect1Max = rect1Min.add(rect1Size);
        Vector2 rect2Max = rect2Min.add(rect2Size);

        return !(rect1Max.getX() < rect2Min.getX() || rect1Max.getY() < rect2Min.getY()
                || rect2Max.getX() < rect1Min.getX() || rect2Max.getY() < rect1Min.getY());
    }

    /**
     * @param circle1Center center of circle 1
     * @param circle1Radius radius of circle 1
     * @param circle2Center center of circle 2
     * @param circle2Radius radius of circle 2
     * @return Whether two circles are intersecting
     */
    public static boolean intersects(Vector2 circle1Center, double circle1Radius, Vector2 circle2Center,
            double circle2Radius) {
        Vector2 displacement = circle1Center.subtract(circle2Center);
        double squaredDistance = Vector2.dotProduct(displacement, displacement);
        double totalRadius = circle1Radius + circle2Radius;

        return squaredDistance <= totalRadius * totalRadius;
    }

    /**
     * @param rectMin      top left corner of rectangle
     * @param rectSize     size of rectangle
     * @param circleCenter center of circle
     * @param radius       radius of circle
     * @return Whether a rectangle is intersecting with a circle
     */
    public static boolean intersects(Vector2 rectMin, Vector2 rectSize, Vector2 circleCenter, double radius) {
        Vector2 rectMax = rectMin.add(rectSize);
        Vector2 closestPoint = EMath.clamp(circleCenter, rectMin, rectMax);
        Vector2 displacement = circleCenter.subtract(closestPoint);
        double squaredDistance = Vector2.dotProduct(displacement, displacement);

        return squaredDistance <= radius * radius;
    }

}
