package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class GameScreen implements Screen, InputProcessor {
    final YouOnlyTapOnce game;
    private OrthographicCamera camera;
    private Texture dotTexture;
    private Texture shadowTexture;
    private int dotTextureSize;
    private Color c;
    private Array<Dot> dots = new Array<Dot>();
    private Random random = new Random();
    private int count = 0;
    private Vector2 screenSize;

    /*  Colors  */
    private Color blue = new Color(60/255f, 143/255f, 215/255f, 1);
    private Color green = new Color(111/255f, 183/255f, 97/255f, 1);

    public GameScreen(final YouOnlyTapOnce game) {
        this.game = game;
        screenSize = new Vector2(1920, 1080);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenSize.x, screenSize.y);
        Gdx.input.setInputProcessor(this);
        dotTexture = new Texture(Gdx.files.internal("dot_white.png"));
        shadowTexture = new Texture(Gdx.files.internal("dot_black.png"));
        dotTextureSize = dotTexture.getWidth();
        c = game.batch.getColor();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(blue.r, blue.g, blue.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        /*  Render Sprites  */
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.setColor(c.r, c.g, c.b, 0.5f);
        for (Dot dot: dots) {
            game.batch.draw(shadowTexture, (dot.getPosition().x - dotTextureSize*dot.getSize() / 2) + (shadowTexture.getWidth() * dot.getSize() * 0.1f), (dot.getPosition().y - shadowTexture.getWidth() * dot.getSize() / 2) - (shadowTexture.getHeight() * 0.1f), dotTextureSize*dot.getSize(), dotTextureSize*dot.getSize());
        }
        game.batch.setColor(c);
        for (Dot dot: dots) {
            game.batch.draw(dotTexture, dot.getPosition().x - dotTextureSize*dot.getSize() / 2, dot.getPosition().y - dotTextureSize*dot.getSize() / 2, dotTextureSize*dot.getSize(), dotTextureSize*dot.getSize());
        }
        game.batch.end();

        /*  Calculate Movement  */
        for (Dot dot: dots) {
            Vector3 position = dot.getPosition();
            position.mulAdd(dot.getDirection(), dot.getSpeed());
            dot.setPosition(position);

            if (dot.getPosition().x < 0+(dotTextureSize*dot.getSize()/2) || dot.getPosition().x+(dotTextureSize*dot.getSize()/2) > screenSize.x) {
                Vector3 direction = dot.getDirection();
                direction.x = -direction.x;
                dot.setDirection(direction);
            }
            if (dot.getPosition().y < 0+(dotTextureSize*dot.getSize()/2) || dot.getPosition().y+(dotTextureSize*dot.getSize()/2) > screenSize.y) {
                Vector3 direction = dot.getDirection();
                direction.y = -direction.y;
                dot.setDirection(direction);
            }
        }
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
        for (int i = 0; i < 100; i++) {
            dots.add(new Dot(new Vector3(screenSize.x/2, screenSize.y/2, 0), new Vector3(random.nextFloat() * 2f - 1f, random.nextFloat() * 2f - 1f, 0).nor(), 3f));
        }
        return true;
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
        return false;
    }
}
