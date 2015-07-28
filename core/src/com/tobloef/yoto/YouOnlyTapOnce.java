package com.tobloef.yoto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class YouOnlyTapOnce extends Game {
	public SpriteBatch batch;
	public ArrayList<Level> levels;
	float size;
	public BitmapFont font32;
	public BitmapFont font64;
	public BitmapFont font128;
	public BitmapFont font256;


	@Override
	public void create () {
		batch = new SpriteBatch();
		size = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())/1080;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		int fontSize;
		parameter.color = Color.WHITE;
		parameter.shadowColor = new Color(0,0,0,0.5f);
		fontSize = 32;
		parameter.shadowOffsetX = Math.round((size*fontSize)/20);
		parameter.shadowOffsetY = Math.round((size*fontSize)/20);
		parameter.size = Math.round(fontSize*size);
		font32 = generator.generateFont(parameter);
		fontSize = 64;
		parameter.shadowOffsetX = Math.round((size*fontSize)/20);
		parameter.shadowOffsetY = Math.round((size*fontSize)/20);
		parameter.size = Math.round(fontSize*size);
		font64 = generator.generateFont(parameter);
		fontSize = 128;
		parameter.shadowOffsetX = Math.round((size*fontSize)/20);
		parameter.shadowOffsetY = Math.round((size*fontSize)/20);
		parameter.size = Math.round(fontSize*size);
		font128 = generator.generateFont(parameter);
		fontSize = 256;
		parameter.shadowOffsetX = Math.round((size*fontSize)/20);
		parameter.shadowOffsetY = Math.round((size*fontSize)/20);
		parameter.size = Math.round(fontSize*size);
		font256 = generator.generateFont(parameter);
		generator.dispose();

		/*  Load Levels  */
		levels = new ArrayList<Level>();
		try {
			Scanner scanner = new Scanner(new File("Levels.txt"));
			while (scanner.hasNext()){
				String[] s = scanner.next().split(",");
				Level level = new Level(Integer.parseInt(s[0]), Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]));
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
