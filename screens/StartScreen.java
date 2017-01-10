package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.genjitheninja.game.GenjiTheNinja;

import helpers.PlayServices;

/**
 * Created by Per on 20.05.2016.
 */
public class StartScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 640;
    private TextureRegion backgroundMenu1, backgroundScroll;
    private TextureRegion title, playButton, swordButton, optionButton, storeButton, aboutButton, playPressed, swordPressed, optionPressed, storePressed, aboutPressed, highscoreButton, highscorePressed;
    private Sound menuSoundPlay;
    private boolean menuSound, gameSound;
    public Stage stage, backgroundStage;
    private final GenjiTheNinja game;
    private FitViewport fitviewport;
    private ExtendViewport extendedviewport;
    public static PlayServices playServices;
    private Preferences prefs;

    public StartScreen(GenjiTheNinja game, PlayServices playServices) {
        this.game = game;
        this.playServices = playServices;
    }

    public void show() {
        prefs = Gdx.app.getPreferences("ColorNinja");
        if(!prefs.contains("menuSoundTrue")){
            menuSound = true;
            gameSound = true;
            prefs.putBoolean("menuSoundTrue", true);
            prefs.putBoolean("gameSoundTrue", true);
            prefs.flush();
        } else {
            menuSound = prefs.getBoolean("menuSoundTrue");
            gameSound = prefs.getBoolean("gameSoundTrue");
        }
        TextureAtlas textureAtlas = game.getAssetManager().get("ninja_assets.atlas");
        fitviewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        extendedviewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(fitviewport);
        backgroundStage = new Stage(extendedviewport);
        Gdx.input.setInputProcessor(stage);
        backgroundMenu1 = textureAtlas.findRegion("backgroundMenu1_Layer 0");
        playButton = textureAtlas.findRegion("buttonsnew_play", 0);
        swordButton = textureAtlas.findRegion("buttonsnew_sword", 0);
        optionButton = textureAtlas.findRegion("buttonsnew_options", 0);
        storeButton = textureAtlas.findRegion("buttonsnew_shop", 0);
        aboutButton = textureAtlas.findRegion("buttonsnew_About", 0);
        playPressed = textureAtlas.findRegion("buttonsnew_play", 1);
        swordPressed = textureAtlas.findRegion("buttonsnew_sword", 1);
        optionPressed = textureAtlas.findRegion("buttonsnew_options", 1);
        storePressed = textureAtlas.findRegion("buttonsnew_shop", 1);
        aboutPressed = textureAtlas.findRegion("buttonsnew_About", 1);
        highscoreButton = textureAtlas.findRegion("buttonsnew_highscore", 0);
        highscorePressed = textureAtlas.findRegion("buttonsnew_highscore", 1);
        title = textureAtlas.findRegion("titletext");
        menuSoundPlay = game.getAssetManager().get("NFF-punch.wav");

        Image background = new Image(backgroundMenu1);
        Image titleName = new Image(title);

        background.setWidth(Gdx.graphics.getWidth());
        background.setHeight(Gdx.graphics.getHeight());

        titleName.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2 + 25);
        titleName.setWidth(title.getRegionWidth() * 2);
        titleName.setHeight(title.getRegionHeight() * 2);

        final ImageButton play = new ImageButton(new TextureRegionDrawable(playButton), new TextureRegionDrawable(playPressed));
        play.setPosition(WORLD_WIDTH / 2 - 35, WORLD_HEIGHT / 2 - 25, Align.center);
        play.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if(menuSound) {
                    menuSoundPlay.play();
                }
                super.tap(event, x, y, count, button);
                stage.addAction(Actions.sequence(Actions.fadeOut(0.4f), Actions.run(new Runnable() {
                    public void run() {
                        game.setScreen(new GameScreen(game, playServices));
                    }
                })));
            }
        });

        ImageButton swords = new ImageButton(new TextureRegionDrawable((highscoreButton)), new TextureRegionDrawable(highscorePressed));
        swords.setPosition(WORLD_WIDTH / 2 + 35, WORLD_HEIGHT / 2 - 25, Align.center);
        swords.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                /*stage.addAction(Actions.sequence(Actions.fadeOut(0.4f), Actions.run(new Runnable() {
                    public void run() {
                        //playServices.signIn();
                        //game.setScreen(new SwordScreen(game, playServices));
                        game.playServices.showScore();
                    }
                })));*/
                if(game.playServices.isSignedIn()){
                    game.playServices.showScore();
                } else {
                    game.playServices.signIn();
                }
            }
        });

        ImageButton options = new ImageButton(new TextureRegionDrawable(optionButton), new TextureRegionDrawable(optionPressed));
        options.setPosition(WORLD_WIDTH / 2 - 35, swords.getY() - 35, Align.center);
        options.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                stage.addAction(Actions.sequence(Actions.fadeOut(0.4f), Actions.run(new Runnable() {
                    public void run() {
                        game.setScreen(new OptionScreen(game, playServices));
                    }
                })));
            }
        });

        ImageButton about = new ImageButton(new TextureRegionDrawable(aboutButton), new TextureRegionDrawable(aboutPressed));
        about.setPosition(WORLD_WIDTH / 2 + 35, swords.getY() - 35, Align.center);
        about.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                stage.addAction(Actions.sequence(Actions.fadeOut(0.4f), Actions.run(new Runnable() {
                    public void run() {
                        game.setScreen(new SwordScreen(game, playServices));
                    }
                })));
            }
        });

        /*ImageButton about = new ImageButton(new TextureRegionDrawable(aboutButton));
        options.setPosition(WORLD_WIDTH / 2 + 35, swords.getY() - 35, Align.center);
        options.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                stage.addAction(Actions.sequence(Actions.fadeOut(0.4f), Actions.run(new Runnable() {
                    public void run() {
                        game.setScreen(new OptionScreen(game, playServices));
                    }
                })));
            }
        });*/

        backgroundStage.addActor(background);
        stage.addActor(titleName);
        stage.addActor(play);
        stage.addActor(swords);
        stage.addActor(options);
        stage.addActor(about);
        //stage.addActor(shop);
        //stage.addActor(highscore);
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(Actions.fadeIn(0.4f));
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        backgroundStage.getViewport().update(width, height);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        extendedviewport.apply();
        backgroundStage.draw();
        fitviewport.apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
        super.hide();
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
