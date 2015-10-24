package com.tobloef.yoto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class GameScreen implements Screen, InputProcessor {
    private final YouOnlyTapOnce game;
    private final Array<Dot> dots = new Array<>();
    private final Level level;
    private OrthographicCamera camera;
    private Color backgroundColor;
    private Texture dotTexture;
    private Texture shadowTexture;
    private Texture pauseTexture;
    private Texture pauseTexturePressed;
    private Texture nextTextureDisabled;
    private Texture nextTexture;
    private Texture nextTexturePressed;
    private Texture randomTexture;
    private Texture randomTexturePressed;
    private Texture resumeTexture;
    private Texture resumeTexturePressed;
    private Texture restartTexture;
    private Texture restartTexturePressed;
    private Texture levelsTexture;
    private Texture levelsTexturePressed;
    private Texture customiseTexture;
    private Texture customiseTexturePressed;
    private Texture settingsTexture;
    private Texture settingsTexturePressed;
    private Texture homeTexture;
    private Texture homeTexturePressed;
    private Texture skipTexture;
    private Texture skipTexturePressed;
    private Sound popSound;
    private Sound clickSound;
    private BitmapFont bigFont;
    private BitmapFont mediumFont;
    private BitmapFont tinyFont;
    private long timeSincePop;
    private boolean hasTouched = false;
    private boolean hasEnded = false;
    private boolean shouldEnd = false;
    private boolean completed = false;
    private boolean paused = false;
    private boolean justResumed = false;
    private int score = 0;
    private int scoreGoal;
    private boolean isMuted;
    private boolean doVibrate;
    private Stage stage;
    private Image tint;
    private Label scoreLabel;
    private ImageButton pauseButton;
    private Table pauseTable = new Table();
    private Table endTable = new Table();
    private ImageButtonStyle pauseButtonStyle;
    private ImageButtonStyle resumeButtonStyle;
    private ImageButtonStyle nextButtonStyle;
    private ImageButtonStyle randomButtonStyle;
    private ImageButtonStyle restartButtonStyle;
    private ImageButtonStyle levelsButtonStyle;
    private ImageButtonStyle customiseButtonStyle;
    private ImageButtonStyle settingsButtonStyle;
    private ImageButtonStyle homeButtonStyle;
    private ImageButtonStyle skipButtonStyle;
    private LabelStyle labelStyleBig;
    private LabelStyle labelStyleMedium;
    private LabelStyle labelStyleTiny;

    public GameScreen(final YouOnlyTapOnce game, Level level) {
        this.game = game;
        this.level = level;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        scoreGoal = Math.max(Math.round(level.count * level.completionPercentage), 1);
        isMuted = game.prefs.getBoolean("settingsMute");
        doVibrate = game.prefs.getBoolean("settingsVibrate");

        backgroundColor = game.blue;
        dotTexture = game.manager.get("dot_white.png", Texture.class);
        shadowTexture = game.manager.get("dot_shadow.png", Texture.class);
        pauseTexture = game.manager.get("pause_icon.png", Texture.class);
        pauseTexturePressed = game.manager.get("pause_icon_pressed.png", Texture.class);
        resumeTexture = game.manager.get("resume_icon.png", Texture.class);
        resumeTexturePressed = game.manager.get("resume_icon_pressed.png", Texture.class);
        nextTexture = game.manager.get("next_icon.png", Texture.class);
        nextTextureDisabled = game.manager.get("next_icon.png", Texture.class);
        nextTexturePressed = game.manager.get("next_icon_pressed.png", Texture.class);
        randomTexture = game.manager.get("random_icon.png", Texture.class);
        randomTexturePressed = game.manager.get("random_icon_pressed.png", Texture.class);
        restartTexture = game.manager.get("restart_icon.png", Texture.class);
        restartTexturePressed = game.manager.get("restart_icon_pressed.png", Texture.class);
        levelsTexture = game.manager.get("levels_icon.png", Texture.class);
        levelsTexturePressed = game.manager.get("levels_icon_pressed.png", Texture.class);
        customiseTexture = game.manager.get("customise_icon.png", Texture.class);
        customiseTexturePressed = game.manager.get("customise_icon_pressed.png", Texture.class);
        settingsTexture = game.manager.get("settings_icon.png", Texture.class);
        settingsTexturePressed = game.manager.get("settings_icon_pressed.png", Texture.class);
        homeTexture = game.manager.get("home_icon.png", Texture.class);
        homeTexturePressed = game.manager.get("home_icon_pressed.png", Texture.class);
        skipTexture = game.manager.get("skip_icon.png", Texture.class);
        skipTexturePressed = game.manager.get("skip_icon_pressed.png", Texture.class);
        popSound = game.manager.get("pop.mp3", Sound.class);
        clickSound = game.manager.get("click.mp3", Sound.class);
        timeSincePop = System.currentTimeMillis();
        bigFont = game.manager.get("big_font.ttf", BitmapFont.class);
        mediumFont = game.manager.get("medium_font.ttf", BitmapFont.class);
        tinyFont = game.manager.get("tiny_font.ttf", BitmapFont.class);

        dotTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        pauseTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pauseTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        resumeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        resumeTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        nextTextureDisabled.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        nextTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        nextTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        randomTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        randomTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        restartTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        restartTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        levelsTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        levelsTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        customiseTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        customiseTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        settingsTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        settingsTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        homeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        homeTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        skipTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        skipTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        pauseButtonStyle = new ImageButtonStyle();
        pauseButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(pauseTexture));
        pauseButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(pauseTexturePressed));

        resumeButtonStyle = new ImageButton.ImageButtonStyle();
        resumeButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(resumeTexture));
        resumeButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(resumeTexturePressed));

        nextButtonStyle = new ImageButton.ImageButtonStyle();
        nextButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(nextTexture));
        nextButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(nextTexturePressed));
        nextButtonStyle.disabled = new TextureRegionDrawable(new TextureRegion(nextTextureDisabled));

        randomButtonStyle = new ImageButton.ImageButtonStyle();
        randomButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(randomTexture));
        randomButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(randomTexturePressed));

        restartButtonStyle = new ImageButton.ImageButtonStyle();
        restartButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(restartTexture));
        restartButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(restartTexturePressed));

        levelsButtonStyle = new ImageButton.ImageButtonStyle();
        levelsButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(levelsTexture));
        levelsButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(levelsTexturePressed));

        customiseButtonStyle = new ImageButton.ImageButtonStyle();
        customiseButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(customiseTexture));
        customiseButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(customiseTexturePressed));

        settingsButtonStyle = new ImageButton.ImageButtonStyle();
        settingsButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(settingsTexture));
        settingsButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(settingsTexturePressed));

        homeButtonStyle = new ImageButton.ImageButtonStyle();
        homeButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(homeTexture));
        homeButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(homeTexturePressed));

        skipButtonStyle = new ImageButton.ImageButtonStyle();
        skipButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(skipTexture));
        skipButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(skipTexturePressed));

        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = bigFont;

        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = mediumFont;

        labelStyleTiny = new Label.LabelStyle();
        labelStyleTiny.font = tinyFont;

        pauseButton = new ImageButton(pauseButtonStyle);
        pauseButton.setSize(200f * game.sizeModifier, 200f * game.sizeModifier);
        pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - (15 * game.sizeModifier), Gdx.graphics.getHeight() - pauseButton.getHeight() - (15 * game.sizeModifier));
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseGame();
            }
        });

        scoreLabel = new Label(score + "/" + scoreGoal, labelStyleBig);
        scoreLabel.setHeight(128 * game.sizeModifier);
        scoreLabel.setPosition(25 * game.sizeModifier, Gdx.graphics.getHeight() - scoreLabel.getHeight() - (30 * game.sizeModifier));

        stage = new Stage(new ScreenViewport());
        stage.setDebugAll(false);
        stage.addActor(pauseButton);
        stage.addActor(scoreLabel);
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!hasTouched && !paused && !justResumed) {
                    dots.add(new Dot(new Vector2(x, y), level.maxSize));
                    hasTouched = true;
                }
                justResumed = false;
            }
        });
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK || keycode == Input.Keys.BACKSPACE) {
                    if (!paused) {
                        pauseGame();
                    } else {
                        resumeGame();
                    }
                }
                return true;
            }
        });

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.7f);
        pixmap.fill();
        tint = new Image(new Texture(pixmap));
        tint.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        tint.setVisible(false);
        stage.addActor(tint);

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        /*  Spawn the dots  */
        for (int i = 0; i < level.count; i++) {
            dots.add(new Dot(new Vector2(game.random.nextFloat() * (Gdx.graphics.getWidth() - ((dotTexture.getWidth() * level.dotSize / 2) * 2)) + (dotTexture.getWidth() * level.dotSize / 2),
                    game.random.nextFloat() * (Gdx.graphics.getHeight() - ((dotTexture.getWidth() * level.dotSize / 2) * 2)) + (dotTexture.getWidth() * level.dotSize / 2)),
                    new Vector2(game.random.nextFloat() * 2f - 1f, game.random.nextFloat() * 2f - 1f).nor(), level.speed, level.dotSize, level.maxSize));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);

        /*  Render Sprites  */
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        for (Dot dot : dots) {
            game.batch.draw(shadowTexture, (dot.position.x - dotTexture.getWidth() * dot.size / 2) + (dotTexture.getWidth() * dot.size * 0.2f * level.dotSize), (dot.position.y - dotTexture.getWidth() * dot.size / 2) - (dotTexture.getWidth() * 0.2f * level.dotSize), dotTexture.getWidth() * dot.size, dotTexture.getWidth() * dot.size);
        }
        for (Dot dot : dots) {
            game.batch.draw(dotTexture, dot.position.x - dotTexture.getWidth() * dot.size / 2, dot.position.y - dotTexture.getWidth() * dot.size / 2, dotTexture.getWidth() * dot.size, dotTexture.getWidth() * dot.size);
        }
        game.batch.end();

        if (!paused) {
            /*  Calculate Movement  */
            for (Dot dot : dots) {
                if (!dot.activated) {
                    Vector2 position = dot.position;
                    position.x += Math.min(Math.max(dot.direction.x * Gdx.graphics.getDeltaTime() * 100, -1), 1) * level.speed * game.sizeModifier;
                    position.y += Math.min(Math.max(dot.direction.y * Gdx.graphics.getDeltaTime() * 100, -1), 1) * level.speed * game.sizeModifier;
                    dot.position = position;

                    /*  Physics  */
                    if (dot.position.x + (dotTexture.getWidth() * dot.size / 2) >= Gdx.graphics.getWidth()) {
                        Vector2 direction = dot.direction;
                        direction.x = -Math.abs(direction.x);
                        dot.direction = direction;
                    }
                    if (dot.position.y + (dotTexture.getWidth() * dot.size / 2) >= Gdx.graphics.getHeight()) {
                        Vector2 direction = dot.direction;
                        direction.y = -Math.abs(direction.y);
                        dot.direction = direction;
                    }
                    if (dot.position.x <= 0 + (dotTexture.getWidth() * dot.size / 2)) {
                        Vector2 direction = dot.direction;
                        direction.x = Math.abs(direction.x);
                        dot.direction = direction;
                    }
                    if (dot.position.y <= 0 + (dotTexture.getWidth() * dot.size / 2)) {
                        Vector2 direction = dot.direction;
                        direction.y = Math.abs(direction.y);
                        dot.direction = direction;
                    }
                } else {

                    /*  Dot Collision  */
                    for (int i = 0; i < dots.size; i++) {
                        if (!dots.get(i).activated && dots.get(i) != dot) {
                            if (dot.position.dst(dots.get(i).position) < ((dotTexture.getWidth() * dot.size) / 2) + ((dotTexture.getWidth() * dots.get(i).size) / 2)) {
                                if (!isMuted && System.currentTimeMillis() - timeSincePop > 50) {
                                    timeSincePop = System.currentTimeMillis();
                                    popSound.play(0.5f, game.random.nextFloat() * (1.25f - 0.75f) + 0.5f, 0);
                                }
                                dots.get(i).activated = true;
                                dots.get(i).state = 1;
                                score += 1;
                                scoreLabel.setText(score + "/" + scoreGoal);
                            }
                        }
                    }
                }

                /*  Expand/Shrink  */
                if (dot.state != 0) {
                    dot.size += dot.state * Gdx.graphics.getDeltaTime() * 0.5f;
                    if (dot.size < 0) {
                        dots.removeValue(dot, true);
                    }
                    if (dot.size > dot.maxSize) {
                        dot.state = 0;
                        if (!dot.shouldCount) {
                            dot.shouldCount = true;
                        }
                    }
                }
                if (dot.shouldCount) {
                    dot.lifetime += Gdx.graphics.getDeltaTime();
                    if (dot.lifetime > 1.5f) {
                        dot.shouldCount = false;
                        dot.state = -1;
                    }
                }
            }

            /*  Level Completion  */
            if (hasTouched && !hasEnded) {
                if (!shouldEnd) {
                    if (score >= scoreGoal) {
                        completed = true;
                        if (game.prefs.getInteger("levelsAvailable") == level.levelID) {
                            game.prefs.putInteger("levelsAvailable", level.levelID + 1);
                            game.prefs.flush();
                        }
                    }
                    shouldEnd = true;
                    for (Dot dot : dots) {
                        if (dot.activated) {
                            shouldEnd = false;
                            break;
                        }
                    }
                } else {
                    for (Dot dot : dots) {
                        dot.state = -1;
                    }
                }
                if (dots.size == 0) {
                    hasEnded = true;
                    endGame();
                }
            }
            if (completed) {
                backgroundColor.lerp(game.green, Gdx.graphics.getDeltaTime() * 5f);
            }
            if (!completed && shouldEnd) {
                backgroundColor.lerp(game.red, Gdx.graphics.getDeltaTime() * 5f);
            }
        }

        stage.draw();
    }

    private void pauseGame() {
        if (!hasEnded) {
            paused = true;
            tint.setVisible(true);
            pauseTable.setFillParent(true);
            popSound.pause();

            Label pauseLabel = new Label("Paused", labelStyleBig);

            ImageButton resumeButton = new ImageButton(resumeButtonStyle);
            resumeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    resumeGame();
                }
            });
            Label resumeLabel = new Label("Resume", labelStyleMedium);

            ImageButton restartButton = new ImageButton(restartButtonStyle);
            restartButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!isMuted) {
                        clickSound.play();
                    }
                    if (doVibrate) {
                        Gdx.input.vibrate(25);
                    }
                    game.setScreen(new GameScreen(game, level));
                }
            });
            Label restartLabel = new Label("Restart", labelStyleMedium);

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

            ImageButton homeButton = new ImageButton(homeButtonStyle);
            homeButton.addListener(new ClickListener() {
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
            Label homeLabel = new Label("Home", labelStyleMedium);

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
                    game.setScreen(new GameScreen(game, game.randomLevel()));
                }
            });
            Label randomLabel = new Label("New", labelStyleMedium);

            ImageButton customiseButton = new ImageButton(customiseButtonStyle);
            customiseButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!isMuted) {
                        clickSound.play();
                    }
                    if (doVibrate) {
                        Gdx.input.vibrate(25);
                    }
                    GDXButtonDialog dialog = game.dialogs.newDialog(GDXButtonDialog.class);
                    dialog.setTitle("Coming Soon!");
                    dialog.setMessage("Custom levels are still being worked on, so look forward to the next update!");

                    dialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {

                        }
                    });

                    dialog.addButton("Will do!");

                    dialog.build().show();
                }
            });
            Label customiseLabel = new Label("Edit", labelStyleMedium);
            customiseLabel.setAlignment(1);

            pauseTable.add(pauseLabel).colspan(2).padTop(game.sizeModifier * 250).padBottom(game.sizeModifier * 150);
            pauseTable.row();
            pauseTable.add(resumeButton).size(game.sizeModifier * 230);
            pauseTable.add(restartButton).size(game.sizeModifier * 230);
            pauseTable.row();
            pauseTable.add(resumeLabel).padTop(game.sizeModifier * 20).padBottom(game.sizeModifier * 130).top();
            pauseTable.add(restartLabel).padTop(game.sizeModifier * 20).padBottom(game.sizeModifier * 130).top();
            pauseTable.row();
            if (level.levelID != -1) {
                pauseTable.add(levelsButton).size(game.sizeModifier * 270);
            } else {
                pauseTable.add(randomButton).size(game.sizeModifier * 270);
            }
            pauseTable.add(homeButton).size(game.sizeModifier * 250);
            pauseTable.row();
            if (level.levelID != -1) {
                pauseTable.add(levelsLabel).expand().padTop(game.sizeModifier * 20).top();
            } else {
                pauseTable.add(randomLabel).expand().padTop(game.sizeModifier * 20).top();
            }
            pauseTable.add(homeLabel).expand().padTop(game.sizeModifier * 20).top();
            stage.addActor(pauseTable);
        }
        if (!isMuted) {
            clickSound.play();
        }
        if (doVibrate) {
            Gdx.input.vibrate(25);
        }
    }

    private void endGame() {
        scoreLabel.setVisible(false);
        pauseButton.setVisible(false);

        endTable.setFillParent(true);
        endTable.setDebug(false);
        Label scoreTitleLabel;
        if (level.levelID == -1) {
            scoreTitleLabel = new Label("Score", labelStyleMedium);
        } else {
            scoreTitleLabel = new Label("Level " + (level.levelID + 1), labelStyleMedium);
        }
        scoreTitleLabel.sizeBy(3);
        Label scoreLabel = new Label(score + "/" + scoreGoal, labelStyleBig);

        ImageButton restartButton = new ImageButton(restartButtonStyle);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                game.setScreen(new GameScreen(game, level));
            }
        });
        Label restartLabel = new Label("Restart", labelStyleMedium);

        ImageButton nextButton = new ImageButton(nextButtonStyle);
        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                if (level.levelID + 1 < game.levels.size()) {
                    if (game.prefs.getBoolean("remindRate") && level.levelID <= game.levels.size() && (level.levelID + 1) % 20 == 0) {
                        GDXButtonDialog dialog = game.dialogs.newDialog(GDXButtonDialog.class);
                        dialog.setTitle("Like this game?");
                        dialog.setMessage("Congratulations on clearing level " + (level.levelID + 1) + "! Would you like to rate the game on Google Play?");

                        dialog.setClickListener(new ButtonClickListener() {

                            @Override
                            public void click(int button) {
                                if (button == 1) {
                                    Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.tobloef.yoto");
                                } else if (button == 0) {
                                    game.prefs.putBoolean("remindRate", false);
                                    game.prefs.flush();
                                }
                            }
                        });

                        dialog.addButton("Never");
                        dialog.addButton("Sure!");
                        dialog.addButton("Later");

                        dialog.build().show();
                    }
                    game.setScreen(new GameScreen(game, game.levels.get(level.levelID + 1)));
                } else {
                    GDXButtonDialog dialog = game.dialogs.newDialog(GDXButtonDialog.class);
                    dialog.setTitle("No more levels");
                    dialog.setMessage("You've cleared the last level. Until new levels are released, how about rating this game on Google Play?");

                    dialog.setClickListener(new ButtonClickListener() {

                        @Override
                        public void click(int button) {
                            if (button == 0) {
                                Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.tobloef.yoto");
                            }
                        }
                    });

                    dialog.addButton("Sure!");
                    dialog.addButton("No");

                    dialog.build().show();
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        });
        Label nextLabel = new Label("Next", labelStyleMedium);

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
                game.setScreen(new GameScreen(game, game.randomLevel()));
            }
        });
        Label randomLabel = new Label("New", labelStyleMedium);

        ImageButton skipButton = new ImageButton(skipButtonStyle);
        skipButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMuted) {
                    clickSound.play();
                }
                if (doVibrate) {
                    Gdx.input.vibrate(25);
                }
                if (game.prefs.getInteger("skips") > 0) {
                    game.prefs.putInteger("skips", game.prefs.getInteger("skips") - 1);
                    game.prefs.putInteger("levelsAvailable", game.prefs.getInteger("levelsAvailable") + 1);
                    game.prefs.flush();
                    if (level.levelID < game.levels.size() - 1) {
                        game.setScreen(new GameScreen(game, game.levels.get(level.levelID + 1)));
                    } else {
                        GDXButtonDialog dialog = game.dialogs.newDialog(GDXButtonDialog.class);
                        dialog.setTitle("No more levels");
                        dialog.setMessage("You've cleared the last level. Until new levels are released, how about rating this game on Google Play?");

                        dialog.setClickListener(new ButtonClickListener() {

                            @Override
                            public void click(int button) {
                                if (button == 0) {
                                    Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.tobloef.yoto");
                                }
                            }
                        });

                        dialog.addButton("Sure!");
                        dialog.addButton("No");

                        dialog.build().show();
                        game.setScreen(new MainMenuScreen(game));
                    }
                } else {
                    GDXButtonDialog dialog = game.dialogs.newDialog(GDXButtonDialog.class);
                    dialog.setTitle("No skips left");
                    dialog.setMessage("You currently have no skips left. Would you like to buy some?");

                    dialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            if (button == 1) {
                                game.setScreen(new ShopScreen(game));
                            }
                        }
                    });

                    dialog.addButton("No");
                    dialog.addButton("Yes");

                    dialog.build().show();
                }
            }
        });
        Label skipLabel = new Label("Skip", labelStyleMedium);
        Label skipsLeftLabel = new Label(game.prefs.getInteger("skips") + " left", labelStyleTiny);

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

        ImageButton homeButton = new ImageButton(homeButtonStyle);
        homeButton.addListener(new ClickListener() {
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
        Label homeLabel = new Label("Home", labelStyleMedium);


        endTable.add(scoreTitleLabel).colspan(2).padTop(game.sizeModifier * 190);
        endTable.row();
        endTable.add(scoreLabel).colspan(2).padBottom(game.sizeModifier * 70);
        endTable.row();
        endTable.add(restartButton).size(game.sizeModifier * 250).uniformX().expandX().padLeft(game.sizeModifier * 50);
        if (level.levelID == -1) {
            endTable.add(randomButton).size(game.sizeModifier * 270).uniformX().expandX().padRight(game.sizeModifier * 50);
        } else {
            if (score < scoreGoal && game.prefs.getInteger("levelsAvailable") == level.levelID) {
                endTable.add(skipButton).size(game.sizeModifier * 270).uniformX().expandX().padRight(game.sizeModifier * 50);
            } else {
                endTable.add(nextButton).size(game.sizeModifier * 310).uniformX().expandX().padRight(game.sizeModifier * 50);
            }
        }
        endTable.row();
        endTable.add(restartLabel).padBottom(game.sizeModifier * 100).top().expandX().uniformX().padLeft(game.sizeModifier * 50);

        if (level.levelID == -1) {
            endTable.add(randomLabel).padBottom(game.sizeModifier * 100).top().expandX().uniformX().padRight(game.sizeModifier * 50);
        } else {
            if (score < scoreGoal && game.prefs.getInteger("levelsAvailable") == level.levelID) {
                endTable.add(skipLabel).top().expandX().uniformX().padRight(game.sizeModifier * 50);
                endTable.row();
                endTable.add();
                endTable.add(skipsLeftLabel).padBottom(game.sizeModifier * 70).padTop(game.sizeModifier * -100).uniformX().padRight(game.sizeModifier * 50);
            } else {
                endTable.add(nextLabel).padBottom(game.sizeModifier * 100).top().expandX().uniformX().padRight(game.sizeModifier * 50);
            }
        }
        endTable.row();
        endTable.add(homeButton).size(game.sizeModifier * 270).expandX().uniformX().padLeft(game.sizeModifier * 50);
        endTable.add(settingsButton).expandX().size(game.sizeModifier * 270).uniformX().padRight(game.sizeModifier * 50);
        endTable.row();
        endTable.add(homeLabel).top().expand().uniformX().padLeft(game.sizeModifier * 50);
        endTable.add(settingsLabel).top().expand().uniformX().padRight(game.sizeModifier * 50);
        stage.addActor(endTable);
    }

    private void resumeGame() {
        paused = false;
        popSound.resume();
        tint.setVisible(false);
        pauseTable.remove();
        pauseTable = new Table();
        justResumed = true;
        if (!isMuted) {
            clickSound.play();
        }
        if (doVibrate) {
            Gdx.input.vibrate(25);
        }
    }

    @Override
    public void resize(int width, int height) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height, true);
        pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - (15 * game.sizeModifier), Gdx.graphics.getHeight() - pauseButton.getHeight() - (15 * game.sizeModifier));
        scoreLabel.setPosition(25 * game.sizeModifier, Gdx.graphics.getHeight() - scoreLabel.getHeight() - (30 * game.sizeModifier));
        tint.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (paused) {
            resumeGame();
            pauseGame();
        } else if (hasEnded) {
            endTable.remove();
            endTable = new Table();
            endGame();
        }
        game.sizeModifier = Math.min(width, height) / 1080f;
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
        dotTexture.dispose();
        shadowTexture.dispose();
        pauseTexture.dispose();
        pauseTexturePressed.dispose();
        nextTextureDisabled.dispose();
        nextTexture.dispose();
        nextTexturePressed.dispose();
        randomTexture.dispose();
        randomTexturePressed.dispose();
        resumeTexture.dispose();
        resumeTexturePressed.dispose();
        restartTexture.dispose();
        restartTexturePressed.dispose();
        levelsTexture.dispose();
        levelsTexturePressed.dispose();
        customiseTexture.dispose();
        customiseTexturePressed.dispose();
        settingsTexture.dispose();
        settingsTexturePressed.dispose();
        homeTexture.dispose();
        homeTexturePressed.dispose();
        popSound.dispose();
        clickSound.dispose();
        bigFont.dispose();
        mediumFont.dispose();
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
