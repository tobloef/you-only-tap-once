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
    private Vector2 screenSize;
    private boolean haveBegun = false;
    private boolean shouldEnd = false;
    private boolean completed = false;

    /*  Colors  */
    private Color background;
    private Color blue = new Color(60/255f, 143/255f, 215/255f, 1);
    private Color green = new Color(100/255f, 204/255f, 80/255f, 1);

    public GameScreen(final YouOnlyTapOnce game) {
        this.game = game;
        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenSize.x, screenSize.y);
        Gdx.input.setInputProcessor(this);
        dotTexture = new Texture(Gdx.files.internal("dot_white.png"));
        shadowTexture = new Texture(Gdx.files.internal("dot_black.png"));
        dotTextureSize = dotTexture.getWidth();
        background = blue;
        c = game.batch.getColor();

        for (int i = 0; i < 250; i++) {
            dots.add(new Dot(new Vector3(random.nextFloat()*(screenSize.x-200)+100, random.nextFloat()*(screenSize.y-200)+100, 0), new Vector3(random.nextFloat() * 2f - 1f, random.nextFloat() * 2f - 1f, 0).nor(), 5f));
        }
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
            game.batch.draw(shadowTexture, (dot.getPosition().x - dotTextureSize*dot.getSize() / 2) + (dotTextureSize * dot.getSize() * 0.1f), (dot.getPosition().y - dotTextureSize * dot.getSize() / 2) - (dotTextureSize * 0.1f), dotTextureSize*dot.getSize(), dotTextureSize*dot.getSize());
        }
        game.batch.setColor(c);
        for (Dot dot: dots) {
            game.batch.draw(dotTexture, dot.getPosition().x - dotTextureSize*dot.getSize() / 2, dot.getPosition().y - dotTextureSize*dot.getSize() / 2, dotTextureSize*dot.getSize(), dotTextureSize*dot.getSize());
        }
        game.batch.end();

        /*  Calculate Movement  */
        for (Dot dot: dots) {
            if (!dot.isActivated()) {
                Vector3 position = dot.getPosition();
                position.x += Math.min(Math.max(dot.getDirection().x * Gdx.graphics.getDeltaTime() * 100, -1), 1) * dot.getSpeed();
                position.y += Math.min(Math.max(dot.getDirection().y * Gdx.graphics.getDeltaTime() * 100, -1), 1) * dot.getSpeed();
                dot.setPosition(position);

                /*  Physics  */
                if (dot.getPosition().x <= 0 + (dotTextureSize * dot.getSize() / 2) || dot.getPosition().x + (dotTextureSize * dot.getSize() / 2) >= screenSize.x) {
                    Vector3 direction = dot.getDirection();
                    direction.x = -direction.x;
                    dot.setDirection(direction);
                }
                if (dot.getPosition().y <= 0 + (dotTextureSize * dot.getSize() / 2) || dot.getPosition().y + (dotTextureSize * dot.getSize() / 2) >= screenSize.y) {
                    Vector3 direction = dot.getDirection();
                    direction.y = -direction.y;
                    dot.setDirection(direction);
                }
            } else {

                /*  Dot Collision  */
                for (int i = 0; i < dots.size; i++) {
                    if (!dots.get(i).isActivated() && dots.get(i) != dot) {
                        if (dot.getPosition().dst(dots.get(i).getPosition()) < ((dotTextureSize * dot.getSize()) / 2) + ((dotTextureSize *  dots.get(i).getSize()) / 2)) {
                            dots.get(i).activate();
                        }
                    }
                }
            }

            /*  Expand/Shrink  */
            if (dot.getState() != 0) {
                dot.setSize(dot.getSize() + dot.getState() * Gdx.graphics.getDeltaTime() * 0.9f);
                if (dot.getSize() < 0) {
                    dots.removeValue(dot, true);
                }
                if (dot.getSize() > dot.getMaxSize()) {
                    dot.setState(0);
                    if (!dot.getShouldCount()) {
                        dot.setShouldCount(true);
                    }
                }
            }
            if (dot.getShouldCount()) {
                dot.setLifetime(dot.getLifetime() + Gdx.graphics.getDeltaTime());
                if (dot.getLifetime() > 1.5f) {
                    dot.setShouldCount(false);
                    dot.setState(-1);
                }
            }
        }

        /*  Level Completion  */
        if (dots.size <= 100*(1-0.9f) && (!shouldEnd || completed)) {
            completed = true;
            if (background != green) {
                background.lerp(green, Gdx.graphics.getDeltaTime() * 3f);
            }
        }
        if (haveBegun && !shouldEnd) {
            shouldEnd = true;
            for (int i = 0; i < dots.size; i++) {
                if (dots.get(i).isActivated()) {
                    shouldEnd = false;
                    break;
                }
            }
            if (shouldEnd) {
                for (int i = 0; i < dots.size; i++) {
                    dots.get(i).setState(-1);
                }
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
        Vector3 mousePos = new Vector3(screenX, screenY, 0);
        camera.unproject(mousePos);
        dots.add(new Dot(mousePos, true));
        haveBegun = true;
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
