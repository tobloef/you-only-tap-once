package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LevelSelectScreen implements Screen {
    YouOnlyTapOnce game;
    private ShapeRenderer shapeRenderer;
    private BitmapFont menuFont;
    private Texture backButtonTexture;
    private Stage stage;
    private Table table;
    private Table innerTable;
    private ScrollPane scrollPane;
    private TextButtonStyle buttonStyleOn;
    private TextButtonStyle buttonStyleOff;
    private LabelStyle labelStyle;
    private ScrollPaneStyle scrollPanelStyle;

    private Color blue = new Color(60/255f, 145/255f, 215/255f, 1f);
    private Color clear = new Color(60/255f, 145/255f, 215/255f, 0f);

    public LevelSelectScreen(final YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        backButtonTexture = game.manager.get("back_icon.png", Texture.class);
        menuFont = game.manager.get("menu_font.ttf", BitmapFont.class);

        stage = new Stage(new ScreenViewport()) {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.BACK || keycode == Input.Keys.BACKSPACE){
                    //TODO Show either pause menu or exit confirmation
                    game.setScreen(new MainMenuScreen(game));
                }
                return true;
            }
        };

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        buttonStyleOn = new TextButtonStyle();
        buttonStyleOn.font = menuFont;
        buttonStyleOn.fontColor = Color.WHITE;
        buttonStyleOn.downFontColor = Color.ORANGE;

        buttonStyleOff = new TextButtonStyle();
        buttonStyleOff.font = menuFont;
        buttonStyleOff.fontColor = Color.DARK_GRAY;
        
        
        labelStyle = new LabelStyle();
        labelStyle.font = menuFont;
        labelStyle.fontColor = Color.WHITE;

        table = new Table();
        //table.setDebug(true);
        table.setFillParent(true);
        innerTable = new Table();
        for (int i = 0; i < game.levels.size(); i++) {
            TextButton textButton;
            if (i <= game.prefs.getInteger("levelsAvailable")) {
                textButton = new TextButton(Integer.toString(i+1), buttonStyleOn);
                final int finalI = i;
                textButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new GameScreen(game, game.levels.get(finalI)));
                    }
                });
            } else {
                textButton = new TextButton(Integer.toString(i+1), buttonStyleOff);
            }
            if (Gdx.graphics.getWidth() > Gdx.graphics.getHeight()) {
                innerTable.add(textButton).width(game.sizeModifier * 250f).height(game.sizeModifier * 200f);
                if ((i+1) % 7 == 0 && i != 0) {
                    innerTable.row();
                }
            } else {
                innerTable.add(textButton).width(game.sizeModifier * 250f).height(game.sizeModifier * 200f);
                if ((i+1) % 4 == 0 && i != 0) {
                    innerTable.row();
                }
            }
        }
        scrollPanelStyle = new ScrollPaneStyle();
        scrollPanelStyle.vScrollKnob = new BaseDrawable();
        scrollPane = new ScrollPane(innerTable);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setupOverscroll(30f, 30f, 150f);

        Image backButton = new Image(backButtonTexture);
        backButton.setScaling(Scaling.fit);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        table.add(backButton).height(175*game.sizeModifier).width(200*game.sizeModifier).padLeft(70*game.sizeModifier).uniformX().left();
        Label titleLabel = new Label("Levels", labelStyle);
        table.add(titleLabel).uniformX();
        table.add().uniformX();
        table.row();
        table.add(scrollPane).expandX().colspan(3);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(blue.r, blue.g, blue.b, 1);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(blue);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), 10 * game.sizeModifier);
        shapeRenderer.rect(0, 10 * game.sizeModifier, Gdx.graphics.getWidth(), 50 * game.sizeModifier, blue, blue, clear, clear);
        shapeRenderer.rect(0, Gdx.graphics.getHeight() - 225 * game.sizeModifier, Gdx.graphics.getWidth(), 50 * game.sizeModifier, clear, clear, blue, blue);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void resize(int width, int height) {
        show();
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
        shapeRenderer.dispose();
        menuFont.dispose();
        backButtonTexture.dispose();
        stage.dispose();
    }
}
