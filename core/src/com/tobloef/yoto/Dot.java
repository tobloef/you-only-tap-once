package com.tobloef.yoto;

import com.badlogic.gdx.math.Vector3;

public class Dot {
    private Vector3 position;
    private Vector3 direction;
    private float speed;
    private float size;
    private float maxSize;
    private boolean activated;
    private int state;
    private boolean shouldCount;
    private float lifetime;

    public Dot() {}
    public Dot(Vector3 position, Vector3 direction, float speed) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        size = 0.2f;
        maxSize = size*2f;
        activated = false;
        state = 0;
        shouldCount = false;
        lifetime = 0;
    }
    public Dot(Vector3 position, boolean activated) {
        this.position = position;
        size = 0.2f;
        maxSize = size*2f;
        size = 0f;
        this.activated = activated;
        state = 1;
        shouldCount = false;
        lifetime = 0;
    }

    public Vector3 getPosition() { return position; }
    public void setPosition(Vector3 position) { this.position = position; }

    public Vector3 getDirection() { return direction; }
    public void setDirection(Vector3 direction) { this.direction = direction; }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }

    public float getSize() { return size; }
    public void setSize(float size) { this.size = size; }

    public float getMaxSize() { return maxSize; }

    public boolean isActivated() { return activated; }
    public void activate() {
        activated = true;
        state = 1;
    }

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }

    public boolean getShouldCount() { return shouldCount; }
    public void setShouldCount(boolean shouldCount) { this.shouldCount = shouldCount; }

    public float getLifetime() { return lifetime; }
    public void setLifetime(float lifetime) { this.lifetime = lifetime; }

}
