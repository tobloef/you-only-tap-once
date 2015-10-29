package com.tobloef.yoto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;

import java.util.ArrayList;
import java.util.Random;

public class YouOnlyTapOnce extends Game {
    public final ArrayList<Level> levels = new ArrayList<Level>();
    public Color blue = new Color(50f / 255f, 130f / 255f, 200f / 255f, 1);
    public Color green = new Color(75f / 255f, 175f / 255f, 75f / 255f, 1);
    public Color red = new Color(190f / 255f, 60f / 255f, 60f / 255f, 1);
    public Color clear = new Color(50f / 255f, 130f / 255f, 200f / 255f, 0f);
    public Random random = new Random();
    public Preferences prefs;
    public AssetManager manager;
    public SpriteBatch batch;
    public float sizeModifier;
    GDXDialogs dialogs;

    @Override
    public void create() {
        prefs = Gdx.app.getPreferences("Game Storage");
        manager = new AssetManager();
        batch = new SpriteBatch();
        sizeModifier = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()) / 1080f;
        dialogs = GDXDialogsSystem.install();

        this.setScreen(new SplashScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    public void dispose() {
        manager.dispose();
        batch.dispose();
        super.dispose();
    }

    private float biasedRandom(float low, float high, float bias) {
        float biasedFloat = (float) Math.pow(random.nextFloat(), bias);
        return low + (high - low) * biasedFloat;
    }

    public Level randomLevel() {
        int randCount = Math.round(biasedRandom(5, 300, 2));
        float randSpeed = Math.round(biasedRandom(0, 8, 0.5f));
        float randCompletion = Math.round(biasedRandom(0.7f, 1, 0.85f));
        float avgSize = (float) Math.max((0.8f - (0.14f * Math.log(randCount))), 0.01f);
        float randSize = biasedRandom(avgSize * 0.6f, avgSize * 1.4f, 1f);
        float randMaxSize = biasedRandom(1f, 8f, (float) (4f * Math.pow(0.99f, randCount)));

        return new Level(-1, randCount, randSize * sizeModifier, randMaxSize, randSpeed, randCompletion);
    }
}
