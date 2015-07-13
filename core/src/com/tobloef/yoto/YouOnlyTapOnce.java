package com.tobloef.yoto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class YouOnlyTapOnce extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		super.dispose();
	}
}
