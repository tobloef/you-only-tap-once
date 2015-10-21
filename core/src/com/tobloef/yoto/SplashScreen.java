package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.pay.*;
import com.badlogic.gdx.utils.GdxRuntimeException;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SplashScreen implements Screen {
    YouOnlyTapOnce game;
    private Texture splashTexture;
    private float sizeModifier;
    private Vector2 screenSize;
    private OrthographicCamera camera;

    private long minSplashTime = 000L;
    private long startTime;
    private String skips10ID = "10skips";
    private String skips50ID = "50skips";
    private String skips2ID = "2skips";

    public static final String GOOGLE_PLAY_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjcWnlkahMj3YLaOTzeqQrx72ZnAUwWlQtJ+vkmrJtcs43IHadshwOjtuLQ4K1Rx1JsRKTBuypp+6aDupbfZzd9rpAwP4a+fT6MAhnQUJXeBpckpfBS/OveOoktwNyHqRHvz2aJ72wix/f55O9mmLY+N81EJcvg4I/lOvlNySOZQyVnrNKepv/mN0f7NUc/J7rYK9nxjrZqda7vU+EuSSs7FgGA1O0jRrXdxFaDnuBAFVQH8aqvTYyKKrt+sopOZ+it7M0P08/VQq9fB1OUSQVllFkyTiJwBTHQzy288WM7ITnHJdW43DHNL9YvWu+/lhHZi5c3+EtH1yA3tvDcPEDQIDAQAB";


    private Color blue = new Color(50f / 255f, 130f / 255f, 200f / 255f, 1);

    public SplashScreen(final YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        startTime = System.currentTimeMillis();

        if (!game.prefs.contains("skips")) {
            game.prefs.putInteger("skips", 3);
            game.prefs.flush();
        }

        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sizeModifier = Math.min(screenSize.x, screenSize.y) / 1080f;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenSize.x, screenSize.y);

        splashTexture = new Texture(Gdx.files.internal("splash_logo.png"));
        splashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        game.manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        game.manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter bigFontParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        int bigFontSize = 208;
        bigFontParams.fontFileName = "arial.ttf";
        bigFontParams.fontParameters.color = Color.WHITE;
        bigFontParams.fontParameters.shadowColor = new Color(0, 0, 0, 0.5f);
        bigFontParams.fontParameters.kerning = false;
        bigFontParams.fontParameters.shadowOffsetX = Math.round((sizeModifier * bigFontSize) * 0.04f);
        bigFontParams.fontParameters.shadowOffsetY = Math.round((sizeModifier * bigFontSize) * 0.04f);
        bigFontParams.fontParameters.size = Math.round(bigFontSize * sizeModifier);
        game.manager.load("big_font.ttf", BitmapFont.class, bigFontParams);

        FreetypeFontLoader.FreeTypeFontLoaderParameter mediumFontParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        int mediumFontSize = 116;
        mediumFontParams.fontFileName = "arial.ttf";
        mediumFontParams.fontParameters.color = Color.WHITE;
        mediumFontParams.fontParameters.kerning = false;
        mediumFontParams.fontParameters.size = Math.round(sizeModifier * mediumFontSize);
        mediumFontParams.fontParameters.shadowColor = new Color(0, 0, 0, 0.5f);
        mediumFontParams.fontParameters.shadowOffsetX = Math.round((sizeModifier * mediumFontSize) * 0.05f);
        mediumFontParams.fontParameters.shadowOffsetY = Math.round((sizeModifier * mediumFontSize) * 0.05f);
        game.manager.load("medium_font.ttf", BitmapFont.class, mediumFontParams);

        FreetypeFontLoader.FreeTypeFontLoaderParameter mediumFontParamsNoShadow = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mediumFontParamsNoShadow.fontFileName = "arial.ttf";
        mediumFontParamsNoShadow.fontParameters.color = Color.WHITE;
        mediumFontParamsNoShadow.fontParameters.kerning = false;
        mediumFontParamsNoShadow.fontParameters.size = Math.round(sizeModifier * mediumFontSize);
        game.manager.load("medium_font_no_shadow.ttf", BitmapFont.class, mediumFontParamsNoShadow);

        FreetypeFontLoader.FreeTypeFontLoaderParameter smallFontParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        int smallFontSize = 96;
        smallFontParams.fontFileName = "arial.ttf";
        smallFontParams.fontParameters.color = Color.WHITE;
        smallFontParams.fontParameters.kerning = false;
        smallFontParams.fontParameters.size = Math.round(sizeModifier * smallFontSize);
        smallFontParams.fontParameters.shadowColor = new Color(0, 0, 0, 0.5f);
        smallFontParams.fontParameters.shadowOffsetX = Math.round((sizeModifier * smallFontSize) * 0.05f);
        smallFontParams.fontParameters.shadowOffsetY = Math.round((sizeModifier * smallFontSize) * 0.05f);
        game.manager.load("small_font.ttf", BitmapFont.class, smallFontParams);

        FreetypeFontLoader.FreeTypeFontLoaderParameter tinyFontParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        int tinyFontSize = 64;
        tinyFontParams.fontFileName = "arial.ttf";
        tinyFontParams.fontParameters.color = Color.WHITE;
        tinyFontParams.fontParameters.kerning = false;
        tinyFontParams.fontParameters.size = Math.round(sizeModifier * tinyFontSize);
        tinyFontParams.fontParameters.shadowColor = new Color(0, 0, 0, 0.5f);
        tinyFontParams.fontParameters.shadowOffsetX = Math.round((sizeModifier * tinyFontSize) * 0.05f);
        tinyFontParams.fontParameters.shadowOffsetY = Math.round((sizeModifier * tinyFontSize) * 0.05f);
        game.manager.load("tiny_font.ttf", BitmapFont.class, tinyFontParams);

        game.manager.load("pop.mp3", Sound.class);
        game.manager.load("click.mp3", Sound.class);
        game.manager.load("dot_white.png", Texture.class);
        game.manager.load("dot_shadow.png", Texture.class);
        game.manager.load("logo.png", Texture.class);
        game.manager.load("logo_horizontal.png", Texture.class);

        game.manager.load("back_icon.png", Texture.class);
        game.manager.load("back_icon_pressed.png", Texture.class);
        game.manager.load("next_icon.png", Texture.class);
        game.manager.load("next_icon_pressed.png", Texture.class);
        game.manager.load("customise_icon.png", Texture.class);
        game.manager.load("customise_icon_pressed.png", Texture.class);
        game.manager.load("home_icon.png", Texture.class);
        game.manager.load("home_icon_pressed.png", Texture.class);
        game.manager.load("levels_icon.png", Texture.class);
        game.manager.load("levels_icon_pressed.png", Texture.class);
        game.manager.load("pause_icon.png", Texture.class);
        game.manager.load("pause_icon_pressed.png", Texture.class);
        game.manager.load("random_icon.png", Texture.class);
        game.manager.load("random_icon_pressed.png", Texture.class);
        game.manager.load("remove_ads_icon.png", Texture.class);
        game.manager.load("remove_ads_icon_pressed.png", Texture.class);
        game.manager.load("restart_icon.png", Texture.class);
        game.manager.load("restart_icon_pressed.png", Texture.class);
        game.manager.load("resume_icon.png", Texture.class);
        game.manager.load("resume_icon_pressed.png", Texture.class);
        game.manager.load("settings_icon.png", Texture.class);
        game.manager.load("settings_icon_pressed.png", Texture.class);
        game.manager.load("disable_vibration_icon.png", Texture.class);
        game.manager.load("disable_vibration_icon_pressed.png", Texture.class);
        game.manager.load("enable_vibration_icon.png", Texture.class);
        game.manager.load("enable_vibration_icon_pressed.png", Texture.class);
        game.manager.load("mute_icon.png", Texture.class);
        game.manager.load("mute_icon_pressed.png", Texture.class);
        game.manager.load("unmute_icon.png", Texture.class);
        game.manager.load("unmute_icon_pressed.png", Texture.class);
        game.manager.load("rate_icon.png", Texture.class);
        game.manager.load("rate_icon_pressed.png", Texture.class);
        game.manager.load("twitter_icon.png", Texture.class);
        game.manager.load("twitter_icon_pressed.png", Texture.class);
        game.manager.load("contact_icon.png", Texture.class);
        game.manager.load("contact_icon_pressed.png", Texture.class);
        game.manager.load("restore_icon.png", Texture.class);
        game.manager.load("restore_icon_pressed.png", Texture.class);
        game.manager.load("shop_icon.png", Texture.class);
        game.manager.load("shop_icon_pressed.png", Texture.class);
        game.manager.load("skip_icon.png", Texture.class);
        game.manager.load("skip_icon_pressed.png", Texture.class);

        if (PurchaseSystem.hasManager()) {
            PurchaseManagerConfig config = new PurchaseManagerConfig();
            config.addOffer(new Offer().setType(OfferType.CONSUMABLE).setIdentifier(skips10ID));
            config.addOffer(new Offer().setType(OfferType.CONSUMABLE).setIdentifier(skips50ID));
            config.addOffer(new Offer().setType(OfferType.CONSUMABLE).setIdentifier(skips2ID));
            config.addStoreParam(PurchaseManagerConfig.STORE_NAME_ANDROID_GOOGLE, GOOGLE_PLAY_KEY);

            PurchaseObserver purchaseObserver = new PurchaseObserver() {
                @Override
                public void handleRestore (Transaction[] transactions) {

                }
                @Override
                public void handleRestoreError (Throwable e) {
                    throw new GdxRuntimeException(e);
                }

                @Override
                public void handleInstall () {

                }

                @Override
                public void handleInstallError (Throwable e) {
                    Gdx.app.log("ERROR", "PurchaseObserver: handleInstallError!: " + e.getMessage());
                    throw new GdxRuntimeException(e);
                }
                @Override
                public void handlePurchase (Transaction transaction) {
                    checkTransaction(transaction.getIdentifier(), false);
                }
                @Override
                public void handlePurchaseError (Throwable e) {	//--- Amazon IAP: this will be called for cancelled
                    throw new GdxRuntimeException(e);
                }

                @Override
                public void handlePurchaseCanceled () {	//will not be called by amazonIAP
                }
            };
            PurchaseSystem.install(purchaseObserver, config);
        }

        /*  Load Levels  */
        FileHandle levelFile = Gdx.files.internal("Levels.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(levelFile.read()));
        
        String line;
        int id = 0;
        try {
            while ((line = br.readLine()) != null)   {
              if (!line.startsWith("#")) {
                    String[] s = line.split(",");
                    Level level = new Level(id++, Integer.parseInt(s[0]), Float.parseFloat(s[1]) * sizeModifier, Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]));
                    game.levels.add(level);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        if (game.manager.update() && (System.currentTimeMillis() - startTime) > minSplashTime) {
            game.setScreen(new MainMenuScreen(game));
        }

        Gdx.gl.glClearColor(blue.r, blue.g, blue.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(splashTexture, Gdx.graphics.getWidth() / 2f - ((float) Gdx.graphics.getWidth() * 0.4f), Gdx.graphics.getHeight() / 2f - (Gdx.graphics.getWidth() / (float) splashTexture.getWidth() * splashTexture.getHeight() * 0.4f), Gdx.graphics.getWidth() * 0.8f, (Gdx.graphics.getWidth() / (float) splashTexture.getWidth()) * (float) splashTexture.getHeight() * 0.8f);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
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
        splashTexture.dispose();
    }

    public boolean checkTransaction (String id, Boolean restore) {
        boolean returnbool = false;
        System.out.println("Checking transaction");
        if (id.equals(skips10ID)) {
            System.out.println("Bought 10 skips");
            buySkips(10);
            returnbool = true;
        } else if (id.equals(skips50ID)) {
            System.out.println("Bought 50 skips");
            buySkips(50);
            returnbool = true;
        } else if (id.equals(skips2ID)) {
            System.out.println("Bought 2 skips");
            buySkips(2);
            returnbool = true;
        }
        return returnbool;
    }

    public void buySkips(int num){
        System.out.println("buySkips");

        game.prefs.putInteger("skips", game.prefs.getInteger("skips") + num);
        game.prefs.putBoolean("hasBought", true);
        game.prefs.flush();

        GDXButtonDialog dialog = game.dialogs.newDialog(GDXButtonDialog.class);
        dialog.setTitle("Purchase Successful!");
        dialog.setMessage("Thank you for supporting the game. " + num + " skips has been added and all ads has been removed.");
        dialog.setClickListener(new ButtonClickListener() {
            @Override
            public void click(int button) {

            }
        });
        dialog.addButton("Continue");
        dialog.build().show();
    }
}
