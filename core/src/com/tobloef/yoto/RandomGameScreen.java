package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class RandomGameScreen implements Screen, InputProcessor {
    final YouOnlyTapOnce game;
    private OrthographicCamera camera;
    private Color blue = new Color(50f / 255f, 130f / 255f, 200f / 255f, 1);
    private Texture dotTexture;
    private Texture shadowTexture;
    private Array<Dot> dots = new Array<Dot>();
    private boolean hasEnded;
    private boolean shouldEnd;
    private int score;
    private int scoreGoal;
    private Level level;

    private float games;
    private float wins;
    private FileHandle levelFile;

    public RandomGameScreen(final YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        levelFile = Gdx.files.local("Generated_Levels.txt");
        games = 0;
        wins = 0;
        score = 0;
        hasEnded = false;
        shouldEnd = false;
        level = game.randomLevel(true);
        game.screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.screenSize.x, game.screenSize.y);
        scoreGoal = Math.round(level.count * level.completionPercentage);

        dotTexture = game.manager.get("dot_white.png", Texture.class);
        shadowTexture = game.manager.get("dot_shadow.png", Texture.class);

        Gdx.input.setCatchBackKey(true);

        /*  Spawn the dots  */
        for (int i = 0; i < level.count; i++) {
            dots.add(new Dot(new Vector2(game.random.nextFloat() * (game.screenSize.x - ((dotTexture.getWidth() * level.dotSize / 2) * 2)) + (dotTexture.getWidth() * level.dotSize / 2),
                    game.random.nextFloat() * (game.screenSize.y - ((dotTexture.getWidth() * level.dotSize / 2) * 2)) + (dotTexture.getWidth() * level.dotSize / 2)),
                    new Vector2(game.random.nextFloat() * 2f - 1f, game.random.nextFloat() * 2f - 1f).nor(), level.speed, level.dotSize, level.maxSize));
        }
        dots.add(new Dot(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2),level.maxSize));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(blue.r, blue.g, blue.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*  Render Sprites  */
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        for (Dot dot : dots) {
            game.batch.draw(shadowTexture, (dot.position.x - dotTexture.getWidth() * dot.size / 2) + (dotTexture.getWidth() * dot.size * 0.2f * level.dotSize), (dot.position.y - dotTexture.getWidth() * dot.size / 2) - (dotTexture.getWidth() * 0.2f * level.dotSize), dotTexture.getWidth() * dot.size, dotTexture.getWidth() * dot.size);
        }
        for (Dot dot : dots) {
            game.batch.draw(dotTexture, dot.position.x - dotTexture.getWidth() * dot.size / 2, dot.position.y - dotTexture.getWidth() * dot.size / 2, dotTexture.getWidth() * dot.size, dotTexture.getWidth() * dot.size);
        }
        game.batch.end();

            /*  Calculate Movement  */
            for (Dot dot : dots) {
                if (!dot.activated) {
                    Vector2 position = dot.position;
                    position.x += Math.min(Math.max(dot.direction.x * Gdx.graphics.getDeltaTime() * 100, -1), 1) * level.speed * game.sizeModifier;
                    position.y += Math.min(Math.max(dot.direction.y * Gdx.graphics.getDeltaTime() * 100, -1), 1) * level.speed * game.sizeModifier;
                    dot.position = position;

                    /*  Physics  */
                    if (dot.position.x <= 0 + (dotTexture.getWidth() * dot.size / 2) || dot.position.x + (dotTexture.getWidth() * dot.size / 2) >= game.screenSize.x) {
                        Vector2 direction = dot.direction;
                        direction.x = -direction.x;
                        dot.direction = direction;
                    }
                    if (dot.position.y <= 0 + (dotTexture.getWidth() * dot.size / 2) || dot.position.y + (dotTexture.getWidth() * dot.size / 2) >= game.screenSize.y) {
                        Vector2 direction = dot.direction;
                        direction.y = -direction.y;
                        dot.direction = direction;
                    }
                } else {

                    /*  Dot Collision  */
                    for (int i = 0; i < dots.size; i++) {
                        if (!dots.get(i).activated && dots.get(i) != dot) {
                            if (dot.position.dst(dots.get(i).position) < ((dotTexture.getWidth() * dot.size) / 2) + ((dotTexture.getWidth() * dots.get(i).size) / 2)) {
                                dots.get(i).activated = true;
                                dots.get(i).state = 1;
                                score += 1;
                            }
                        }
                    }
                }

                /*  Expand/Shrink  */
                if (dot.state != 0) {
                    dot.size += dot.state * Gdx.graphics.getDeltaTime() * 0.5f;
                    if (dot.size < 0) {
                        dots.removeValue(dot, true);
                    }
                    if (dot.size > dot.maxSize) {
                        dot.state = 0;
                        if (!dot.shouldCount) {
                            dot.shouldCount = true;
                        }
                    }
                }
                if (dot.shouldCount) {
                    dot.lifetime += Gdx.graphics.getDeltaTime();
                    if (dot.lifetime > 1.5f) {
                        dot.shouldCount = false;
                        dot.state = -1;
                    }
                }
            }

            /*  Level Completion  */
            if (!hasEnded) {
                if (!shouldEnd) {
                    if (score >= scoreGoal) {
                        wins += 1;
                        hasEnded = true;
                    }
                    shouldEnd = true;
                    for (Dot dot : dots) {
                        if (dot.activated) {
                            shouldEnd = false;
                            break;
                        }
                    }
                } else {
                    hasEnded = true;
                }
            } else {
                restart();
            }
    }

    public void restart() {
        dots.clear();
        games += 1;
        System.out.println(wins/games);
        level = game.randomLevel(true);
        scoreGoal = Math.round(level.count * level.completionPercentage);
        score = 0;
        hasEnded = false;
        shouldEnd = false;
        for (int i = 0; i < level.count; i++) {
            dots.add(new Dot(new Vector2(game.random.nextFloat() * (game.screenSize.x - ((dotTexture.getWidth() * level.dotSize / 2) * 2)) + (dotTexture.getWidth() * level.dotSize / 2),
                    game.random.nextFloat() * (game.screenSize.y - ((dotTexture.getWidth() * level.dotSize / 2) * 2)) + (dotTexture.getWidth() * level.dotSize / 2)),
                    new Vector2(game.random.nextFloat() * 2f - 1f, game.random.nextFloat() * 2f - 1f).nor(), level.speed, level.dotSize, level.maxSize));
        }
        dots.add(new Dot(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2),level.maxSize));
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
        return false;
    }
}
