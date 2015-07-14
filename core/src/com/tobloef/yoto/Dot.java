package com.tobloef.yoto;

import com.badlogic.gdx.math.Vector3;

public class Dot {
    private final static int EXPANDING = 1;
    private final static int IDLE = 0;
    private final static int SHRINKING = -1;

    public Vector3 position;
    public Vector3 direction;
    public float speed;
    public float size;
    public boolean actived;
    public int state;
    public float lifetime;

    public Dot() {}
    public Dot(Vector3 position, Vector3 direction, float speed) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        size = 1;
        actived = false;
        state = IDLE;
        lifetime = 0;
    }
}
