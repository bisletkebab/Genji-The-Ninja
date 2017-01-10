package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.genjitheninja.game.GenjiTheNinja;

import helpers.PlayServices;

/**
 * Created by pernaess on 16.04.2016.
 */
public class LoadingScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 640;
    private final GenjiTheNinja easiestGame;
    private Texture loadingTexture;
    private Stage stage;
    public static PlayServices playServices;

    public LoadingScreen(GenjiTheNinja easiestGame, PlayServices playServices) {
        this.easiestGame = easiestGame;
        this.playServices = playServices;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        easiestGame.getAssetManager().load("ninja_assets.atlas", TextureAtlas.class);
        easiestGame.getAssetManager().load("NFF-punch.wav", Sound.class);
        easiestGame.getAssetManager().load("NFF-rasp.wav", Sound.class);
        easiestGame.getAssetManager().load("NFF-explode.wav", Sound.class);
        loadingTexture = new Texture(Gdx.files.internal("loadingscreen.png"));
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("text.fnt"));
        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, bitmapFont.getColor());

        Label versionLabel = new Label("Version 1.1", labelStyle);
        versionLabel.setPosition(WORLD_WIDTH * 0.9f, WORLD_HEIGHT * 0.1f);

        loadingTexture = new Texture(Gdx.files.internal("loadingscreen.png"));

        Image background = new Image(loadingTexture);

        background.setWidth(WORLD_WIDTH);
        background.setHeight(WORLD_HEIGHT);
        stage.addActor(background);
        stage.addActor(versionLabel);
    }

    @Override
    public void render(float delta) {
        update();
        clearScreen();
        draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }


    @Override
    public void hide(){
        dispose();
    }

    private void update() {
        if(easiestGame.getAssetManager().update()){
            easiestGame.setScreen(new StartScreen(easiestGame, playServices));
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw() {
    }
}
