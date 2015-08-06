package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen, InputProcessor {
    final YouOnlyTapOnce game;
    private OrthographicCamera camera;

    private Color backgroundColor;
    private Color blue = new Color(60/255f, 145/255f, 215/255f, 1);
    private Color green = new Color(95/255f, 195/255f, 95/255f, 1);

    private Texture dotTexture;
    private Texture shadowTexture;
    private int dotTextureSize;
    private Sound popSound;
    private BitmapFont scoreFont;

    private Array<Dot> dots = new Array<Dot>();
    private long timeSincePop;
    private boolean haveTouched = false;
    private boolean shouldEnd = false;
    private boolean completed = false;
    private int score = 0;
    private int scoreGoal;

    private int levelID;
    private int count;
    private float dotSize;
    private float maxSize;
    private float speed;
    private float completionPercentage;

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
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.screenSize.x, game.screenSize.y);
        Gdx.input.setInputProcessor(this);

        backgroundColor = blue;
        dotTexture = game.manager.get("dot_white.png", Texture.class);
        shadowTexture = game.manager.get("dot_shadow.png", Texture.class);
        dotTextureSize = dotTexture.getWidth();
        popSound = game.manager.get("pop.mp3", Sound.class);
        timeSincePop = System.currentTimeMillis();
        scoreFont = game.manager.get("score_font.ttf", BitmapFont.class);

        scoreGoal = Math.round(count * completionPercentage);


        /*  Spawn the dots  */
        for (int i = 0; i < count; i++) {
            dots.add(new Dot(new Vector3(game.random.nextFloat() * (game.screenSize.x - ((dotTextureSize * dotSize/2) * 2)) + (dotTextureSize * dotSize/2),
                    game.random.nextFloat() * (game.screenSize.y - ((dotTextureSize * dotSize/2) * 2)) + (dotTextureSize * dotSize/2), 0),
                    new Vector3(game.random.nextFloat() * 2f - 1f, game.random.nextFloat() * 2f - 1f, 0).nor(), speed, dotSize, maxSize));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*  Render Sprites  */
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        for (Dot dot: dots) {
            game.batch.draw(shadowTexture, (dot.position.x - dotTextureSize * dot.size / 2) + (dotTextureSize * dot.size * 0.2f * dotSize), (dot.position.y - dotTextureSize * dot.size / 2) - (dotTextureSize * 0.2f * dotSize), dotTextureSize * dot.size, dotTextureSize * dot.size);
        }
        for (Dot dot: dots) {
            game.batch.draw(dotTexture, dot.position.x - dotTextureSize * dot.size / 2, dot.position.y - dotTextureSize * dot.size / 2, dotTextureSize * dot.size, dotTextureSize * dot.size);
        }
        scoreFont.draw(game.batch, score + "/" + scoreGoal, 30 * game.sizeModifier, game.screenSize.y - (30 * game.sizeModifier));
        game.batch.end();

        /*  Calculate Movement  */
        for (Dot dot: dots) {
            if (!dot.activated) {
                Vector3 position = dot.position;
                position.x += Math.min(Math.max(dot.direction.x * Gdx.graphics.getDeltaTime() * 100, -1), 1) * speed;
                position.y += Math.min(Math.max(dot.direction.y * Gdx.graphics.getDeltaTime() * 100, -1), 1) * speed;
                dot.position = position;

                /*  Physics  */
                if (dot.position.x <= 0 + (dotTextureSize * dot.size / 2) || dot.position.x + (dotTextureSize * dot.size / 2) >= game.screenSize.x) {
                    Vector3 direction = dot.direction;
                    direction.x = -direction.x;
                    dot.direction = direction;
                }
                if (dot.position.y <= 0 + (dotTextureSize * dot.size / 2) || dot.position.y + (dotTextureSize * dot.size / 2) >= game.screenSize.y) {
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
                                popSound.play(0.5f, game.random.nextFloat()*(1.25f - 0.75f) + 0.5f, 0);
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
        if (score >= scoreGoal && (!shouldEnd || completed)) {
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
        if (!haveTouched) {
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
