package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.genjitheninja.game.GenjiTheNinja;

import helpers.PlayServices;

/**
 * Created by Per on 10.07.2016.
 */
public class SwordScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 640;
    private Stage stage, backgroundStage;
    private TextureRegion backgroundMenu1, backgroundScroll;
    private TextureRegion backButton, backButtonPressed, loveButton, loveButtonPressed;
    private GenjiTheNinja game;
    private Viewport extendedviewport, fitviewport;
    public static PlayServices playServices;

    public SwordScreen(GenjiTheNinja game, PlayServices playServices) {
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
        backgroundStage.getViewport().update(width, height);
    }

    @Override
    public void show() {
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
        loveButton = textureAtlas.findRegion("buttonsnew_About", 0);
        loveButtonPressed = textureAtlas.findRegion("buttonsnew_About", 1);
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("text2.fnt"));
        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, bitmapFont.getColor());

        Image background = new Image(backgroundMenu1);
        background.setWidth(Gdx.graphics.getWidth());
        background.setHeight(Gdx.graphics.getHeight());
        Image scroll = new Image(backgroundScroll);


        background.setWidth(Gdx.graphics.getWidth());
        background.setHeight(Gdx.graphics.getHeight());

        ImageButton back = new ImageButton(new TextureRegionDrawable((new TextureRegion(backButton))), new TextureRegionDrawable((new TextureRegion(backButtonPressed))));
        back.setPosition(WORLD_WIDTH * 0.04f, WORLD_HEIGHT * 0.87f);
        back.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                stage.addAction(Actions.sequence(Actions.fadeOut(0.3f), Actions.run(new Runnable() {
                    public void run() {
                        game.setScreen(new StartScreen(game, playServices));
                    }
                })));
            }
        });

        scroll.setPosition(5, 0);
        scroll.setWidth(480);
        scroll.setHeight(550);

        Label aboutText = new Label("Thank you so much for trying this\n game. It's the first i've\n ever started and finished making.\n And please don't laugh at my\n drawing skills =( \n\n All the sounds used in this game\n are from www.noiseforfun.com \n\n If you enjoyed the game,\n please rate it <3", labelStyle);
        aboutText.setPosition(scroll.getX() + 50, scroll.getY() + 165);

        ImageButton love = new ImageButton(new TextureRegionDrawable((loveButton)), new TextureRegionDrawable(loveButtonPressed));
        love.setPosition(WORLD_WIDTH / 2, aboutText.getY() - 40, Align.center);
        love.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.playServices.rateGame();
            }
        });

        backgroundStage.addActor(background);
        stage.addActor(back);
        stage.addActor(scroll);
        stage.addActor(aboutText);
        stage.addActor(love);
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(Actions.fadeIn(0.3f));

    }

    @Override
    public void hide() {
        dispose();
        super.hide();
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