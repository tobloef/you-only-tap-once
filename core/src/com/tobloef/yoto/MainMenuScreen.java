package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    YouOnlyTapOnce game;
    private BitmapFont bigFont;
    private BitmapFont mediumFont;
    private Texture levelsTexture;
    private Texture levelsTexturePressed;
    private Texture randomTexture;
    private Texture randomTexturePressed;
    private Texture customTexture;
    private Texture customTexturePressed;
    private Texture settingsTexture;
    private Texture settingsTexturePressed;

    private Stage stage;
    private Table table;
    private Texture logoTexture;
    private Texture logoTextureHorizontal;
    private Color blue = new Color(50f/255f, 130f/255f, 200f/255f, 1);

    ImageButton.ImageButtonStyle levelsButtonStyle;
    ImageButton.ImageButtonStyle randomButtonStyle;
    ImageButton.ImageButtonStyle customButtonStyle;
    ImageButton.ImageButtonStyle settingsButtonStyle;
    Label.LabelStyle labelStyleBig;
    Label.LabelStyle labelStyleMedium;

    public MainMenuScreen(final YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        bigFont = game.manager.get("big_font.ttf", BitmapFont.class);
        mediumFont = game.manager.get("medium_font.ttf", BitmapFont.class);
        logoTexture = game.manager.get("logo.png", Texture.class);
        logoTextureHorizontal = game.manager.get("logo_horizontal.png", Texture.class);
        levelsTexture = game.manager.get("levels_icon.png", Texture.class);
        levelsTexturePressed = game.manager.get("levels_icon_pressed.png", Texture.class);
        randomTexture = game.manager.get("random_icon.png", Texture.class);
        randomTexturePressed = game.manager.get("random_icon_pressed.png", Texture.class);
        customTexture = game.manager.get("customise_icon.png", Texture.class);
        customTexturePressed = game.manager.get("customise_icon_pressed.png", Texture.class);
        settingsTexture = game.manager.get("settings_icon.png", Texture.class);
        settingsTexturePressed = game.manager.get("settings_icon_pressed.png", Texture.class);

        logoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logoTextureHorizontal.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        levelsTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        levelsTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        randomTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        randomTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        customTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        customTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        settingsTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        settingsTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        levelsButtonStyle = new ImageButton.ImageButtonStyle();
        levelsButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(levelsTexture));
        levelsButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(levelsTexturePressed));

        randomButtonStyle = new ImageButton.ImageButtonStyle();
        randomButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(randomTexture));
        randomButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(randomTexturePressed));

        customButtonStyle = new ImageButton.ImageButtonStyle();
        customButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(customTexture));
        customButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(customTexturePressed));

        settingsButtonStyle = new ImageButton.ImageButtonStyle();
        settingsButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(settingsTexture));
        settingsButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(settingsTexturePressed));

        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = bigFont;

        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = mediumFont;

        stage = new Stage(new ScreenViewport());
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK || keycode == Input.Keys.BACKSPACE) {
                    Gdx.app.exit();
                }
                return true;
            }
        });
        stage.setDebugAll(false);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        Image logoImage = new Image(logoTexture);
        Image logoImageHorizontal = new Image(logoTextureHorizontal);
        logoImage.setScaling(Scaling.fit);
        logoImageHorizontal.setScaling(Scaling.fit);

        ImageButton levelsButton = new ImageButton(levelsButtonStyle);
        levelsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });
        Label levelsLabel = new Label("Levels", labelStyleMedium);

        ImageButton randomButton = new ImageButton(randomButtonStyle);
        randomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, game.randomLevel()));
            }
        });
        Label randomLabel = new Label("Random", labelStyleMedium);

        ImageButton customButton = new ImageButton(customButtonStyle);
        customButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Go to custom level
            }
        });
        Label customLabel = new Label("Custom", labelStyleMedium);

        ImageButton settingsButton = new ImageButton(settingsButtonStyle);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));
            }
        });
        Label settingsLabel = new Label("Settings", labelStyleMedium);

        table = new Table();
        table.setFillParent(true);

        if (game.screenSize.x > game.screenSize.y) {
            table.add(logoImageHorizontal).colspan(4).padBottom(game.sizeModifier * 80).size(game.sizeModifier * 1704, game.sizeModifier * 213);
            table.row();
            table.add(levelsButton).expandX().size(game.sizeModifier * 220).padBottom(game.sizeModifier * -20).padLeft(game.sizeModifier * 40).uniformX();
            table.add(randomButton).expandX().size(game.sizeModifier * 220).uniformX();
            table.add(customButton).expandX().size(game.sizeModifier * 220).uniformX();
            table.add(settingsButton).expandX().size(game.sizeModifier * 230).padRight(game.sizeModifier * 40).uniformX();
            table.row();
            table.add(levelsLabel).padTop(game.sizeModifier * 0).expandX().padBottom(game.sizeModifier * 180).padLeft(game.sizeModifier * 40).uniformX();
            table.add(randomLabel).padTop(game.sizeModifier * 0).expandX().padBottom(game.sizeModifier * 180).uniformX();
            table.add(customLabel).padTop(game.sizeModifier * 0).expandX().padBottom(game.sizeModifier * 180).uniformX();
            table.add(settingsLabel).padTop(game.sizeModifier * 0).expandX().padBottom(game.sizeModifier * 180).padRight(game.sizeModifier * 40).uniformX();
        } else {
            table.add(logoImage).colspan(2).size(game.sizeModifier * 950).padTop(game.sizeModifier * -75f).padBottom(game.sizeModifier * -120f);
            table.row();
            table.add(levelsButton).size(game.sizeModifier * 220).padRight(game.sizeModifier * -30);
            table.add(randomButton).size(game.sizeModifier * 220).padLeft(game.sizeModifier * -30);
            table.row();
            table.add(levelsLabel).padTop(game.sizeModifier * 0).padBottom(game.sizeModifier * 140).padRight(game.sizeModifier * -30).top();
            table.add(randomLabel).padTop(game.sizeModifier * 0).padBottom(game.sizeModifier * 140).padLeft(game.sizeModifier * -30).top();
            table.row();
            table.add(customButton).size(game.sizeModifier * 220).padRight(game.sizeModifier * -30);
            table.add(settingsButton).size(game.sizeModifier * 220).padLeft(game.sizeModifier * -30);
            table.row();
            table.add(customLabel).expand().padTop(game.sizeModifier * 0).padBottom(game.sizeModifier * 140).padRight(game.sizeModifier * -30).top();
            table.add(settingsLabel).expand().padTop(game.sizeModifier * 0).padBottom(game.sizeModifier * 140).padLeft(game.sizeModifier * -30).top();
        }
        stage.addActor(table);
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
        game.sizeModifier = Math.min(width, height)/1080f;
        game.screenSize.x = width;
        game.screenSize.y = height;
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
        bigFont.dispose();
        stage.dispose();
        logoTexture.dispose();
    }
}
