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

public class SplashScreen implements Screen {
    YouOnlyTapOnce game;
    private Texture splashTexture;
    private float sizeModifier;
    private Vector2 screenSize;
    private OrthographicCamera camera;

    private long minSplashTime = 000L;
    private long startTime;

    private Color blue = new Color(50f / 255f, 130f / 255f, 200f / 255f, 1);

    public SplashScreen(final YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        startTime = System.currentTimeMillis();

        if (!game.prefs.contains("levelsAvailable")) {
            game.prefs.putInteger("levelsAvailable", 0);
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

        FreetypeFontLoader.FreeTypeFontLoaderParameter mediumFontParamsNoShadow = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mediumFontParamsNoShadow.fontFileName = "arial.ttf";
        mediumFontParamsNoShadow.fontParameters.color = Color.WHITE;
        mediumFontParamsNoShadow.fontParameters.kerning = false;
        mediumFontParamsNoShadow.fontParameters.size = Math.round(sizeModifier * mediumFontSize);
        game.manager.load("medium_font_no_shadow.ttf", BitmapFont.class, mediumFontParamsNoShadow);

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

        /*  Load Levels  */
        FileHandle levelFile = Gdx.files.internal("Levels.txt");
        int id = 0;
        for (String line : levelFile.readString().split("\\n")) {
            if (!line.startsWith("#")) {
                String[] s = line.split(",");
                Level level = new Level(id++, Integer.parseInt(s[0]), Float.parseFloat(s[1]) * sizeModifier, Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]));
                game.levels.add(level);
            }
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
}
