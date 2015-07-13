package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen, InputProcessor {
    final YouOnlyTapOnce game;
    private OrthographicCamera camera;
    private int segments;
    private int size = 64;
    private Array<Texture> textures = new Array<Texture>();

    public GameScreen(final YouOnlyTapOnce game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        Gdx.input.setInputProcessor(this);
        segments = Math.max(3, (int) (10 * (float) Math.sqrt(size)));

        Long t1 = TimeUtils.nanoTime();
        textures.add(new Texture(Gdx.files.internal("dot_black.png")));
        textures.add(new Texture(Gdx.files.internal("dot_gray_10.png")));
        textures.add(new Texture(Gdx.files.internal("dot_gray_20.png")));
        textures.add(new Texture(Gdx.files.internal("dot_gray_30.png")));
        textures.add(new Texture(Gdx.files.internal("dot_gray_40.png")));
        textures.add(new Texture(Gdx.files.internal("dot_gray_50.png")));
        textures.add(new Texture(Gdx.files.internal("dot_gray_60.png")));
        textures.add(new Texture(Gdx.files.internal("dot_gray_70.png")));
        textures.add(new Texture(Gdx.files.internal("dot_gray_80.png")));
        textures.add(new Texture(Gdx.files.internal("dot_gray_90.png")));
        textures.add(new Texture(Gdx.files.internal("dot_white.png")));

        textures.add(new Texture(Gdx.files.internal("dot_blue_dark.png")));
        textures.add(new Texture(Gdx.files.internal("dot_blue_light.png")));
        textures.add(new Texture(Gdx.files.internal("dot_green.png")));
        textures.add(new Texture(Gdx.files.internal("dot_orange.png")));
        textures.add(new Texture(Gdx.files.internal("dot_purple.png")));
        textures.add(new Texture(Gdx.files.internal("dot_red.png")));
        textures.add(new Texture(Gdx.files.internal("dot_yellow.png")));

        System.out.println(TimeUtils.nanosToMillis(TimeUtils.nanoTime() - t1)/1000f);
    }

    @Override
    public void show() {
        spawnDot();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.shapeRenderer.setProjectionMatrix(camera.combined);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0, 0, 0, 1);
        game.shapeRenderer.circle(camera.viewportWidth * 0.6f, camera.viewportHeight / 2, size, segments);
        game.shapeRenderer.end();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(textures.first(), (camera.viewportWidth * 0.4f)-(textures.first().getWidth() / 8)/2, (camera.viewportHeight / 2)-(textures.first().getWidth() / 8)/2, textures.first().getWidth() / 8, textures.first().getHeight() / 8);
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
