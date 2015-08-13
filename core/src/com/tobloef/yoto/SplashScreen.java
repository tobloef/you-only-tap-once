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

    private long minSplashTime = 2000L;
    private long startTime;

    private Color blue = new Color(60/255f, 145/255f, 215/255f, 1);

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
        sizeModifier = Math.min(screenSize.x, screenSize.y)/1080f;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenSize.x, screenSize.y);

        splashTexture = new Texture(Gdx.files.internal("splash_logo.png"));
        splashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        game.manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        game.manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter scoreFontParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        scoreFontParams.fontFileName = "arial.ttf";
        scoreFontParams.fontParameters.color = Color.WHITE;
        scoreFontParams.fontParameters.shadowColor = new Color(0,0,0,0.5f);
        scoreFontParams.fontParameters.kerning = false;
        scoreFontParams.fontParameters.shadowOffsetX = Math.round((sizeModifier*192)/30);
        scoreFontParams.fontParameters.shadowOffsetY = Math.round((sizeModifier*192)/30);
        scoreFontParams.fontParameters.size = Math.round(192*sizeModifier);
        game.manager.load("score_font.ttf", BitmapFont.class, scoreFontParams);

        FreetypeFontLoader.FreeTypeFontLoaderParameter menuFontParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        menuFontParams.fontFileName = "arial.ttf";
        menuFontParams.fontParameters.color = Color.WHITE;
        menuFontParams.fontParameters.size = Math.round(128*sizeModifier);
        game.manager.load("menu_font.ttf", BitmapFont.class, menuFontParams);


        game.manager.load("pop.mp3", Sound.class);
        game.manager.load("dot_white.png", Texture.class);
        game.manager.load("dot_shadow.png", Texture.class);
        game.manager.load("splash_logo.png", Texture.class);
        game.manager.load("restart_icon.png", Texture.class);
        //game.manager.load("back_icon.png", Texture.class);

        //TODO Move to level selection menu
        /*  Load Levels  */
        FileHandle levelFile = Gdx.files.internal("Levels.txt");
        int id = 0;
        for (String line : levelFile.readString().split("\\n")) {
            String[] s = line.split(",");
            Level level = new Level(id++, Integer.parseInt(s[0]), Float.parseFloat(s[1])/10f*sizeModifier, Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]));
            game.levels.add(level);
        }
    }

    @Override
    public void render(float delta) {
        if (game.manager.update() && (System.currentTimeMillis() - startTime) > minSplashTime) {
            //game.loadLevel(game.levelID);
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

    }
}
