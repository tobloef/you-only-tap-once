package com.tobloef.yoto;

public class Level {
    public int count;
    public float size;
    public float maxSize;
    public float speed;
    public float completionPercentage;

    public Level(int count, float size, float maxSize, float speed, float completionPercentage) {
        this.count = count;
        this.size = size;
        this.maxSize = maxSize*size;
        this.speed = speed;
        this.completionPercentage = completionPercentage;
    }
}