package com.tobloef.yoto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class YouOnlyTapOnce extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public ShapeRenderer shapeRenderer;
	public ArrayList<Level> levels;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();

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
		batch.dispose();
		font.dispose();
		super.dispose();
	}
}
