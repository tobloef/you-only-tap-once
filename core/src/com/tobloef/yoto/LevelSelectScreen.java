package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class LevelSelectScreen implements Screen {
    YouOnlyTapOnce game;
    private ShapeRenderer shapeRenderer;
    private BitmapFont menuFont;
    private Stage stage;
    private Table table;
    private Table innerTable;
    private ScrollPane scrollPane;

    private Color blue = new Color(60/255f, 145/255f, 215/255f, 1f);
    private Color clear = new Color(60/255f, 145/255f, 215/255f, 0f);

    public LevelSelectScreen(final YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        menuFont = game.manager.get("menu_font.ttf", BitmapFont.class);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = menuFont;
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.downFontColor = Color.ORANGE;

        table = new Table();
        table.setFillParent(true);
        innerTable = new Table();
        for (int i = 1; i < game.levels.size() + 1; i++) {
            TextButton textButton = new TextButton(Integer.toString(i), textButtonStyle);
            final int finalI = i-1;
            textButton.addListener(new ChangeListener() {
                @Override
                public void changed (ChangeEvent event, Actor actor) {
                    game.setScreen(new GameScreen(game, game.levels.get(finalI)));
                }
            });
            innerTable.add(textButton).width(game.sizeModifier*270f).height(game.sizeModifier*200f);
            if (i%6 == 0) {
                innerTable.row();
            }
        }
        ScrollPaneStyle scrollPanelStyle = new ScrollPaneStyle();
        scrollPanelStyle.vScrollKnob = new BaseDrawable();
        scrollPane = new ScrollPane(innerTable);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setupOverscroll(30f, 30f, 150f);
        table.add(scrollPane);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClearColor(blue.r, blue.g, blue.b, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(blue);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), 10 * game.sizeModifier);
        shapeRenderer.rect(0, 10 * game.sizeModifier, Gdx.graphics.getWidth(), 50 * game.sizeModifier, blue, blue, clear, clear);
        shapeRenderer.rect(0, Gdx.graphics.getHeight() - 60*game.sizeModifier, Gdx.graphics.getWidth(), 50 * game.sizeModifier, clear, clear, blue, blue);
        shapeRenderer.rect(0, Gdx.graphics.getHeight() - 10*game.sizeModifier, Gdx.graphics.getWidth(), 10 * game.sizeModifier);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
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
