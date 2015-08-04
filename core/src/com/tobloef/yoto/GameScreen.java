package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
    private Sound popSound;
    private Texture dotTexture;
    private Texture shadowTexture;
    private int dotTextureSize;
    private Color c;
    private Array<Dot> dots = new Array<Dot>();
    private Random random = new Random();
    private Vector2 screenSize;
    private boolean haveTouched = false;
    private boolean shouldEnd = false;
    private boolean completed = false;
    private long timeSincePop;

    private Level level;

    private int levelID;
    private int count;
    private float dotSize;
    private float maxSize;
    private float speed;
    private float completionPercentage;

    private int score = 0;
    private float sizeModifier;

    /*  Colors  */
    private Color backgroundColor;
    private Color blue = new Color(60/255f, 145/255f, 215/255f, 1);
    private Color green = new Color(95/255f, 195/255f, 95/255f, 1);

    public GameScreen(final YouOnlyTapOnce game, Level level) {
        this.game = game;
        levelID = level.levelID;
        count = level.count;
        dotSize = level.size;
        maxSize = level.maxSize;
        speed = level.speed;
        completionPercentage = level.completionPercentage;
    }

    @Override
    public void show() {

        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sizeModifier = Math.min(screenSize.x, screenSize.y) / 1080f;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenSize.x, screenSize.y);
        Gdx.input.setInputProcessor(this);

        popSound = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
        timeSincePop = System.currentTimeMillis();

        dotTexture = new Texture(Gdx.files.internal("dot_white.png"), true);
        shadowTexture = new Texture(Gdx.files.internal("dot_black.png"), true);
        dotTextureSize = dotTexture.getWidth();
        backgroundColor = blue;
        c = game.batch.getColor();

        /*  Spawn the dots  */
        for (int i = 0; i < count; i++) {
            dots.add(new Dot(new Vector3(random.nextFloat() * (screenSize.x - ((dotTextureSize * dotSize/2) * 2)) + (dotTextureSize * dotSize/2),
                    random.nextFloat() * (screenSize.y - ((dotTextureSize * dotSize/2) * 2)) + (dotTextureSize * dotSize/2), 0),
                    new Vector3(random.nextFloat() * 2f - 1f, random.nextFloat() * 2f - 1f, 0).nor(), speed, dotSize, maxSize));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        /*  Render Sprites  */
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.setColor(c.r, c.g, c.b, 0.5f);
        for (Dot dot: dots) {
            game.batch.draw(shadowTexture, (dot.position.x - dotTextureSize * dot.size / 2) + (dotTextureSize * dot.size * 0.2f * dotSize), (dot.position.y - dotTextureSize * dot.size / 2) - (dotTextureSize * 0.2f * dotSize), dotTextureSize * dot.size, dotTextureSize * dot.size);
        }
        game.batch.setColor(c);
        for (Dot dot: dots) {
            game.batch.draw(dotTexture, dot.position.x - dotTextureSize * dot.size / 2, dot.position.y - dotTextureSize * dot.size / 2, dotTextureSize * dot.size, dotTextureSize * dot.size);
        }
        game.fonts.get(256).draw(game.batch, score + "/" + Math.round(count * completionPercentage), 30 * sizeModifier, screenSize.y - (30 * sizeModifier));
        game.batch.end();

        /*  Calculate Movement  */
        for (Dot dot: dots) {
            if (!dot.activated) {
                Vector3 position = dot.position;
                position.x += Math.min(Math.max(dot.direction.x * Gdx.graphics.getDeltaTime() * 100, -1), 1) * speed;
                position.y += Math.min(Math.max(dot.direction.y * Gdx.graphics.getDeltaTime() * 100, -1), 1) * speed;
                dot.position = position;

                /*  Physics  */
                if (dot.position.x <= 0 + (dotTextureSize * dot.size / 2) || dot.position.x + (dotTextureSize * dot.size / 2) >= screenSize.x) {
                    Vector3 direction = dot.direction;
                    direction.x = -direction.x;
                    dot.direction = direction;
                }
                if (dot.position.y <= 0 + (dotTextureSize * dot.size / 2) || dot.position.y + (dotTextureSize * dot.size / 2) >= screenSize.y) {
                    Vector3 direction = dot.direction;
                    direction.y = -direction.y;
                    dot.direction = direction;
                }
            } else {

                /*  Dot Collision  */
                for (int i = 0; i < dots.size; i++) {
                    if (!dots.get(i).activated && dots.get(i) != dot) {
                        if (dot.position.dst(dots.get(i).position) < ((dotTextureSize * dot.size) / 2) + ((dotTextureSize *  dots.get(i).size) / 2)) {
                            if (System.currentTimeMillis() - timeSincePop > 50) {
                                timeSincePop = System.currentTimeMillis();
                                popSound.play(0.5f, random.nextFloat()*(1.25f - 0.75f) + 0.5f, 0);
                            }
                            dots.get(i).activated = true;
                            dots.get(i).state = 1;
                            score += 1;
                        }
                    }
                }
            }

            /*  Expand/Shrink  */
            if (dot.state != 0) {
                dot.size += dot.state * Gdx.graphics.getDeltaTime() * 0.9f;
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
        if (score >= count*completionPercentage && (!shouldEnd || completed)) {
            completed = true;
            if (backgroundColor != green) {
                backgroundColor.lerp(green, Gdx.graphics.getDeltaTime() * 3f);
            }
        }
        if (haveTouched && !shouldEnd) {
            shouldEnd = true;
            for (int i = 0; i < dots.size; i++) {
                if (dots.get(i).activated) {
                    shouldEnd = false;
                    break;
                }
            }
            if (shouldEnd) {
                for (int i = 0; i < dots.size; i++) {
                    dots.get(i).state = -1;
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
        if (!haveTouched && game.canTouch) {
            Vector3 mousePos = new Vector3(screenX, screenY, 0);
            camera.unproject(mousePos);
            dots.add(new Dot(mousePos, maxSize));
            haveTouched = true;
        } else if(dots.size <= 0) {
            game.loadLevel(levelID);
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
