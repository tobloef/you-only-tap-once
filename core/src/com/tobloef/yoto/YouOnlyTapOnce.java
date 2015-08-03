package com.tobloef.yoto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.*;

public class YouOnlyTapOnce extends Game {
	private long lastTime;
	public Preferences prefs;
	public SpriteBatch batch;
	public ArrayList<Level> levels;
	private float sizeModifier;
	List<Integer> fontSizes;
	public Map<Integer, BitmapFont> fonts;


	@Override
	public void create () {
		lastTime = System.currentTimeMillis();
		Preferences prefs = Gdx.app.getPreferences("Settings");
		batch = new SpriteBatch();
		sizeModifier = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())/1080f;

		/*  Generate Fonts  */
		fonts = new HashMap<Integer, BitmapFont>();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.color = Color.WHITE;
		parameter.shadowColor = new Color(0,0,0,0.5f);
		parameter.kerning = false;
		fontSizes = new ArrayList<Integer>(Arrays.asList(256));
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
		for (String line : levelFile.readString().split("\\n")) {
			String[] s = line.split(",");
			Level level = new Level(Integer.parseInt(s[0]), Float.parseFloat(s[1])*sizeModifier, Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]));
			levels.add(level);
		}

		//Temporary Level Loading
		this.setScreen(new GameScreen(this, levels.get(1)));
	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose() {
		super.dispose();
	}

	public void timePassed() {
		System.out.println(System.currentTimeMillis() - lastTime);
		lastTime = System.currentTimeMillis();
	}
}
