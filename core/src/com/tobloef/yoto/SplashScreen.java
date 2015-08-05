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

    private long startTime;

    private Color blue = new Color(60/255f, 145/255f, 215/255f, 1);

    public SplashScreen(final YouOnlyTapOnce game) {
        this.game = game;
    }

    @Override
    public void show() {
        startTime = System.currentTimeMillis();

        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sizeModifier = Math.min(screenSize.x, screenSize.y)/1080f;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenSize.x, screenSize.y);

        splashTexture = new Texture(Gdx.files.internal("splash_logo.png"));
        splashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        game.manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        game.manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter size256Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        size256Params.fontFileName = "arial.ttf";
        size256Params.fontParameters.color = Color.WHITE;
        size256Params.fontParameters.shadowColor = new Color(0,0,0,0.5f);
        size256Params.fontParameters.kerning = false;
        size256Params.fontParameters.shadowOffsetX = Math.round((sizeModifier*256)/30);
        size256Params.fontParameters.shadowOffsetY = Math.round((sizeModifier*256)/30);
        size256Params.fontParameters.size = Math.round(256*sizeModifier);
        game.manager.load("arial256.ttf", BitmapFont.class, size256Params);

        game.manager.load("pop.mp3", Sound.class);
        game.manager.load("dot_white.png", Texture.class);
        game.manager.load("dot_shadow.png", Texture.class);

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
        if (game.manager.update() && (System.currentTimeMillis() - startTime) > 2000L) {
            game.loadLevel(game.levelID);
        }

        Gdx.gl.glClearColor(blue.r, blue.g, blue.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(splashTexture, screenSize.x/2 - (splashTexture.getWidth()*sizeModifier / 2), screenSize.y/2 - (splashTexture.getHeight()*sizeModifier / 2), splashTexture.getWidth()*sizeModifier, splashTexture.getHeight()*sizeModifier);
        game.batch.end();
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
