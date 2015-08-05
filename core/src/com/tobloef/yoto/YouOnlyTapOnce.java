package com.tobloef.yoto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class YouOnlyTapOnce extends Game {
	public AssetManager manager;
	public SpriteBatch batch;
	public ArrayList<Level> levels = new ArrayList<Level>();;
	public Vector2 screenSize;
	public float sizeModifier;
	private static long minSplashTime = 2000L;
	public Random random = new Random();

	public int levelID = -1;

	@Override
	public void create () {
		manager = new AssetManager();
		batch = new SpriteBatch();
		screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sizeModifier = Math.min(screenSize.x, screenSize.y)/1080f;

		this.setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose() {
		super.dispose();
	}

	public void loadLevel(int id) {
		if (id == -1) {
			int randCount = random.nextInt(300 - 1) + 1;
			float randSize = 0f;
			while (randSize < 0.1f) {
				randSize = (0.4f - (((randCount-100f) * 2f) / 1000f) + (random.nextFloat() * (0.2f - -0.2f) + -0.2f))/2;
			}
			float randMaxSize = Math.max(2, (float) Math.pow(Math.E, Math.log(1.15f) / randSize) + (random.nextFloat() * (1f - -1f) + -1f));
			float randSpeed = random.nextFloat() * (3f - 0.25f) + 0.25f;
			float randCompletion = random.nextFloat() * (0.99f - 0.65f) + 0.65f;
			Level level = new Level(-1, randCount, randSize, randMaxSize, randSpeed, randCompletion);
			Gdx.app.log("Level", "\nCount: " + randCount + "\nSize: " + randSize + "\nMaxSize: " + randMaxSize + "\nSpeed: " + randSpeed + "\nCompletion: " + randCompletion);
			this.setScreen(new GameScreen(this, level));
		} else {
			this.setScreen(new GameScreen(this, levels.get(id)));
		}
	}
}
