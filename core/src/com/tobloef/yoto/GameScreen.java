package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private Color blue = new Color(60f/255f, 145f/255f, 215f/255f, 1);
    private Color green = new Color(95f/255f, 195f/255f, 95f/255f, 1);
    private Color red = new Color(216f/255f, 71f/255f, 71f/255f, 1);

    private Texture dotTexture;
    private Texture shadowTexture;
    private Texture restartTexture;
    private Sound popSound;
    private BitmapFont scoreFont;

    private Array<Dot> dots = new Array<Dot>();
    private long timeSincePop;
    private boolean hasTouched = false;
    private boolean hasEnded = false;
    private boolean shouldEnd = false;
    private boolean completed = false;
    private int score = 0;
    private int scoreGoal;
    
    private float textureOffset;
    private float dotTextureSize;

    private Level level;

    public GameScreen(final YouOnlyTapOnce game, Level level) {
        this.game = game;
        this.level = level;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.screenSize.x, game.screenSize.y);
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        backgroundColor = blue;
        dotTexture = game.manager.get("dot.png", Texture.class);
        restartTexture = game.manager.get("restart_icon.png", Texture.class);
        popSound = game.manager.get("pop.mp3", Sound.class);
        timeSincePop = System.currentTimeMillis();
        scoreFont = game.manager.get("score_font.ttf", BitmapFont.class);

        scoreGoal = Math.round(level.count * level.completionPercentage);
        textureOffset = (dotTexture.getWidth()-15)*game.sizeModifier;

        /*  Spawn the dots  */
        for (int i = 0; i < level.count; i++) {
            float posX = game.random.nextFloat() * (game.screenSize.x - textureOffset*2) + textureOffset*2;
            float posY = game.random.nextFloat() * (game.screenSize.y - textureOffset*2) + textureOffset*2;
            Vector3 position = new Vector3(posX, posY, 0);
            Vector3 direction = new Vector3(game.random.nextFloat() * 2f - 1f, game.random.nextFloat() * 2f - 1f, 0).nor();
            dots.add(new Dot(position, direction, level.speed, level.dotSize, level.maxSize));
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
            game.batch.draw(dotTexture, dot.position.x - textureOffset*dot.size, dot.position.y - textureOffset*dot.size, dotTexture.getWidth()*dot.size, dotTexture.getWidth()*dot.size);
        }
        game.batch.draw(restartTexture, game.screenSize.x - 150f * game.sizeModifier, game.screenSize.y - 150f * game.sizeModifier, restartTexture.getWidth() /2 * game.sizeModifier, restartTexture.getWidth()/2 * game.sizeModifier);
        scoreFont.draw(game.batch, score + "/" + scoreGoal, 30 * game.sizeModifier, game.screenSize.y - (30 * game.sizeModifier));
        game.batch.end();

        /*  Calculate Movement  */
        for (Dot dot: dots) {
            if (!dot.activated) {
                Vector3 position = dot.position;
                position.x += Math.min(Math.max(dot.direction.x * Gdx.graphics.getDeltaTime() * 100, -1), 1) * level.speed;
                position.y += Math.min(Math.max(dot.direction.y * Gdx.graphics.getDeltaTime() * 100, -1), 1) * level.speed;
                dot.position = position;

                /*  Physics  */
                if (dot.position.x <= textureOffset || dot.position.x >= game.screenSize.x - textureOffset) {
                    Vector3 direction = dot.direction;
                    direction.x = -direction.x;
                    dot.direction = direction;
                }
                if (dot.position.y <= textureOffset || dot.position.y >= game.screenSize.y - textureOffset) {
                    Vector3 direction = dot.direction;
                    direction.y = -direction.y;
                    dot.direction = direction;
                }
            } else {

                /*  Dot Collision  */
                for (int i = 0; i < dots.size; i++) {
                    if (!dots.get(i).activated && dots.get(i) != dot) {
                        if (dot.position.dst(dots.get(i).position) < ((dotTexture.getWidth() * game.sizeModifier * dot.size) / 2) + ((dotTexture.getWidth() * game.sizeModifier *  dots.get(i).size) / 2)) {
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
        if (hasTouched && !hasEnded) {
            if (!shouldEnd) {
                if (score >= scoreGoal) {
                    completed = true;
                    if (game.prefs.getInteger("levelsAvailable") == level.levelID) {
                        game.prefs.putInteger("levelsAvailable", level.levelID+1);
                        game.prefs.flush();
                    }
                }
                shouldEnd = true;
                for (Dot dot : dots) {
                    if (dot.activated) {
                        shouldEnd = false;
                        break;
                    }
                }
            } else {
                if (shouldEnd) {
                    for (Dot dot : dots) {
                        dot.state = -1;
                    }
                }
            }
            if (dots.size == 0) {
                hasEnded = true;
            }
        }
        if (completed && backgroundColor != green) {
            backgroundColor.lerp(green, Gdx.graphics.getDeltaTime() * 4f);
        }
        if (!completed && shouldEnd && backgroundColor != red) {
            backgroundColor.lerp(red, Gdx.graphics.getDeltaTime() * 4f);
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
        if(keycode == Input.Keys.BACK || keycode == Input.Keys.BACKSPACE){
            //TODO Show either pause menu or exit confirmation
            game.setScreen(new MainMenuScreen(game));
        }
        return true;
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
        if (mousePos.x > game.screenSize.x-150f*game.sizeModifier && mousePos.x < (game.screenSize.x-150f*game.sizeModifier) + (restartTexture.getWidth()*game.sizeModifier)
        && mousePos.y > game.screenSize.y-150f*game.sizeModifier && mousePos.y < (game.screenSize.y-150f*game.sizeModifier) + (restartTexture.getHeight()*game.sizeModifier)) {
            game.setScreen(new GameScreen(game, level));
            return true;
        }
        if (!hasTouched) {
            dots.add(new Dot(mousePos, level.maxSize));
            hasTouched = true;
        } else if(dots.size <= 0) {
            game.setScreen(new MainMenuScreen(game));
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
