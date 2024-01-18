package com.query;

import java.util.ArrayList;

/**
 * A class that represents the collidable relationships between two
 * {@link Collider Colliders}.
 */
public class CollisionGroup {

    private static ArrayList<CollisionGroup> groups = new ArrayList<CollisionGroup>();

    private static int groupIdCounter = 0;

    private static final String formatString = "CollisionGroup(%d, %s)";

    /**
     * The default Collision Group.
     * Always has a groupId of 0.
     */
    public static final CollisionGroup DEFAULT = new CollisionGroup("Default");

    /**
     * @param groupId
     * @return The CollisionGroup that has this groupId
     */
    public static CollisionGroup getGroup(int groupId) {
        return groupId < groups.size() ? groups.get(groupId) : null;
    }

    /**
     * @param name
     * @return The CollisionGroup that has this name
     */
    public static CollisionGroup getGroup(String name) {
        for (CollisionGroup collisionGroup : groups) {
            if (collisionGroup.name.equals(name))
                return collisionGroup;
        }
        return null;
    }

    public final int groupId;

    public final String name;

    private ArrayList<Boolean> collidable;

    /**
     * Constructs a new collision group that is collidable with all groups.
     * @param name the name of the collision group
     */
    public CollisionGroup(String name) {
        this.name = name;
        this.groupId = groupIdCounter;
        groupIdCounter++;
        this.collidable = new ArrayList<Boolean>();

        // set this collidable with all groups.
        for (int i = 0; i < groupIdCounter; i++) {
            this.collidable.add(true);
        }

        // set all other groups collidable with this.
        for (CollisionGroup group : groups) {
            group.collidable.add(true);
        }

        groups.add(this);
    }

    /**
     * Set whether objects belonging to this collision group can collide with objects belonging to other.
     * @param other
     * @param canCollide
     */
    public void setCollidable(CollisionGroup other, boolean canCollide) {
        collidable.set(other.groupId, canCollide);
        other.collidable.set(groupId, canCollide);
    }

    /**
     * @param other
     * @return Whether objects belonging to this collision group can collide with objects belonging to other
     */
    public boolean canCollide(CollisionGroup other) {
        return collidable.get(other.groupId);
    }

    /**
     * @return The collision group's id
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * @return The name of the collision group
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format(formatString, groupId, name);
    }

}
