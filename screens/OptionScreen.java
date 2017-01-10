package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.genjitheninja.game.GenjiTheNinja;
import com.sun.javafx.scene.control.skin.CheckBoxSkin;

import helpers.PlayServices;

/**
 * Created by Per on 10.07.2016.
 */
public class OptionScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 640;
    private Stage stage, backgroundStage;
    private TextureRegion backgroundMenu1, backgroundScroll;
    private TextureRegion backButton, backButtonPressed, checkBoxButtonOn, checkBoxButtonOff;
    private GenjiTheNinja game;
    private TextButton.TextButtonStyle textButtonStyle;
    private Viewport extendedviewport, fitviewport;
    private Preferences prefs;
    public static PlayServices playServices;
    private boolean menuSound, gameSound;

    public OptionScreen(GenjiTheNinja game, PlayServices playServices) {
        this.game = game;
        this.playServices = playServices;
    }

    @Override
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
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        prefs = Gdx.app.getPreferences("ColorNinja");
        menuSound = prefs.getBoolean("menuSoundTrue");
        gameSound = prefs.getBoolean("gameSoundTrue");
        fitviewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        extendedviewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(fitviewport);
        backgroundStage = new Stage(extendedviewport);
        Gdx.input.setInputProcessor(stage);
        TextureAtlas textureAtlas = game.getAssetManager().get("ninja_assets.atlas");
        backgroundMenu1 = textureAtlas.findRegion("backgroundMenu1_Layer 0");
        backgroundScroll = textureAtlas.findRegion("backgroundMenu1_Layer 1");
        backButton = textureAtlas.findRegion("buttonsnew_back", 0);
        backButtonPressed = textureAtlas.findRegion("buttonsnew_back", 1);
        checkBoxButtonOn = textureAtlas.findRegion("buttonsnew_checkbox", 0);
        checkBoxButtonOff = textureAtlas.findRegion("buttonsnew_checkbox", 1);
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("text.fnt"));
        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, bitmapFont.getColor());

        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle(new TextureRegionDrawable(checkBoxButtonOff), new TextureRegionDrawable(checkBoxButtonOn), bitmapFont, Color.BLACK);

        Skin skin = new Skin();
        //buttonAtlas = new TextureAtlas(Gdx.files.internal("ninja_assets.atlas"));
        skin.addRegions(textureAtlas);

        Image background = new Image(backgroundMenu1);
        Image scroll = new Image(backgroundScroll);

        background.setWidth(Gdx.graphics.getWidth());
        background.setHeight(Gdx.graphics.getHeight());

        Label labelMenuSound = new Label("Menu Sound", labelStyle);
        labelMenuSound.setPosition(WORLD_WIDTH / 6, WORLD_HEIGHT * 0.7f);
        CheckBox menuSoundBox = new CheckBox("", checkBoxStyle);
        menuSoundBox.setPosition(labelMenuSound.getX() + 250, labelMenuSound.getY());
        menuSoundBox.setChecked(menuSound);
        menuSoundBox.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                prefs.putBoolean("menuSoundTrue", !menuSound);
                prefs.flush();
            }
        });

        Label labelGameSound = new Label("Game Sound", labelStyle);
        labelGameSound.setPosition(WORLD_WIDTH / 6, WORLD_HEIGHT * 0.5f);
        CheckBox gameSoundBox = new CheckBox("", checkBoxStyle);
        gameSoundBox.setPosition(labelGameSound.getX() + 250, labelGameSound.getY());
        gameSoundBox.setChecked(gameSound);
        gameSoundBox.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                prefs.putBoolean("gameSoundTrue", !gameSound);
                prefs.flush();
            }
        });

        Label labelColorblind = new Label("Colorblind", labelStyle);
        labelColorblind.setPosition(WORLD_WIDTH / 5, WORLD_HEIGHT * 0.3f);
        CheckBox colorblindBox = new CheckBox("", checkBoxStyle);
        colorblindBox.setPosition(labelColorblind.getX() + 350, labelColorblind.getY());

        /*Slider gameSoundSlider = new Slider(0, 1, 1, false, sliderStyle);
        gameSoundSlider.setWidth(70);
        gameSoundSlider.setPosition(labelGameSound.getX() + 220, labelGameSound.getY());*/

        ImageButton back = new ImageButton(new TextureRegionDrawable((new TextureRegion(backButton))), new TextureRegionDrawable((new TextureRegion(backButtonPressed))));
        back.setPosition(WORLD_WIDTH * 0.04f, WORLD_HEIGHT * 0.87f);
        back.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                stage.addAction(Actions.sequence(Actions.fadeOut(0.4f), Actions.run(new Runnable() {
                    public void run() {
                        game.setScreen(new StartScreen(game, playServices));
                    }
                })));
            }
        });

        scroll.setPosition(5, 0);
        scroll.setWidth(480);
        scroll.setHeight(550);

        backgroundStage.addActor(background);
        stage.addActor(back);
        stage.addActor(scroll);
        stage.addActor(labelMenuSound);
        stage.addActor(labelGameSound);
        //stage.addActor(labelColorblind);
        stage.addActor(menuSoundBox);
        stage.addActor(gameSoundBox);
        //stage.addActor(colorblindBox);
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(Actions.fadeIn(0.4f));
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}