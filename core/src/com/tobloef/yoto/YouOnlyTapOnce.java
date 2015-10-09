package com.tobloef.yoto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;

import java.util.ArrayList;
import java.util.Random;

public class YouOnlyTapOnce extends Game {
    public Preferences prefs;
    public AssetManager manager;
    public SpriteBatch batch;
    public ArrayList<Level> levels = new ArrayList<Level>();
    ;
    public Vector2 screenSize;
    public float sizeModifier;
    public Random random = new Random();
    GDXDialogs dialogs;

    @Override
    public void create() {
        prefs = Gdx.app.getPreferences("Game Storage");
        prefs.putInteger("levelsAvailable", 100);
        manager = new AssetManager();
        batch = new SpriteBatch();
        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sizeModifier = Math.min(screenSize.x, screenSize.y) / 1080f;
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

    public float biasedRandom(float low, float high, float bias) {
        float biasedFloat = (float) Math.pow(random.nextFloat(), bias);
        return low + (high - low) * biasedFloat;
    }

    public Level randomLevel(boolean trueRandom) {
        float randMaxSize;
        float randSize;
        int randCount = Math.round(biasedRandom(5, 300, 2));
        float randSpeed = random.nextFloat() * (8f - 5f) + 5f;
        float randCompletion = random.nextFloat() * (1f - 0.7f) + 0.7f;
        if (trueRandom) {
            randSize = biasedRandom(0.3f, 4, 2)/10f;
            randMaxSize = random.nextFloat() * (5 - 1) + 1;
        } else {
            randSize = 0f;
            while (randSize < 0.1f) {
                randSize = (0.4f - (((randCount - 100f) * 2f) / 1000f) + (random.nextFloat() * (0.2f - -0.2f) + -0.2f)) / 2;
            }
            randMaxSize = Math.max(2, (float) Math.pow(Math.E, Math.log(1.15f) / randSize) + (random.nextFloat() * (1f - -1f) + -1f));
        }
        Level level = new Level(-1, randCount, randSize, randMaxSize, randSpeed, randCompletion);
        return level;
    }
}
