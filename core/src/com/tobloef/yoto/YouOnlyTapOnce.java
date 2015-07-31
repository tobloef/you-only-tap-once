package com.tobloef.yoto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class YouOnlyTapOnce extends Game {
	public SpriteBatch batch;
	public ArrayList<Level> levels;
	private float size;
	List<Integer> fontSizes;
	public Map<Integer, BitmapFont> fonts;


	@Override
	public void create () {
		batch = new SpriteBatch();
		size = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())/1080f;
		fonts = new HashMap<Integer, BitmapFont>();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.color = Color.WHITE;
		parameter.shadowColor = new Color(0,0,0,0.5f);
		fontSizes = new ArrayList<Integer>(Arrays.asList(8, 16, 24, 32, 48, 64, 96, 128, 192, 256));
		for (int fontSize : fontSizes) {
			parameter.shadowOffsetX = Math.round((size*fontSize)/30);
			parameter.shadowOffsetY = Math.round((size*fontSize)/30);
			parameter.size = Math.round(fontSize*size);
			fonts.put(fontSize, generator.generateFont(parameter));
		}
		generator.dispose();

		/*  Load Levels  */
		levels = new ArrayList<Level>();
		try {
			Scanner scanner = new Scanner(new File("Levels.txt"));
			while (scanner.hasNext()){
				String[] s = scanner.next().split(",");
				Level level = new Level(Integer.parseInt(s[0]), Math.round(Float.parseFloat(s[1])*size), Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]));
				levels.add(level);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Temporary Level Loading
		this.setScreen(new GameScreen(this, levels.get(0)));
	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose() {
		super.dispose();
	}
}
