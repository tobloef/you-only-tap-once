package com.tobloef.yoto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class YouOnlyTapOnce extends Game {
	public Preferences prefs;
	public AssetManager manager;
	public SpriteBatch batch;
	public ArrayList<Level> levels = new ArrayList<Level>();;
	public Vector2 screenSize;
	public float sizeModifier;
	public Random random = new Random();

	@Override
	public void create () {
		prefs = Gdx.app.getPreferences("Game Storage");
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
}
