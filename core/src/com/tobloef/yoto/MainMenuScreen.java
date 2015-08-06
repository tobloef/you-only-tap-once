package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
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

public class MainMenuScreen implements Screen {
    YouOnlyTapOnce game;
    private BitmapFont menuFont;
    private Stage stage;
    private Table table;
    private Texture logoTexture;

    public MainMenuScreen(final YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        menuFont = game.manager.get("menu_font.ttf", BitmapFont.class);
        logoTexture = game.manager.get("splash_logo.png", Texture.class);

        stage = new Stage();
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
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
