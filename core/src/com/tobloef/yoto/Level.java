package com.tobloef.yoto;

public class Level {
    public final int levelID;
    public final int count;
    public final float dotSize;
    public final float maxSize;
    public final float speed;
    public final float completionPercentage;

    public Level(int levelID, int count, float dotSize, float maxSize, float speed, float completionPercentage) {
        this.levelID = levelID;
        this.count = count;
        this.dotSize = dotSize;
        this.maxSize = maxSize * dotSize;
        this.speed = speed;
        this.completionPercentage = completionPercentage;
    }
}