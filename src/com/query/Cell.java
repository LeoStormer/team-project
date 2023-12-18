package com.query;

import java.util.HashSet;

public class Cell {
    private HashSet<Collider> colliders;

    public Cell() {
        colliders = new HashSet<>();
    }

    public void add(Collider c) {
        colliders.add(c);
    }

    public void remove(Collider c) {
        colliders.remove(c);
    }

    public void collect(HashSet<Collider> s) {
        s.addAll(colliders);
    }
}
