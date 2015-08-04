package com.tobloef.yoto;

public class Level {
    public int levelID;
    public int count;
    public float size;
    public float maxSize;
    public float speed;
    public float completionPercentage;

    public Level(int levelID, int count, float size, float maxSize, float speed, float completionPercentage) {
        this.levelID = levelID;
        this.count = count;
        this.size = size;
        this.maxSize = maxSize*size;
        this.speed = speed;
        this.completionPercentage = completionPercentage;
    }
}