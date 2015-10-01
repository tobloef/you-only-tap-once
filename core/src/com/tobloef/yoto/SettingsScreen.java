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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsScreen implements Screen {
    YouOnlyTapOnce game;
    private BitmapFont bigFont;
    private BitmapFont mediumFont;
    private Texture backTexture;
    private Texture backTexturePressed;
    private Texture muteTexture;
    private Texture muteTexturePressed;
    private Texture unmuteTexture;
    private Texture unmuteTexturePressed;
    private Texture removeAdsTexture;
    private Texture removeAdsTexturePressed;
    private Texture rateTexture;
    private Texture rateTexturePressed;
    private Texture enableVibrationTexture;
    private Texture enableVibrationTexturePressed;
    private Texture disableVibrationTexture;
    private Texture disableVibrationTexturePressed;
    private Sound clickSound;


    private Stage stage;
    private Table table;
    private Label vibrationLabel;
    private Label muteLabel;
    private Color blue = new Color(50f / 255f, 130f / 255f, 200f / 255f, 1);

    private boolean doVibrate;
    private boolean isMuted;

    ImageButtonStyle backButtonStyle;
    ImageButtonStyle muteButtonStyle;
    ImageButtonStyle unmuteButtonStyle;
    ImageButtonStyle removeAdsButtonStyle;
    ImageButtonStyle rateButtonStyle;
    ImageButtonStyle enableVibrationButtonStyle;
    ImageButtonStyle disableVibrationButtonStyle;
    LabelStyle labelStyleBig;
    LabelStyle labelStyleMedium;

    public SettingsScreen(final YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        isMuted = game.prefs.getBoolean("settingsMute");
        doVibrate = game.prefs.getBoolean("settingsVibrate");

        bigFont = game.manager.get("big_font.ttf", BitmapFont.class);
        mediumFont = game.manager.get("medium_font.ttf", BitmapFont.class);
        backTexture = game.manager.get("back_icon.png", Texture.class);
        backTexturePressed = game.manager.get("back_icon_pressed.png", Texture.class);
        muteTexture = game.manager.get("mute_icon.png", Texture.class);
        muteTexturePressed = game.manager.get("mute_icon_pressed.png", Texture.class);
        unmuteTexture = game.manager.get("unmute_icon.png", Texture.class);
        unmuteTexturePressed = game.manager.get("unmute_icon_pressed.png", Texture.class);
        removeAdsTexture = game.manager.get("remove_ads_icon.png", Texture.class);
        removeAdsTexturePressed = game.manager.get("remove_ads_icon_pressed.png", Texture.class);
        rateTexture = game.manager.get("rate_icon.png", Texture.class);
        rateTexturePressed = game.manager.get("rate_icon_pressed.png", Texture.class);
        enableVibrationTexture = game.manager.get("enable_vibration_icon.png", Texture.class);
        enableVibrationTexturePressed = game.manager.get("enable_vibration_icon_pressed.png", Texture.class);
        disableVibrationTexture = game.manager.get("disable_vibration_icon.png", Texture.class);
        disableVibrationTexturePressed = game.manager.get("disable_vibration_icon_pressed.png", Texture.class);
        clickSound = game.manager.get("click.mp3", Sound.class);

        backTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        muteTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        muteTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        unmuteTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        unmuteTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        removeAdsTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        removeAdsTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rateTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rateTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        enableVibrationTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        enableVibrationTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        disableVibrationTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        disableVibrationTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        backButtonStyle = new ImageButtonStyle();
        backButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(backTexture));
        backButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(backTexturePressed));

        muteButtonStyle = new ImageButtonStyle();
        muteButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(muteTexture));
        muteButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(muteTexturePressed));

        unmuteButtonStyle = new ImageButtonStyle();
        unmuteButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(unmuteTexture));
        unmuteButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(unmuteTexturePressed));

        removeAdsButtonStyle = new ImageButtonStyle();
        removeAdsButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(removeAdsTexture));
        removeAdsButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(removeAdsTexturePressed));

        rateButtonStyle = new ImageButtonStyle();
        rateButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(rateTexture));
        rateButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(rateTexturePressed));

        enableVibrationButtonStyle = new ImageButtonStyle();
        enableVibrationButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(enableVibrationTexture));
        enableVibrationButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(enableVibrationTexturePressed));

        disableVibrationButtonStyle = new ImageButtonStyle();
        disableVibrationButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(disableVibrationTexture));
        disableVibrationButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(disableVibrationTexturePressed));

        labelStyleBig = new LabelStyle();
        labelStyleBig.font = bigFont;

        labelStyleMedium = new LabelStyle();
        labelStyleMedium.font = mediumFont;

        stage = new Stage(new ScreenViewport());
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK || keycode == Input.Keys.BACKSPACE) {
                    game.setScreen(new MainMenuScreen(game));
                }
                return true;
            }
        });
        stage.setDebugAll(false);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        Label settingsLabel = new Label("Settings", labelStyleBig);

        ImageButton backButton = new ImageButton(backButtonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                game.setScreen(new MainMenuScreen(game));
            }
        });

        ImageButton removeAdsButton = new ImageButton(removeAdsButtonStyle);
        removeAdsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Do something
            }
        });
        Label removeAdsLabel = new Label("Remove\nAds", labelStyleMedium);
        removeAdsLabel.setAlignment(2);

        ImageButton rateButton = new ImageButton(rateButtonStyle);
        rateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.tobloef.yoto");
            }
        });
        Label rateLabel = new Label("Rate\n", labelStyleMedium);
        rateLabel.setAlignment(2);

        final ImageButton vibrationButton;
        if (game.prefs.getBoolean("settingsVibrate")) {
            vibrationButton = new ImageButton(disableVibrationButtonStyle);
            vibrationLabel = new Label("Disable\nVibration", labelStyleMedium);
        } else {
            vibrationButton = new ImageButton(enableVibrationButtonStyle);
            vibrationLabel = new Label("Enable\nVibration", labelStyleMedium);
        }
        vibrationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (game.prefs.getBoolean("settingsVibrate")) {
                    vibrationLabel.setText("Enable\nVibration");
                    vibrationButton.setStyle(enableVibrationButtonStyle);
                    game.prefs.putBoolean("settingsVibrate", false);
                    doVibrate = false;
                } else {
                    Gdx.input.vibrate(25);
                    vibrationLabel.setText("Disable\nVibration");
                    vibrationButton.setStyle(disableVibrationButtonStyle);
                    game.prefs.putBoolean("settingsVibrate", true);
                    doVibrate = true;
                }
                game.prefs.flush();
            }
        });
        vibrationLabel.setAlignment(2);

        final ImageButton muteButton;
        if (game.prefs.getBoolean("settingsMute")) {
            muteButton = new ImageButton(unmuteButtonStyle);
            muteLabel = new Label("Unmute\n", labelStyleMedium);
        } else {
            muteButton = new ImageButton(muteButtonStyle);
            muteLabel = new Label("Mute\n", labelStyleMedium);
        }
        muteLabel.setAlignment(2);
        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                if (game.prefs.getBoolean("settingsMute")) {
                    clickSound.play();
                    muteLabel.setText("Mute\n");
                    muteButton.setStyle(muteButtonStyle);
                    game.prefs.putBoolean("settingsMute", false);
                    isMuted = false;
                } else {
                    muteLabel.setText("Unmute\n");
                    muteButton.setStyle(unmuteButtonStyle);
                    game.prefs.putBoolean("settingsMute", true);
                    isMuted = true;
                }
                game.prefs.flush();
            }
        });


        table = new Table();
        table.setFillParent(true);

        if (game.screenSize.x > game.screenSize.y) {
            table.add(settingsLabel).colspan(4).padBottom(game.sizeModifier * 80);
            table.row();
            table.add(muteButton).expandX().size(game.sizeModifier * 250).padBottom(game.sizeModifier * 0).padLeft(game.sizeModifier * 40).uniformX();
            table.add(rateButton).expandX().size(game.sizeModifier * 250).padBottom(game.sizeModifier * 20).uniformX();
            table.add(removeAdsButton).expandX().size(game.sizeModifier * 250).padBottom(game.sizeModifier * 20).uniformX();
            table.add(vibrationButton).expandX().size(game.sizeModifier * 260).padRight(game.sizeModifier * 40).padBottom(game.sizeModifier * 20).uniformX();
            table.row();
            table.add(muteLabel).expandX().padBottom(game.sizeModifier * 180).padLeft(game.sizeModifier * 40).uniformX();
            table.add(removeAdsLabel).expandX().padBottom(game.sizeModifier * 180).uniformX();
            table.add(rateLabel).expandX().padBottom(game.sizeModifier * 180).uniformX();
            table.add(vibrationLabel).expandX().padBottom(game.sizeModifier * 180).padRight(game.sizeModifier * 40).uniformX();
        } else {
            table.add(settingsLabel).colspan(2).expand().padBottom(game.sizeModifier * 80).padTop(game.sizeModifier * 50);
            table.row();
            table.add(muteButton).size(game.sizeModifier * 250).padRight(game.sizeModifier * -30);
            table.add(rateButton).size(game.sizeModifier * 250).padLeft(game.sizeModifier * -30);
            table.row();
            table.add(muteLabel).padRight(game.sizeModifier * -30).top();
            table.add(rateLabel).padLeft(game.sizeModifier * -30).top();
            table.row();
            table.add(removeAdsButton).size(game.sizeModifier * 250).padRight(game.sizeModifier * -30);
            table.add(vibrationButton).size(game.sizeModifier * 250).padLeft(game.sizeModifier * -30);
            table.row();
            table.add(removeAdsLabel).expand().padRight(game.sizeModifier * -30).top().padBottom(game.sizeModifier * 100);
            table.add(vibrationLabel).expand().padLeft(game.sizeModifier * -30).top().padBottom(game.sizeModifier * 100);
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

    }
}
