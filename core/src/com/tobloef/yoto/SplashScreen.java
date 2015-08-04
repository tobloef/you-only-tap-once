package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class SplashScreen implements Screen {
    YouOnlyTapOnce game;
    private Texture splashTexture;
    private float sizeModifier;
    private Vector2 screenSize;
    private OrthographicCamera camera;

    private Color blue = new Color(60/255f, 145/255f, 215/255f, 1);

    public SplashScreen(final YouOnlyTapOnce game) {
        splashTexture = new Texture(Gdx.files.internal("splash_logo.png"));
        splashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.game = game;
    }

    @Override
    public void show() {
        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sizeModifier = Math.min(screenSize.x, screenSize.y)/1080f;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenSize.x, screenSize.y);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(blue.r, blue.g, blue.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        //game.batch.draw(splashTexture, Gdx.graphics.getWidth() / 2 - ((splashTexture.getWidth() * sizeModifier) / 2), Gdx.graphics.getHeight() / 2 - ((splashTexture.getHeight() * sizeModifier) / 2), splashTexture.getHeight() * sizeModifier, splashTexture.getWidth() * sizeModifier);
        game.batch.draw(splashTexture, screenSize.x/2 - (splashTexture.getWidth()*sizeModifier / 2), screenSize.y/2 - (splashTexture.getHeight()*sizeModifier / 2), splashTexture.getWidth()*sizeModifier, splashTexture.getHeight()*sizeModifier);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
