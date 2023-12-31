package com.query;

import com.util.EMath;

public class CollisionDetector {

    public static boolean intersects(Vector2 rect1Min, Vector2 rect1Size, Vector2 rect2Min, Vector2 rect2Size) {
        Vector2 rect1Max = rect1Min.add(rect1Size);
        Vector2 rect2Max = rect2Min.add(rect2Size);

        return !(rect1Max.getX() < rect2Min.getX() || rect1Max.getY() < rect2Min.getY()
                || rect2Max.getX() < rect1Min.getX() || rect2Max.getY() < rect1Min.getY());
    }

    public static boolean intersects(Vector2 circle1Center, double circle1Radius, Vector2 circle2Center,
            double circle2Radius) {
        Vector2 displacement = circle1Center.subtract(circle2Center);
        double squaredDistance = Vector2.dotProduct(displacement, displacement);
        double totalRadius = circle1Radius + circle2Radius;

        return squaredDistance <= totalRadius * totalRadius;
    }

    public static boolean intersects(Vector2 rectMin, Vector2 rectSize, Vector2 circleCenter, double radius) {
        Vector2 rectMax = rectMin.add(rectSize);
        Vector2 closestPoint = EMath.clamp(circleCenter, rectMin, rectMax);
        Vector2 displacement = circleCenter.subtract(closestPoint);
        double squaredDistance = Vector2.dotProduct(displacement, displacement);

        return squaredDistance <= radius * radius;
    }

}
