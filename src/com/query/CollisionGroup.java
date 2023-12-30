package com.query;

import java.util.ArrayList;

// Allows colliders to be selectively collidable.
public class CollisionGroup {

    private static ArrayList<CollisionGroup> groups = new ArrayList<CollisionGroup>();
    private static int groupIdCounter = 0;
    public static final CollisionGroup DEFAULT = new CollisionGroup("Default");

    public final int groupId;
    public String name;
    private ArrayList<Boolean> collidable;

    public CollisionGroup(String name) {
        this.name = name;
        this.groupId = groupIdCounter;
        groupIdCounter++;
        this.collidable = new ArrayList<Boolean>();

        // set this collidable with all groups including this.
        for (int i = 0; i < groupIdCounter; i++) {
            this.collidable.add(true);
        }

        // set all other groups collidable with this.
        for (CollisionGroup group : groups) {
            group.collidable.add(true);
        }

        groups.add(this);
    }

    public void setCollidable(CollisionGroup collisionGroup, boolean canCollide) {
        collidable.set(collisionGroup.groupId, canCollide);
        collisionGroup.collidable.set(groupId, canCollide);
    }

    public boolean canCollide(CollisionGroup collisionGroup) {
        return collidable.get(collisionGroup.groupId);
    }

}
