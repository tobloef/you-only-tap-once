package com.tobloef.yoto;

import com.badlogic.gdx.math.Vector2;

public class Dot {
    public Vector2 position;
    public Vector2 direction;
    public float size;
    public float maxSize;
    public boolean activated;
    public int state;
    public float lifetime = 0;
    public boolean shouldCount = false;

    //The general moving dot
    public Dot(Vector2 position, Vector2 direction, float speed, float size, float maxSize) {
        this.position = position;
        this.direction = direction;
        this.size = size;
        this.maxSize = maxSize;
        activated = false;
        state = 0;
    }

    //Used for the initial dot the player place
    public Dot(Vector2 position, float maxSize) {
        this.position = position;
        this.maxSize = maxSize;
        activated = true;
        state = 1;
        direction = new Vector2(0, 0);
    }
}
