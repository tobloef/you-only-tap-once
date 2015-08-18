package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    YouOnlyTapOnce game;
    private BitmapFont menuFont;
    private Stage stage;
    private Table table;
    private Texture logoTexture;
    private Color blue = new Color(60/255f, 145/255f, 215/255f, 1);
    public MainMenuScreen(final YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        menuFont = game.manager.get("menu_font.ttf", BitmapFont.class);
        logoTexture = game.manager.get("splash_logo.png", Texture.class);

        stage = new Stage(new ScreenViewport())  {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.BACK || keycode == Input.Keys.BACKSPACE){
                    //TODO Confirmation?
                    Gdx.app.exit();
                }
                return true;
            }
        };
        Gdx.input.setInputProcessor(stage);

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = menuFont;
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.downFontColor = Color.ORANGE;
        final TextButton levelSelectButton = new TextButton("Level Select", textButtonStyle);
        final TextButton randomLevelButton = new TextButton("Random Level", textButtonStyle);
        final TextButton customLevelButton = new TextButton("Custom Level", textButtonStyle);

        table = new Table();
        table.setFillParent(true);
        table.add(levelSelectButton);
        table.row();
        table.add(randomLevelButton);
        table.row();
        table.add(customLevelButton);
        stage.addActor(table);

        levelSelectButton.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        randomLevelButton.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                //TODO Preview of random level before play
                int randCount = game.random.nextInt(300 - 1) + 1;
                float randSize = 0f;
                while (randSize < 0.1f) {
                    randSize = (0.4f - (((randCount-100f) * 2f) / 1000f) + (game.random.nextFloat() * (0.2f - -0.2f) + -0.2f))/2;
                }
                float randMaxSize = Math.max(2, (float) Math.pow(Math.E, Math.log(1.15f) / randSize) + (game.random.nextFloat() * (1f - -1f) + -1f));
                float randSpeed = game.random.nextFloat() * (3f - 0.25f) + 0.25f;
                float randCompletion = game.random.nextFloat() * (0.99f - 0.65f) + 0.65f;
                Level level = new Level(-1, randCount, randSize, randMaxSize, randSpeed, randCompletion);
                Gdx.app.log("Level", "\nCount: " + randCount + "\nSize: " + randSize + "\nMaxSize: " + randMaxSize + "\nSpeed: " + randSpeed + "\nCompletion: " + randCompletion);
                game.setScreen(new GameScreen(game, level));
            }
        });

        customLevelButton.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(blue.r, blue.g, blue.b, 1);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        menuFont.dispose();
        stage.dispose();
        logoTexture.dispose();
    }
}
