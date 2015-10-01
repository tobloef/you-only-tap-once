package com.tobloef.yoto;

public class Level {
    public int levelID;
    public int count;
    public float dotSize;
    public float maxSize;
    public float speed;
    public float completionPercentage;

    public Level(int levelID, int count, float dotSize, float maxSize, float speed, float completionPercentage) {
        this.levelID = levelID;
        this.count = count;
        this.dotSize = dotSize;
        this.maxSize = maxSize * dotSize;
        this.speed = speed;
        this.completionPercentage = completionPercentage;
    }
}