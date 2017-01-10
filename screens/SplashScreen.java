package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.genjitheninja.game.GenjiTheNinja;

import helpers.PlayServices;

/**
 * Created by Per on 10.07.2016.
 */
public class SplashScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 640;
    private Stage stage;
    private Texture splashTexture;
    private GenjiTheNinja game;
    private Viewport viewport;
    private Camera camera;
    public static PlayServices playServices;

    public SplashScreen(GenjiTheNinja game, PlayServices playServices) {
        this.game = game;
        this.playServices = playServices;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));

        splashTexture = new Texture(Gdx.files.internal("splashscreen.png"));

        Image background = new Image(splashTexture);

        background.setWidth(WORLD_WIDTH);
        background.setHeight(WORLD_HEIGHT);
        stage.addActor(background);
        //stage.getRoot().getColor().a = 0;
        //stage.getRoot().addAction(Actions.fadeIn(0.7f));
        stage.addAction(Actions.sequence(Actions.fadeIn(0.7f),Actions.fadeOut(1f), Actions.run(new Runnable() {
            public void run() {
                game.setScreen(new LoadingScreen(game, playServices));
            }
        })));
    }

    @Override
    public void hide() {
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
        super.dispose();
    }
}
