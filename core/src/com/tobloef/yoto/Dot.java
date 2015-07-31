package com.tobloef.yoto;

import com.badlogic.gdx.math.Vector3;

public class Dot {
    public Vector3 position;
    public Vector3 direction;
    public float size;
    public float maxSize;
    public boolean activated;
    public int state;
    public float lifetime = 0;
    public boolean shouldCount = false;

    public Dot() {}
    public Dot(Vector3 position, Vector3 direction, float speed, float size, float maxSize) {
        this.position = position;
        this.direction = direction;
        this.size = size;
        this.maxSize = maxSize;
        activated = false;
        state = 0;
    }
    public Dot(Vector3 position, float maxSize) {
        this.position = position;
        this.maxSize = maxSize;
        activated = true;
        state = 1;
    }
}
