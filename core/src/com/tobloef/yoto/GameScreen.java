package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen, InputProcessor {
    final YouOnlyTapOnce game;
    private OrthographicCamera camera;
    private Texture dotImage;
    private Texture shadowImage;
    private Color c;
    private Array<Dot> dots = new Array<Dot>();

    /*  Colors  */
    private Color blue = new Color(60/255f, 143/255f, 215/255f, 1);
    private Color green = new Color(111/255f, 183/255f, 97/255f, 1);

    public GameScreen(final YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        Gdx.input.setInputProcessor(this);
        dotImage = new Texture(Gdx.files.internal("dot_white.png"));
        shadowImage = new Texture(Gdx.files.internal("dot_black.png"));
        c = game.batch.getColor();
        dots.add(new Dot());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(blue.r, blue.g, blue.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.setColor(c.r, c.g, c.b, 0.5f);
        for (Dot dot: dots) {
            game.batch.draw(shadowImage, ((camera.viewportWidth * 0.5f) - shadowImage.getWidth() / 2) + (shadowImage.getWidth() * 0.1f), ((camera.viewportHeight / 2) - shadowImage.getWidth() / 2) - (shadowImage.getHeight() * 0.1f));
        }
        game.batch.setColor(c);
        for (Dot dot: dots) {
            game.batch.draw(dotImage, (camera.viewportWidth * 0.5f) - dotImage.getWidth() / 2, (camera.viewportHeight / 2) - dotImage.getWidth() / 2);
        }
        game.batch.end();
     }

    public void spawnDot() {

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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return true;
    }
}
