package com.tobloef.yoto;

import com.badlogic.gdx.math.Vector3;

public class Dot {
    private final static int EXPANDING = 1;
    private final static int IDLE = 0;
    private final static int SHRINKING = -1;

    private Vector3 position;
    private Vector3 direction;
    private float speed;
    private float size;
    private boolean activated;
    private int state;
    private float lifetime;

    public Dot() {}
    public Dot(Vector3 position, Vector3 direction, float speed) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        size = 0.5f;
        activated = false;
        state = IDLE;
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

    public boolean getActivated() { return activated; }
    public void setActivated(boolean activated) { this.activated = activated; }

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }

    public float getLifetime() { return lifetime; }
    public void setLifetime(float lifetime) { this.lifetime = lifetime; }

}
