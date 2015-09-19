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

public class GameScreen implements Screen, InputProcessor {
    final YouOnlyTapOnce game;
    private OrthographicCamera camera;

    private Color backgroundColor;
    private Color blue = new Color(50f/255f, 130f/255f, 200f/255f, 1);
    private Color green = new Color(75f/255f, 175f/255f, 75f/255f, 1);
    private Color red = new Color(190f/255f, 60f/255f, 60f/255f, 1);

    private Texture dotTexture;
    private Texture shadowTexture;
    private Texture pauseTexture;
    private Texture pauseTexturePressed;
    private Texture nextTexture;
    private Texture nextTexturePressed;
    private Texture resumeTexture;
    private Texture resumeTexturePressed;
    private Texture restartTexture;
    private Texture restartTexturePressed;
    private Texture levelsTexture;
    private Texture levelsTexturePressed;
    private Texture customizeTexture;
    private Texture customizeTexturePressed;
    private Texture settingsTexture;
    private Texture settingsTexturePressed;
    private Texture homeTexture;
    private Texture homeTexturePressed;
    private Sound popSound;
    private BitmapFont bigFont;
    private BitmapFont mediumFont;

    private Array<Dot> dots = new Array<Dot>();
    private long timeSincePop;
    private boolean hasTouched = false;
    private boolean hasEnded = false;
    private boolean shouldEnd = false;
    private boolean completed = false;
    private boolean paused = false;
    private boolean justResumed = false;
    private int score = 0;
    private int scoreGoal;

    private Stage stage;
    private Image tint;
    private Label scoreLabel;
    private ImageButton pauseButton;
    private Table pauseTable = new Table();
    private Table endTable = new Table();

    ImageButtonStyle pauseButtonStyle;
    ImageButtonStyle resumeButtonStyle;
    ImageButtonStyle nextButtonStyle;
    ImageButtonStyle restartButtonStyle;
    ImageButtonStyle levelsButtonStyle;
    ImageButtonStyle customizeButtonStyle;
    ImageButtonStyle settingsButtonStyle;
    ImageButtonStyle homeButtonStyle;
    LabelStyle labelStyleBig;
    LabelStyle labelStyleMedium;

    private Level level;

    public GameScreen(final YouOnlyTapOnce game, Level level) {
        this.game = game;
        this.level = level;
    }

    @Override
    public void show() {
        game.screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.screenSize.x, game.screenSize.y);
        scoreGoal = Math.round(level.count * level.completionPercentage);

        backgroundColor = blue;
        dotTexture = game.manager.get("dot_white.png", Texture.class);
        shadowTexture = game.manager.get("dot_shadow.png", Texture.class);
        pauseTexture = game.manager.get("pause_icon.png", Texture.class);
        pauseTexturePressed = game.manager.get("pause_icon_pressed.png", Texture.class);
        resumeTexture = game.manager.get("resume_icon.png", Texture.class);
        resumeTexturePressed = game.manager.get("resume_icon_pressed.png", Texture.class);
        nextTexture = game.manager.get("next_icon.png", Texture.class);
        nextTexturePressed = game.manager.get("next_icon_pressed.png", Texture.class);
        restartTexture = game.manager.get("restart_icon.png", Texture.class);
        restartTexturePressed = game.manager.get("restart_icon_pressed.png", Texture.class);
        levelsTexture = game.manager.get("levels_icon.png", Texture.class);
        levelsTexturePressed = game.manager.get("levels_icon_pressed.png", Texture.class);
        customizeTexture = game.manager.get("customize_icon.png", Texture.class);
        customizeTexturePressed = game.manager.get("customize_icon_pressed.png", Texture.class);
        settingsTexture = game.manager.get("settings_icon.png", Texture.class);
        settingsTexturePressed = game.manager.get("settings_icon_pressed.png", Texture.class);
        homeTexture = game.manager.get("home_icon.png", Texture.class);
        homeTexturePressed = game.manager.get("home_icon_pressed.png", Texture.class);
        popSound = game.manager.get("pop.mp3", Sound.class);
        timeSincePop = System.currentTimeMillis();
        bigFont = game.manager.get("big_font.ttf", BitmapFont.class);
        mediumFont = game.manager.get("medium_font.ttf", BitmapFont.class);

        pauseTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pauseTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        resumeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        resumeTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        nextTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        nextTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        restartTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        restartTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        levelsTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        levelsTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        customizeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        customizeTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        settingsTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        settingsTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        homeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        homeTexturePressed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        pauseButtonStyle = new ImageButtonStyle();
        pauseButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(pauseTexture));
        pauseButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(pauseTexturePressed));

        resumeButtonStyle = new ImageButton.ImageButtonStyle();
        resumeButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(resumeTexture));
        resumeButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(resumeTexturePressed));

        nextButtonStyle = new ImageButton.ImageButtonStyle();
        nextButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(nextTexture));
        nextButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(nextTexturePressed));

        restartButtonStyle = new ImageButton.ImageButtonStyle();
        restartButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(restartTexture));
        restartButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(restartTexturePressed));

        levelsButtonStyle = new ImageButton.ImageButtonStyle();
        levelsButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(levelsTexture));
        levelsButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(levelsTexturePressed));

        customizeButtonStyle = new ImageButton.ImageButtonStyle();
        customizeButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(customizeTexture));
        customizeButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(customizeTexturePressed));

        settingsButtonStyle = new ImageButton.ImageButtonStyle();
        settingsButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(settingsTexture));
        settingsButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(settingsTexturePressed));

        homeButtonStyle = new ImageButton.ImageButtonStyle();
        homeButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(homeTexture));
        homeButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(homeTexturePressed));

        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = bigFont;

        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = mediumFont;

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
        scoreLabel.setPosition(25 * game.sizeModifier, game.screenSize.y - scoreLabel.getHeight() - (30 * game.sizeModifier));

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
                    //TODO Show either pause menu or exit confirmation
                    if (!paused) {
                        game.setScreen(new MainMenuScreen(game));
                    } else {
                        resumeGame();
                    }
                }
                return true;
            }
        });

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888 );
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
            dots.add(new Dot(new Vector2(game.random.nextFloat() * (game.screenSize.x - ((dotTexture.getWidth() * level.dotSize/2) * 2)) + (dotTexture.getWidth() * level.dotSize/2),
                    game.random.nextFloat() * (game.screenSize.y - ((dotTexture.getWidth() * level.dotSize/2) * 2)) + (dotTexture.getWidth() * level.dotSize/2)),
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
        for (Dot dot: dots) {
            game.batch.draw(shadowTexture, (dot.position.x - dotTexture.getWidth() * dot.size / 2) + (dotTexture.getWidth() * dot.size * 0.2f * level.dotSize), (dot.position.y - dotTexture.getWidth() * dot.size / 2) - (dotTexture.getWidth() * 0.2f * level.dotSize), dotTexture.getWidth() * dot.size, dotTexture.getWidth() * dot.size);
        }
        for (Dot dot: dots) {
            game.batch.draw(dotTexture, dot.position.x - dotTexture.getWidth() * dot.size / 2, dot.position.y - dotTexture.getWidth() * dot.size / 2, dotTexture.getWidth() * dot.size, dotTexture.getWidth() * dot.size);
        }
        game.batch.end();

        if (!paused) {
            /*  Calculate Movement  */
            for (Dot dot : dots) {
                if (!dot.activated) {
                    Vector2 position = dot.position;
                    position.x += Math.min(Math.max(dot.direction.x * Gdx.graphics.getDeltaTime() * 100, -1), 1) * level.speed;
                    position.y += Math.min(Math.max(dot.direction.y * Gdx.graphics.getDeltaTime() * 100, -1), 1) * level.speed;
                    dot.position = position;

                    /*  Physics  */
                    if (dot.position.x <= 0 + (dotTexture.getWidth() * dot.size / 2) || dot.position.x + (dotTexture.getWidth() * dot.size / 2) >= game.screenSize.x) {
                        Vector2 direction = dot.direction;
                        direction.x = -direction.x;
                        dot.direction = direction;
                    }
                    if (dot.position.y <= 0 + (dotTexture.getWidth() * dot.size / 2) || dot.position.y + (dotTexture.getWidth() * dot.size / 2) >= game.screenSize.y) {
                        Vector2 direction = dot.direction;
                        direction.y = -direction.y;
                        dot.direction = direction;
                    }
                } else {

                    /*  Dot Collision  */
                    for (int i = 0; i < dots.size; i++) {
                        if (!dots.get(i).activated && dots.get(i) != dot) {
                            if (dot.position.dst(dots.get(i).position) < ((dotTexture.getWidth() * dot.size) / 2) + ((dotTexture.getWidth() * dots.get(i).size) / 2)) {
                                if (System.currentTimeMillis() - timeSincePop > 50) {
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
                    if (shouldEnd) {
                        for (Dot dot : dots) {
                            dot.state = -1;
                        }
                    }
                }
                if (dots.size == 0) {
                    hasEnded = true;
                }
            }
            if (completed && backgroundColor != green) {
                backgroundColor.lerp(green, Gdx.graphics.getDeltaTime() * 4f);
            }
            if (!completed && shouldEnd && backgroundColor != red) {
                backgroundColor.lerp(red, Gdx.graphics.getDeltaTime() * 4f);
            }
        }

        stage.draw();
    }

    public void pauseGame() {
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
                game.setScreen(new GameScreen(game, level));
            }
        });
        Label restartLabel = new Label("Restart", labelStyleMedium);
        ImageButton levelsButton = new ImageButton(levelsButtonStyle);
        levelsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });
        Label levelsLabel = new Label("Levels", labelStyleMedium);
        ImageButton settingsButton = new ImageButton(settingsButtonStyle);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        Label settingsLabel = new Label("Settings", labelStyleMedium);

        if (game.screenSize.x > game.screenSize.y) {
            pauseTable.add(pauseLabel).colspan(4).padBottom(game.sizeModifier * 130);
            pauseTable.row();
            pauseTable.add(resumeButton).expandX().size(game.sizeModifier * 200).uniformX();
            pauseTable.add(restartButton).expandX().size(game.sizeModifier * 200).uniformX();
            pauseTable.add(levelsButton).expandX().size(game.sizeModifier * 200).uniformX();
            pauseTable.add(settingsButton).expandX().size(game.sizeModifier * 220).uniformX();
            pauseTable.row();
            pauseTable.add(resumeLabel).padTop(game.sizeModifier * 20).expandX().padBottom(game.sizeModifier * 220).uniformX();
            pauseTable.add(restartLabel).padTop(game.sizeModifier * 20).expandX().padBottom(game.sizeModifier * 220).uniformX();
            pauseTable.add(levelsLabel).padTop(game.sizeModifier * 20).expandX().padBottom(game.sizeModifier * 220).uniformX();
            pauseTable.add(settingsLabel).padTop(game.sizeModifier * 20).expandX().padBottom(game.sizeModifier * 220).uniformX();
        } else {
            pauseTable.add(pauseLabel).colspan(2).padTop(game.sizeModifier * 280).padBottom(game.sizeModifier * 210);
            pauseTable.row();
            pauseTable.add(resumeButton).size(game.sizeModifier * 200);
            pauseTable.add(restartButton).size(game.sizeModifier * 200);
            pauseTable.row();
            pauseTable.add(resumeLabel).padTop(game.sizeModifier * 20).padBottom(game.sizeModifier * 140).top();
            pauseTable.add(restartLabel).padTop(game.sizeModifier * 20).padBottom(game.sizeModifier * 140).top();
            pauseTable.row();
            pauseTable.add(levelsButton).size(game.sizeModifier * 200);
            pauseTable.add(settingsButton).size(game.sizeModifier * 200);
            pauseTable.row();
            pauseTable.add(levelsLabel).expand().padTop(game.sizeModifier * 20).padBottom(game.sizeModifier * 140).top();
            pauseTable.add(settingsLabel).expand().padTop(game.sizeModifier * 20).padBottom(game.sizeModifier * 140).top();
        }
        stage.addActor(pauseTable);
    }

    public void endGame() {
        endTable.setFillParent(true);
        Label scoreTitleLabel = new Label("Score", labelStyleMedium);
        Label scoreLabel = new Label(score + "/" + scoreGoal, labelStyleBig);

        ImageButton restartButton = new ImageButton(restartButtonStyle);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, level));
            }
        });
        Label restartLabel = new Label("Restart", labelStyleMedium);

        ImageButton nextButton = new ImageButton(nextButtonStyle);
        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeGame();
            }
        });
        Label nextLabel = new Label("Next", labelStyleMedium);

        ImageButton levelsButton = new ImageButton(levelsButtonStyle);
        levelsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });
        Label levelsLabel = new Label("Levels", labelStyleMedium);

        ImageButton customizeButton = new ImageButton(customizeButtonStyle);
        customizeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeGame();
            }
        });
        Label customizeLabel = new Label("Customize", labelStyleMedium);

        ImageButton settingsButton = new ImageButton(settingsButtonStyle);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        Label settingsLabel = new Label("Settings", labelStyleMedium);

        ImageButton homeButton = new ImageButton(homeButtonStyle);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeGame();
            }
        });
        Label homeLabel = new Label("Next", labelStyleMedium);

        if (game.screenSize.x > game.screenSize.y) {
            endTable.add(scoreTitleLabel).colspan(6).padBottom(game.sizeModifier * 0);
            endTable.row();
            endTable.add(scoreLabel).colspan(6).padBottom(game.sizeModifier * 0);
            endTable.row();
            if (score <= scoreGoal) {
                endTable.add(nextButton).expandX().size(game.sizeModifier * 200).uniformX();

            } else {
                endTable.add(restartButton).expandX().size(game.sizeModifier * 200).uniformX();

            }
            endTable.add(levelsButton).expandX().size(game.sizeModifier * 200).uniformX();
            endTable.add(customizeButton).expandX().size(game.sizeModifier * 200).uniformX();
            endTable.add(settingsButton).expandX().size(game.sizeModifier * 220).uniformX();
            endTable.add(homeButton).expandX().size(game.sizeModifier * 220).uniformX();
            endTable.row();
            if (score <= scoreGoal) {
                endTable.add(nextLabel).padTop(game.sizeModifier * 20).expandX().padBottom(game.sizeModifier * 220).uniformX();
            } else {
                endTable.add(restartLabel).padTop(game.sizeModifier * 20).expandX().padBottom(game.sizeModifier * 220).uniformX();

            }
            endTable.add(levelsLabel).padTop(game.sizeModifier * 20).expandX().padBottom(game.sizeModifier * 220).uniformX();
            endTable.add(customizeLabel).padTop(game.sizeModifier * 20).expandX().padBottom(game.sizeModifier * 220).uniformX();
            endTable.add(settingsLabel).padTop(game.sizeModifier * 20).expandX().padBottom(game.sizeModifier * 220).uniformX();
            endTable.add(homeLabel).padTop(game.sizeModifier * 20).expandX().padBottom(game.sizeModifier * 220).uniformX();
        } else {
            endTable.add(scoreTitleLabel).colspan(6).padTop(game.sizeModifier * 0).padBottom(game.sizeModifier * 0);
            endTable.row();
            endTable.add(scoreLabel).colspan(6).padTop(game.sizeModifier * 0).padBottom(game.sizeModifier * 0);
            endTable.row();
            if (score <= scoreGoal) {
                endTable.add(nextButton).size(game.sizeModifier * 200);
            } else {
                endTable.add(restartButton).size(game.sizeModifier * 200);
            }
            endTable.add(levelsButton).size(game.sizeModifier * 200);
            endTable.row();
            if (score <= scoreGoal) {
                endTable.add(nextLabel).padTop(game.sizeModifier * 0).padBottom(game.sizeModifier * 0).top();
            } else {
                endTable.add(restartLabel).padTop(game.sizeModifier * 0).padBottom(game.sizeModifier * 0).top();
            }
            endTable.add(levelsLabel).padTop(game.sizeModifier * 0).padBottom(game.sizeModifier * 0).top();
            endTable.row();
            endTable.add(customizeButton).size(game.sizeModifier * 200);
            endTable.add(settingsButton).size(game.sizeModifier * 200);
            endTable.add(homeButton).size(game.sizeModifier * 200);
            endTable.row();
            endTable.add(customizeLabel).expand().padTop(game.sizeModifier * 0).padBottom(game.sizeModifier * 0).top();
            endTable.add(settingsLabel).expand().padTop(game.sizeModifier * 0).padBottom(game.sizeModifier * 0).top();
            endTable.add(homeLabel).expand().padTop(game.sizeModifier * 0).padBottom(game.sizeModifier * 0).top();
        }
        stage.addActor(endTable);
    }


    public void resumeGame() {
        paused = false;
        popSound.resume();
        tint.setVisible(false);
        pauseTable.remove();
        pauseTable = new Table();
        justResumed = true;
    }

    @Override
    public void resize(int width, int height) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        if (game.screenSize.x != width && game.screenSize.y != height) {
            game.screenSize = new Vector2(width, height);
            for (Dot dot : dots) {
                if (width > height) {
                    dot.position = dot.position.rotate(90);
                    dot.direction = dot.direction.rotate(90);
                    dot.position.x += width;
                } else {
                    dot.position = dot.position.rotate(-90);
                    dot.direction = dot.direction.rotate(-90);
                    dot.position.y += height;
                }
            }
        }
        stage.getViewport().update(width, height, true);
        pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - (15 * game.sizeModifier), Gdx.graphics.getHeight() - pauseButton.getHeight() - (15 * game.sizeModifier));
        scoreLabel.setPosition(25 * game.sizeModifier, game.screenSize.y - scoreLabel.getHeight() - (30 * game.sizeModifier));
        tint.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (paused) {
            resumeGame();
            pauseGame();
        }
        game.sizeModifier = Math.min(width, height)/1080f;
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
        popSound.dispose();
        bigFont.dispose();
        mediumFont.dispose();
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
