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
import com.badlogic.gdx.pay.PurchaseSystem;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ShopScreen implements Screen {
    YouOnlyTapOnce game;

    private Stage stage;
    private Table table;
    private Color blue = new Color(50f / 255f, 130f / 255f, 200f / 255f, 1);
    private boolean doVibrate;
    private boolean isMuted;
    private Sound clickSound;
    private BitmapFont bigFont;
    private BitmapFont mediumFont;
    private BitmapFont smallFont;
    private BitmapFont tinyFont;
    private Texture backButtonTexture;
    private Texture backButtonTexturePressed;
    private Texture skipButtonTexture;
    private Texture skipButtonTexturePressed;

    ImageButton.ImageButtonStyle backButtonStyle;
    ImageButton.ImageButtonStyle skipButtonStyle;
    Label.LabelStyle labelStyleBig;
    Label.LabelStyle labelStyleMedium;
    Label.LabelStyle labelStyleSmall;
    Label.LabelStyle labelStyleTiny;

    private String skips10ID = "10skips";
    private String skips50ID = "50skips";
    private String skips2ID = "2skips";

    public static final String GOOGLE_PLAY_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjcWnlkahMj3YLaOTzeqQrx72ZnAUwWlQtJ+vkmrJtcs43IHadshwOjtuLQ4K1Rx1JsRKTBuypp+6aDupbfZzd9rpAwP4a+fT6MAhnQUJXeBpckpfBS/OveOoktwNyHqRHvz2aJ72wix/f55O9mmLY+N81EJcvg4I/lOvlNySOZQyVnrNKepv/mN0f7NUc/J7rYK9nxjrZqda7vU+EuSSs7FgGA1O0jRrXdxFaDnuBAFVQH8aqvTYyKKrt+sopOZ+it7M0P08/VQq9fB1OUSQVllFkyTiJwBTHQzy288WM7ITnHJdW43DHNL9YvWu+/lhHZi5c3+EtH1yA3tvDcPEDQIDAQAB";

    public ShopScreen(YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        isMuted = game.prefs.getBoolean("settingsMute");
        doVibrate = game.prefs.getBoolean("settingsVibrate");

        bigFont = game.manager.get("big_font.ttf", BitmapFont.class);
        mediumFont = game.manager.get("medium_font.ttf", BitmapFont.class);
        smallFont = game.manager.get("small_font.ttf", BitmapFont.class);
        tinyFont = game.manager.get("tiny_font.ttf", BitmapFont.class);
        backButtonTexture = game.manager.get("back_icon.png", Texture.class);
        backButtonTexturePressed = game.manager.get("back_icon_pressed.png", Texture.class);
        skipButtonTexture = game.manager.get("skip_icon.png", Texture.class);
        skipButtonTexturePressed = game.manager.get("skip_icon_pressed.png", Texture.class);
        clickSound = game.manager.get("click.mp3", Sound.class);

        backButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backButtonTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        skipButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        skipButtonTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(backButtonTexture));
        backButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(backButtonTexturePressed));

        skipButtonStyle = new ImageButton.ImageButtonStyle();
        skipButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(skipButtonTexture));
        skipButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(skipButtonTexturePressed));

        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = bigFont;

        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = mediumFont;

        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = smallFont;

        labelStyleTiny = new Label.LabelStyle();
        labelStyleTiny.font = tinyFont;

        stage = new Stage(new ScreenViewport());
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK || keycode == Input.Keys.BACKSPACE) {
                    if (!isMuted) {
                        clickSound.play();
                    }
                    if (doVibrate) {
                        Gdx.input.vibrate(25);
                    }
                    game.setScreen(new MainMenuScreen(game));
                }
                return true;
            }
        });
        stage.setDebugAll(false);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        Label titleLabel = new Label("Shop", labelStyleMedium);

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

        ImageButton skipsButton10 = new ImageButton(skipButtonStyle);
        skipsButton10.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                try {
                    PurchaseSystem.purchase(skips10ID);
                } catch (Exception e) {

                }
            }
        });
        Label skipsLabel10 = new Label("10 skips", labelStyleSmall);
        Label priceLabel10 = new Label("$0.99", labelStyleMedium);
        skipsLabel10.setAlignment(2);
        skipsLabel10.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                try {
                    PurchaseSystem.purchase(skips10ID);
                } catch (Exception e) {

                }
            }
        });
        priceLabel10.setAlignment(2);
        priceLabel10.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                try {
                    PurchaseSystem.purchase(skips10ID);
                } catch (Exception e) {

                }
            }
        });

        ImageButton skipsButton50 = new ImageButton(skipButtonStyle);
        skipsButton50.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                try {
                    PurchaseSystem.purchase(skips50ID);
                } catch (Exception e) {

                }
            }
        });
        Label skipsLabel50 = new Label("50 skips", labelStyleSmall);
        Label priceLabel50 = new Label("$2.99", labelStyleMedium);
        skipsLabel50.setAlignment(2);
        skipsLabel50.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                try {
                    PurchaseSystem.purchase(skips50ID);
                } catch (Exception e) {

                }
            }
        });
        priceLabel50.setAlignment(2);
        priceLabel50.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                try {
                    PurchaseSystem.purchase(skips50ID);
                } catch (Exception e) {

                }
            }
        });

        Label skipDescriptionLabel = new Label("Skip a difficult level", labelStyleSmall);
        skipDescriptionLabel.setAlignment(2);

        Label underlineLabel1 = new Label(new String(new char[18]).replace("\0", "_"), labelStyleSmall);
        skipDescriptionLabel.setAlignment(2);

        Label underlineLabel2 = new Label(new String(new char[18]).replace("\0", "_"), labelStyleSmall);
        skipDescriptionLabel.setAlignment(2);


        Label supportDeveloperLabel = new Label("By making a purchase you support\nfurther development of this and\nother games\n\nThank you!", labelStyleTiny);
        supportDeveloperLabel.setAlignment(2);
        supportDeveloperLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    PurchaseSystem.purchase(skips2ID);
                } catch (Exception e) {

                }
            }
        });

        Label removeAdsLabel = new Label("Any purchase will remove all ads", labelStyleTiny);
        removeAdsLabel.setAlignment(2);

        table = new Table();
        table.setFillParent(true);

        table.add(backButton).height(225 * game.sizeModifier).padLeft(game.sizeModifier * 60).padTop(20 * game.sizeModifier).left().uniformX().colspan(2);
        table.add(titleLabel).uniformX().colspan(2);
        table.add().uniformX().colspan(2);
        table.row();
        table.add(skipDescriptionLabel).colspan(6).padTop(game.sizeModifier * 150);
        table.row();
        table.add(underlineLabel1).colspan(6).padTop(game.sizeModifier * -80);
        table.row();
        table.add(skipsButton10).size(game.sizeModifier * 250).padLeft(130 * game.sizeModifier).padTop(game.sizeModifier * 20).colspan(3);
        table.add(skipsButton50).size(game.sizeModifier * 250).padRight(130 * game.sizeModifier).padTop(game.sizeModifier * 20).colspan(3);
        table.row();
        table.add(skipsLabel10).top().padTop(game.sizeModifier * -20).padLeft(130 * game.sizeModifier).colspan(3);
        table.add(skipsLabel50).top().padTop(game.sizeModifier * -20).padRight(130 * game.sizeModifier).colspan(3);
        table.row();
        table.add(priceLabel10).padBottom(game.sizeModifier * 0).padLeft(130 * game.sizeModifier).colspan(3);
        table.add(priceLabel50).padBottom(game.sizeModifier * 0).padRight(130 * game.sizeModifier).colspan(3);
        table.row();
        table.add(removeAdsLabel).colspan(6).padTop(game.sizeModifier * 50);
        table.row();
        table.add(underlineLabel2).colspan(6).padTop(game.sizeModifier * -70).padBottom(game.sizeModifier * 120);
        table.row();
        table.add(supportDeveloperLabel).colspan(6).expand().top();
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
