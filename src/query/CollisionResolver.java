package query;

import util.EMath;

public class CollisionResolver {
    public static Vector2[] resolveCollision(Vector2 rect1Min, Vector2 rect1Size, boolean rect1Anchored,
            Vector2 rect2Min, Vector2 rect2Size, boolean rect2Anchored) {
        Vector2 rect1Max = rect1Min.add(rect1Size);
        Vector2 rect2Max = rect2Min.add(rect2Size);

        Vector2 separation1 = rect1Max.subtract(rect2Min);
        Vector2 separation2 = rect2Max.subtract(rect1Min);

        double depthX = Math.min(separation1.getX(), separation2.getX());
        double depthY = Math.min(separation1.getY(), separation2.getY());
        double depth = Math.min(depthX, depthY);

        Vector2 normal = depthY < depthX ? Vector2.YAXIS : Vector2.XAXIS;
        Vector2 displacement = rect2Min.subtract(rect1Min);
        normal = Vector2.dotProduct(displacement, normal) > 0 ? normal.scale(-1) : normal;
        double rect1ResolutionFactor = rect1Anchored ? 0.0d : rect2Anchored ? 1.0d : 0.5d;
        double rect2ResolutionFactor = rect2Anchored ? 0.0d : rect1Anchored ? 1.0d : 0.5d;
        
        Vector2[] positions = { rect1Min.add(normal.scale(depth * rect1ResolutionFactor)),
                rect2Min.add(normal.scale(-depth * rect2ResolutionFactor)) };

        return positions;
    }

    public static Vector2[] resolveCollision(Vector2 circle1Min, double circle1Radius, boolean circle1Anchored,
            Vector2 circle2Min, double circle2Radius, boolean circle2Anchored) {
        Vector2 circle1Center = circle1Min.add(circle1Radius, circle1Radius);
        Vector2 circle2Center = circle2Min.add(circle2Radius, circle2Radius);
        Vector2 displacement = circle1Center.subtract(circle2Center);
        Vector2 normal = displacement.getNormal();
        double radialSum = circle1Radius + circle2Radius;
        double collisionDepth = radialSum - displacement.getMagnitude();
        double circle1ResolutionFactor = circle1Anchored ? 0.0d : circle2Anchored ? 1.0d : 0.5d;
        double circle2ResolutionFactor = circle2Anchored ? 0.0d : circle1Anchored ? 1.0d : 0.5d;

        Vector2[] positions = { circle1Min.add(normal.scale(collisionDepth * circle1ResolutionFactor)),
                circle2Min.add(normal.scale(-collisionDepth * circle2ResolutionFactor)) };

        return positions;
    }

    public static Vector2[] resolveCollision(Vector2 rectMin, Vector2 rectSize, boolean rectAnchored,
            Vector2 circleMin, double radius, boolean circleAnchored) {
        Vector2 rectHalfSize = rectSize.scale(0.5d);
        Vector2 rectCenter = rectMin.add(rectHalfSize);
        Vector2 rectMax = rectMin.add(rectSize);
        Vector2 circleCenter = circleMin.add(radius, radius);
        Vector2 closestPoint = EMath.clamp(circleCenter, rectMin, rectMax);
        Vector2 axis = closestPoint.subtract(circleCenter);
        double distance = axis.getMagnitude();
        Vector2 normal;
        double depth;

        if (distance == 0) {// Circle center is inside of rect :(
            Vector2 displacement = rectCenter.subtract(circleCenter);
            double displacementX = displacement.getX();
            double displacementY = displacement.getY();
            double depthX = radius + rectHalfSize.getX() - Math.abs(displacementX);
            double depthY = radius + rectHalfSize.getY() - Math.abs(displacementY);
            normal = Math.abs(displacementY) >= Math.abs(displacementX) ? new Vector2(0, displacementY < 0 ? -1 : 1)
                    : new Vector2(displacementX < 0 ? -1 : 1, 0);
            depth = Math.min(depthX, depthY);
        } else {
            normal = axis.divide(distance);
            depth = radius - distance;
        }

        double rectResolutionFactor = rectAnchored ? 0.0d : circleAnchored ? 1.0d : 0.5d;
        double circleResolutionFactor = circleAnchored ? 0.0d : rectAnchored ? 1.0d : 0.5d;

        Vector2[] positions = { rectMin.add(normal.scale(depth * rectResolutionFactor)),
                circleMin.add(normal.scale(-depth * circleResolutionFactor)) };

        return positions;
    }
}
