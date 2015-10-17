package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class MainMenuScreen implements Screen {
    YouOnlyTapOnce game;
    private BitmapFont bigFont;
    private BitmapFont mediumFont;
    private Texture levelsTexture;
    private Texture levelsTexturePressed;
    private Texture randomTexture;
    private Texture randomTexturePressed;
    private Texture shopTexture;
    private Texture shopTexturePressed;
    private Texture settingsTexture;
    private Texture settingsTexturePressed;
    private Sound clickSound;

    private boolean doVibrate;
    private boolean isMuted;

    private Stage stage;
    private Table table;
    private Texture logoTexture;
    private Texture logoTextureHorizontal;
    private Color blue = new Color(50f / 255f, 130f / 255f, 200f / 255f, 1);

    ImageButton.ImageButtonStyle levelsButtonStyle;
    ImageButton.ImageButtonStyle randomButtonStyle;
    ImageButton.ImageButtonStyle shopButtonStyle;
    ImageButton.ImageButtonStyle settingsButtonStyle;
    Label.LabelStyle labelStyleBig;
    Label.LabelStyle labelStyleMedium;

    public MainMenuScreen(final YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        isMuted = game.prefs.getBoolean("settingsMute");
        doVibrate = game.prefs.getBoolean("settingsVibrate");

        bigFont = game.manager.get("big_font.ttf", BitmapFont.class);
        mediumFont = game.manager.get("medium_font.ttf", BitmapFont.class);
        logoTexture = game.manager.get("logo.png", Texture.class);
        logoTextureHorizontal = game.manager.get("logo_horizontal.png", Texture.class);
        levelsTexture = game.manager.get("levels_icon.png", Texture.class);
        levelsTexturePressed = game.manager.get("levels_icon_pressed.png", Texture.class);
        randomTexture = game.manager.get("random_icon.png", Texture.class);
        randomTexturePressed = game.manager.get("random_icon_pressed.png", Texture.class);
        shopTexture = game.manager.get("shop_icon.png", Texture.class);
        shopTexturePressed = game.manager.get("shop_icon_pressed.png", Texture.class);
        settingsTexture = game.manager.get("settings_icon.png", Texture.class);
        settingsTexturePressed = game.manager.get("settings_icon_pressed.png", Texture.class);
        clickSound = game.manager.get("click.mp3", Sound.class);

        logoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logoTextureHorizontal.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        levelsTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        levelsTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        randomTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        randomTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        shopTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        shopTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        settingsTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        settingsTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        levelsButtonStyle = new ImageButton.ImageButtonStyle();
        levelsButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(levelsTexture));
        levelsButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(levelsTexturePressed));

        randomButtonStyle = new ImageButton.ImageButtonStyle();
        randomButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(randomTexture));
        randomButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(randomTexturePressed));

        shopButtonStyle = new ImageButton.ImageButtonStyle();
        shopButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(shopTexture));
        shopButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(shopTexturePressed));

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
                    GDXButtonDialog dialog = game.dialogs.newDialog(GDXButtonDialog.class);
                    dialog.setTitle("Are you sure?");
                    dialog.setMessage("Are you sure you want to exit?");

                    dialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            if (button == 1) {
                                Gdx.app.exit();
                            }
                        }
                    });

                    dialog.addButton("No");
                    dialog.addButton("Yes");

                    dialog.build().show();
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
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                game.setScreen(new LevelSelectScreen(game));
            }
        });
        Label levelsLabel = new Label("Levels", labelStyleMedium);

        ImageButton randomButton = new ImageButton(randomButtonStyle);
        randomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                game.setScreen(new GameScreen(game, game.randomLevel(true)));
            }
        });
        Label randomLabel = new Label("Random", labelStyleMedium);

        ImageButton shopButton = new ImageButton(shopButtonStyle);
        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                //Do seething
            }
        });
        Label shopLabel = new Label("Shop", labelStyleMedium);

        ImageButton settingsButton = new ImageButton(settingsButtonStyle);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                game.setScreen(new SettingsScreen(game));
            }
        });
        Label settingsLabel = new Label("Settings", labelStyleMedium);

        table = new Table();
        table.setFillParent(true);

        if (game.screenSize.x > game.screenSize.y) {
            table.add(logoImageHorizontal).colspan(4).padBottom(game.sizeModifier * 80).size(game.sizeModifier * 1704, game.sizeModifier * 213);
            table.row();
            table.add(levelsButton).expandX().size(game.sizeModifier * 250).padBottom(game.sizeModifier * -20).padLeft(game.sizeModifier * 40).uniformX();
            table.add(randomButton).expandX().size(game.sizeModifier * 250).uniformX();
            table.add(shopButton).expandX().size(game.sizeModifier * 250).uniformX();
            table.add(settingsButton).expandX().size(game.sizeModifier * 260).padRight(game.sizeModifier * 40).uniformX();
            table.row();
            table.add(levelsLabel).expandX().padBottom(game.sizeModifier * 180).padLeft(game.sizeModifier * 40).uniformX();
            table.add(randomLabel).expandX().padBottom(game.sizeModifier * 180).uniformX();
            table.add(shopLabel).expandX().padBottom(game.sizeModifier * 180).uniformX();
            table.add(settingsLabel).expandX().padBottom(game.sizeModifier * 180).padRight(game.sizeModifier * 40).uniformX();
        } else {
            table.add(logoImage).colspan(2).size(game.sizeModifier * 950).padTop(game.sizeModifier * -75f).padBottom(game.sizeModifier * -160f);
            table.row();
            table.add(levelsButton).size(game.sizeModifier * 250).padRight(game.sizeModifier * -30);
            table.add(randomButton).size(game.sizeModifier * 250).padLeft(game.sizeModifier * -30);
            table.row();
            table.add(levelsLabel).padBottom(game.sizeModifier * 120).padRight(game.sizeModifier * -30).top();
            table.add(randomLabel).padBottom(game.sizeModifier * 120).padLeft(game.sizeModifier * -30).top();
            table.row();
            table.add(shopButton).size(game.sizeModifier * 250).padRight(game.sizeModifier * -30);
            table.add(settingsButton).size(game.sizeModifier * 260).padLeft(game.sizeModifier * -30);
            table.row();
            table.add(shopLabel).expand().padRight(game.sizeModifier * -30).top();
            table.add(settingsLabel).expand().padLeft(game.sizeModifier * -30).top();
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
        game.sizeModifier = Math.min(width, height) / 1080f;
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
        mediumFont.dispose();
        levelsTexture.dispose();
        levelsTexturePressed.dispose();
        randomTexture.dispose();
        randomTexturePressed.dispose();
        shopTexture.dispose();
        shopTexturePressed.dispose();
        settingsTexture.dispose();
        settingsTexturePressed.dispose();
        clickSound.dispose();
        stage.dispose();
        logoTexture.dispose();
    }
}
