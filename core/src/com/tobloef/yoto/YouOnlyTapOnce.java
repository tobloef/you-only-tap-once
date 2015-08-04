package com.tobloef.yoto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;

import java.util.*;

public class YouOnlyTapOnce extends Game {
	public Preferences prefs;
	public SpriteBatch batch;
	public ArrayList<Level> levels;
	private float sizeModifier;
	List<Integer> fontSizes;
	public Map<Integer, BitmapFont> fonts;
	private static long minSplashTime = 2000L;
	private Random rand = new Random();

	private int levelID = 0;

	@Override
	public void create () {
		Preferences prefs = Gdx.app.getPreferences("Settings");
		batch = new SpriteBatch();
		sizeModifier = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())/1080f;

		this.setScreen(new SplashScreen(this));

		final long startTime = System.currentTimeMillis();
		new Thread(new Runnable() {
			@Override public void run() {
				Gdx.app.postRunnable(new Runnable() {
					@Override public void run() {

						/*  Generate Fonts  */
						fonts = new HashMap<Integer, BitmapFont>();
						FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
						FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
						parameter.color = Color.WHITE;
						parameter.shadowColor = new Color(0,0,0,0.5f);
						parameter.kerning = false;
						fontSizes = new ArrayList<Integer>(Arrays.asList(64, 128, 256));
						for (int fontSize : fontSizes) {
							parameter.shadowOffsetX = Math.round((sizeModifier*fontSize)/30);
							parameter.shadowOffsetY = Math.round((sizeModifier*fontSize)/30);
							parameter.size = Math.round(fontSize*sizeModifier);
							fonts.put(fontSize, generator.generateFont(parameter));
						}
						generator.dispose();

						/*  Load Levels  */
						levels = new ArrayList<Level>();
						FileHandle levelFile = Gdx.files.internal("Levels.txt");
						int id = 0;
						for (String line : levelFile.readString().split("\\n")) {
							String[] s = line.split(",");
							Level level = new Level(id++, Integer.parseInt(s[0]), Float.parseFloat(s[1])*sizeModifier, Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]));
							levels.add(level);
						}
						long splash_elapsed_time = System.currentTimeMillis() - startTime;
						if (splash_elapsed_time < minSplashTime) {
							Timer.schedule(
									new Timer.Task() {
										@Override
										public void run() {
											loadLevel(levelID);
										}
									}, (float) (YouOnlyTapOnce.minSplashTime - splash_elapsed_time) / 1000f);
						} else {
							loadLevel(levelID);
						}
					}
				});
			}
		}).start();
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
			int randCount = rand.nextInt(500 - 1) + 1;
			float randSize = rand.nextFloat() * (2f - 0.1f) + 0.1f;
			float randMaxSize = rand.nextFloat() * (5f - 1f) + 1f;
			float randSpeed = rand.nextFloat() * (5f - 0.1f) + 0.1f;
			float randCompletion = rand.nextFloat();
			Level level = new Level(-1, randCount, randSize, randMaxSize, randSpeed, randCompletion);
			this.setScreen(new GameScreen(this, level));
		} else {
			this.setScreen(new GameScreen(this, levels.get(id)));
		}
	}
}
