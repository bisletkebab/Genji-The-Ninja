package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.genjitheninja.game.GenjiTheNinja;
import helpers.InputHandler;

import helpers.PlayServices;
import objects.Player;
import objects.Projectiles;

/**
 * Created by pernaess on 16.04.2016.
 */
public class GameScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 640;
    private GenjiTheNinja game;
    private Viewport viewport, extendviewport;
    private Camera camera;
    private SpriteBatch batch;
    private Player player;
    private Array<Projectiles> projectiles = new Array<Projectiles>();
    private float shootTime;
    private float timer = shootTime;
    private int score = 0;
    private float shurikenX, shurikenY;
    private GlyphLayout glyphLayout;
    private BitmapFont bitmapFont;
    private float speedX, speedY, speedZero;
    private TextureRegion retryButton, highscoreButton, exitButton;
    private TextureRegion backgroundGameYellow, backgroundGamePurple, backgroundGamePink, backgroundGame;
    private TextureRegion buttonPause;
    private TextureRegion cloud0;
    private TextureRegion blueCubeCut0, blueCubeCut1, blueCubeCut2, blueCubeCut3, blueCubeCut4, blueCubeCut5, blueCubeCut6;
    private TextureRegion redCubeCut0, redCubeCut1, redCubeCut2, redCubeCut3, redCubeCut4, redCubeCut5, redCubeCut6;
    private TextureRegion greenCubeCut0, greenCubeCut1, greenCubeCut2, greenCubeCut3, greenCubeCut4, greenCubeCut5, greenCubeCut6;
    private Animation blueCubeCutAnimation, redCubeCutAnimation, greenCubeCutAnimation;
    private static float FRAME_DURATION = 0.03f;
    private boolean cut = false;
    private float animationTimer;
    private float projectileX, projectileY;
    private float cloudX, cloudY, cloudSpeed, cloudWIDTH, cloudHEIGHT;
    private int cutColor;
    private float exitButtonX, exitButtonY, buttonPauseX,  buttonPauseY, buttonWIDTH, buttonHEIGHT, retryButtonX, retryButtonY, retryButtonWidth, retryButtonHeight, highscoreButtonX, highscoreButtonY;
    private Vector3 touchPos;
    private State state;
    private int fromColor, lastColor, bestScore;
    private TextureAtlas textureAtlas;
    private Preferences prefs;
    private int oldRandom;
    public static PlayServices playServices;
    private Sound menuSoundPlay, hitSound, deathSound;
    private boolean menuSound, gameSound;

    public enum State{
        RUNNING,
        PAUSED,
        BEGINNING,
        COMPLETE,
        DEAD
    }

    public GameScreen(GenjiTheNinja game, PlayServices playServices) {
        this.game = game;
        this.playServices =  playServices;
    }

    @Override
    public void render(float delta) {
        switch(state) {
            case RUNNING:
                unPaused();
                clearScreen();
                draw();
                update(delta);
                break;

            case PAUSED:
                paused();
                draw();
                update(delta);
                break;

            case BEGINNING:
                draw();
                update(delta);

            case COMPLETE:
                draw();
                update(delta);

            case DEAD:
                draw();
                update(delta);
        }

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        viewport.update(width, height);
        extendviewport.update(width, height);
    }

    @Override
    public void show() {
        prefs = Gdx.app.getPreferences("ColorNinja");
        if (!prefs.contains("highscore")) {
            /*if (playServices.isSignedIn()) {
                if (playServices.getPlayerHighscore() != null) {
                    prefs.putInteger("highscore", Integer.parseInt(playServices.getPlayerHighscore()));
                    bestScore = prefs.getInteger("highscore");
                }
            } else */
            prefs.putInteger("highscore", 0);
            prefs.flush();
        } else {
            bestScore = prefs.getInteger("highscore");
        }
        menuSound = prefs.getBoolean("menuSoundTrue");
        gameSound = prefs.getBoolean("gameSoundTrue");
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        extendviewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch = new SpriteBatch();
        camera.update();
        Gdx.input.setInputProcessor(new InputHandler(this));
        glyphLayout = new GlyphLayout();
        bitmapFont = new BitmapFont(Gdx.files.internal("text.fnt"));
        speedZero = 0;
        textureAtlas = game.getAssetManager().get("ninja_assets.atlas");
        retryButton = textureAtlas.findRegion("buttonsnew_retry", 0);
        exitButton = textureAtlas.findRegion("buttonsnew_exit", 0);
        backgroundGameYellow = textureAtlas.findRegion("background_yellowBackground");
        backgroundGamePurple = textureAtlas.findRegion("background_purpleBackground");
        backgroundGamePink = textureAtlas.findRegion("background_pinkBackground");
        backgroundGame = new TextureRegion();
        buttonPause = textureAtlas.findRegion("buttonsnew_pause", 0);
        highscoreButton = textureAtlas.findRegion("buttonsnew_highscore", 0);
        cloud0 = textureAtlas.findRegion("cloud");
        menuSoundPlay = game.getAssetManager().get("NFF-punch.wav");
        hitSound = game.getAssetManager().get("NFF-rasp.wav");
        deathSound = game.getAssetManager().get("NFF-explode.wav");
        player = new Player(WORLD_WIDTH / 2 - 32, WORLD_HEIGHT / 2 - 32, WORLD_WIDTH, WORLD_HEIGHT, textureAtlas);
        touchPos = new Vector3();
        cutAnimation();
        cut = false;
        buttonWIDTH = 60;
        buttonHEIGHT = 60;
        retryButtonWidth = 60;
        retryButtonHeight = 60;
        buttonPauseX = WORLD_WIDTH * 0.05f;
        buttonPauseY = WORLD_HEIGHT * 0.80f;
        retryButtonX = WORLD_WIDTH / 2 - (retryButtonWidth / 2);
        highscoreButtonX = buttonPauseX;
        exitButtonX = buttonPauseX;
        exitButtonY = buttonPauseY + 65;
        highscoreButtonY = buttonPauseY - 65;
        retryButtonY = WORLD_HEIGHT / 2 - 150;
        state = State.BEGINNING;
        setLevel();
        setBackground();
        setClouds();
        fromColor = 0;
    }

    public void running(int screenX, int screenY) {
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);
        if(touchPos.x > buttonPauseX && touchPos.x < buttonPauseX + buttonWIDTH && touchPos.y < buttonPauseY + 10 && touchPos.y > buttonPauseY - 32){
            if(menuSound) {
                menuSoundPlay.play();
            }
            setPause();
        } else {
            player.attack(touchPos.x, touchPos.y);
        }
    }

    public void paused(int screenX, int screenY) {
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);
        if (touchPos.x > buttonPauseX && touchPos.x < buttonPauseX + buttonWIDTH) {
            if (touchPos.y < buttonPauseY + 10 && touchPos.y > buttonPauseY - 32) {
                if(menuSound) {
                    menuSoundPlay.play();
                }
                setRunning();
            }
        }

        if (touchPos.x > highscoreButtonX && touchPos.x < highscoreButtonX + retryButtonWidth) {
            if (touchPos.y < highscoreButtonY + 10 && touchPos.y > highscoreButtonY -32) {
                playServices.showScore();
            }
        }

        if (touchPos.x > exitButtonX && touchPos.x < exitButtonX + retryButtonWidth) {
            if (touchPos.y < exitButtonY + 10 && touchPos.y > exitButtonY - 32) {
                if(menuSound) {
                    menuSoundPlay.play();
                }
                game.setScreen(new StartScreen(game, playServices));
                super.dispose();
            }
        }

    }

    public void death(int screenX, int screenY) {
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);
        if (touchPos.x > retryButtonX && touchPos.x < retryButtonX + retryButtonWidth) {
            if (touchPos.y > retryButtonY && touchPos.y < retryButtonY + retryButtonHeight) {
                if(menuSound) {
                    menuSoundPlay.play();
                }
                game.setScreen(new GameScreen(game, playServices));
            }
        }

        if (touchPos.x > highscoreButtonX && touchPos.x < highscoreButtonX + retryButtonWidth) {
            if (touchPos.y < highscoreButtonY + 10 && touchPos.y > highscoreButtonY - 32) {
                playServices.showScore();
            }
        }

        if (touchPos.x > exitButtonX && touchPos.x < exitButtonX + retryButtonWidth) {
            if (touchPos.y < exitButtonY + 10 && touchPos.y > exitButtonY - 32) {
                if(menuSound) {
                    menuSoundPlay.play();
                }
                game.setScreen(new StartScreen(game, playServices));
                super.dispose();
            }
        }
    }

    private void update(float delta) {
        player.update(delta);
        animationTimer += delta;
        drawClouds(delta);

        if(state == State.RUNNING) {
            createNewProjectile(delta);
            updateProjectiles(delta);
            checkForCollision();
            checkForCollisionPlayer();
        } else if( state == State.DEAD) {
            for (Projectiles projectile : projectiles) {
                destroyProjectile(projectiles.indexOf(projectile, false));
            }
        }
    }

    private void draw() {
        extendviewport.apply();
        batch.begin();
        batch.setTransformMatrix(camera.view);
        batch.draw(backgroundGame, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        batch.end();

        viewport.apply();
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(cloud0, cloudX, cloudY, cloudWIDTH, cloudHEIGHT);

        if(state == State.BEGINNING) {
            drawLevel();
        } else if(state == State.PAUSED) {
            batch.draw(exitButton, exitButtonX, exitButtonY, buttonWIDTH, buttonHEIGHT);
            batch.draw(buttonPause, buttonPauseX, buttonPauseY, buttonWIDTH, buttonWIDTH);
            batch.draw(highscoreButton, highscoreButtonX, highscoreButtonY, retryButtonWidth, retryButtonHeight);
        } else if(state == State.DEAD) {
            player.draw(batch);
            batch.draw(exitButton, exitButtonX, exitButtonY, buttonWIDTH, buttonHEIGHT);
            batch.draw(retryButton, retryButtonX, retryButtonY, retryButtonWidth, retryButtonHeight);
            batch.draw(highscoreButton, highscoreButtonX, highscoreButtonY, retryButtonWidth, retryButtonHeight);
            drawDead();
        } else if(state == State.RUNNING){
            batch.draw(buttonPause, buttonPauseX, buttonPauseY, buttonWIDTH, buttonHEIGHT);

            player.draw(batch);

            for(Projectiles projectile : projectiles) {
                projectile.draw(batch);
            }

            if(cut == true) {
                if(cutColor == 0) {
                    batch.draw(blueCubeCutAnimation.getKeyFrame(animationTimer), projectileX, projectileY + 5, 95, 95);
                    if(blueCubeCutAnimation.isAnimationFinished(animationTimer)) {
                        cut = false;
                    }
                } else if(cutColor == 1) {
                    batch.draw(redCubeCutAnimation.getKeyFrame(animationTimer), projectileX, projectileY + 5, 95, 95);
                    if(redCubeCutAnimation.isAnimationFinished(animationTimer)) {
                        cut = false;
                    }
                } else if(cutColor == 2) {
                    batch.draw(greenCubeCutAnimation.getKeyFrame(animationTimer), projectileX, projectileY + 5, 95, 95);
                    if(greenCubeCutAnimation.isAnimationFinished(animationTimer)) {
                        cut = false;
                    }
                }
            }
            drawScore();
        }

        batch.end();
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

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void updateProjectiles(float delta) {
        for(Projectiles projectile : projectiles) {
            projectile.update(delta);
        }
    }

    private void setLevel() {
        speedX = 60;
        speedY = 60;
        shootTime = 2;
    }

    private boolean checkForCollision() {
        for(Projectiles projectile : projectiles) {
            if (projectile.isSwordColliding(player.getSwordCollisionRectangle()) && projectile.getCubeColorNumber() == player.getColorNumber()) {
                if(gameSound) {
                    hitSound.play();
                }
                animationTimer = 0;
                projectileX = projectile.getX() - (projectile.getWidth() / 2);
                projectileY = projectile.getY() - (projectile.getHeight() / 2);
                cutColor = player.getColorNumber();
                cut = true;
                destroyProjectile(projectiles.indexOf(projectile, false));
                updateScore();
                return true;
            } else if(projectile.isSwordColliding(player.getSwordCollisionRectangle()) && projectile.getCubeColorNumber() != player.getColorNumber()){
                if(gameSound) {
                    deathSound.play();
                }
                destroyProjectile(projectiles.indexOf(projectile, false));
                player.setIsDead();
                setDead();
                submitScore();
            }
        }
        return false;
    }

    private boolean checkForCollisionPlayer() {
        for(Projectiles projectile : projectiles) {
            if(projectile.isPlayerColliding(player.getPlayerRectangle())) {
                if(gameSound) {
                    deathSound.play();
                }
                destroyProjectile(projectiles.indexOf(projectile, false));
                player.setIsDead();
                setDead();
                submitScore();
            }
        }
        return false;
    }

    private void destroyProjectile(int projectile) {
        projectiles.removeIndex(projectile);
    }

    private void updateScore() {
        score++;
    }

    private void drawScore() {
        String scoreAsString = Integer.toString(score);
        glyphLayout.setText(bitmapFont, scoreAsString);
        bitmapFont.draw(batch, scoreAsString, (viewport.getWorldWidth() * 0.85f) - glyphLayout.width / 2, (4 * viewport.getWorldHeight() / 5) - glyphLayout.height / 2);
    }

    private void drawLevel() {
        String levelAsString = "Tap to begin!";
        glyphLayout.setText(bitmapFont, levelAsString);
        bitmapFont.draw(batch, levelAsString, (viewport.getWorldWidth() / 2f) - glyphLayout.width / 2, (viewport.getWorldHeight() / 2f) - glyphLayout.height / 2);
    }

    private void drawDead() {
        String deadString;
        String bestString;
        if(score > bestScore) {
            deadString = "Congrats! New best: " + score;
            bestString = "";
        } else {
            deadString = "Score: " + Integer.toString(score);
            bestString = "Best:  " + Integer.toString(bestScore);
        }
        glyphLayout.setText(bitmapFont, deadString);
        bitmapFont.draw(batch, deadString, (viewport.getWorldWidth() / 2f) - glyphLayout.width / 2, (viewport.getWorldHeight() / 1.5f) - glyphLayout.height / 2);
        bitmapFont.draw(batch, bestString, (viewport.getWorldWidth() / 2f) - glyphLayout.width / 2, (viewport.getWorldHeight() / 1.6f) - glyphLayout.height / 2);
    }

    private void setBackground() {
        int random = MathUtils.random(0, 2);
        if(random == 0) {
            backgroundGame = backgroundGameYellow;
        } else if(random == 1) {
            backgroundGame = backgroundGamePurple;
        } else if(random ==  2) {
            backgroundGame = backgroundGamePink;
        }
    }

    private void setClouds() {
        cloudWIDTH = 64;
        cloudHEIGHT = 64;
        cloudX = MathUtils.random(200, 480);
        cloudY = MathUtils.random(550, 580);
        cloudSpeed = 1;
    }

    private void drawClouds(float delta) {
        cloudX = cloudX - (cloudSpeed * delta);
        if(cloudX + cloudWIDTH < 0) {
            cloudX = WORLD_WIDTH;
        }
    }

    private void createNewProjectile(float delta) {

        timer -= delta;
        if(timer <= 0) {
            randomShuriken();
            timer = shootTime;
        }
    }

    private void randomShuriken() {
        fromColor++;
        if(fromColor > 2) {
            fromColor = 0;
        }

        if(score < 6) {
            speedX = 80;
            speedY = 90;
            shootTime = 1.6f;
        } else if(score < 9) {
            speedX = 90;
            speedY = 100;
        } else if(score < 12) {
            speedX = 100;
            speedY = 110;
            shootTime = 1.2f;
        } else if(score < 15) {
            speedX = 110;
            speedY = 120;
            shootTime = 0.8f;
        } else if(score < 18) {
            speedX = 120;
            speedY = 130;
        } else if(score < 21) {
            speedX = 130;
            speedY = 140;
        } else if(score < 24) {
            speedX = 140;
            speedY = 150;
        }

        int random = MathUtils.random(0, 3);

        if(random == oldRandom) {
            random++;
            if(random > 3) {
                random = 0;
            }
        }

        oldRandom = random;

        if(random == 0) {
            shurikenLeft(fromColor);
        } else if(random == 1) {
            shurikenRight(fromColor);
        }else if (random == 2) {
            shurikenDown(fromColor);
        } else if(random == 3) {
            shurikenUp(fromColor);
        }
    }

    private void shurikenLeft(int fromColor) {
        for(int i = 0; i < 1; i++) {
            shurikenX = 0;
            shurikenY = WORLD_HEIGHT / 2 - 20;
            Projectiles newProjectile = new Projectiles(shurikenX, shurikenY, -speedX, speedZero, textureAtlas, fromColor);
            projectiles.add(newProjectile);
        }
    }

    private void shurikenRight(int fromColor) {
        shurikenX = WORLD_WIDTH;
        shurikenY = WORLD_HEIGHT / 2 - 20;
        Projectiles newProjectile = new Projectiles(shurikenX, shurikenY, speedX, speedZero, textureAtlas, fromColor);
        projectiles.add(newProjectile);
    }

    private void shurikenUp(int fromColor) {
        shurikenX = WORLD_WIDTH / 2 - 20;
        shurikenY = WORLD_HEIGHT;
        Projectiles newProjectile = new Projectiles(shurikenX, shurikenY, speedZero, speedY, textureAtlas, fromColor);
        projectiles.add(newProjectile);
    }

    private void shurikenDown(int fromColor) {
        shurikenX = WORLD_WIDTH / 2 - 20;
        shurikenY = 0;
        Projectiles newProjectile = new Projectiles(shurikenX, shurikenY, speedZero, -speedY, textureAtlas, fromColor);
        projectiles.add(newProjectile);
    }

    private void cutAnimation() {
        blueCubeCut0 = textureAtlas.findRegion("explosion_BlueExplosion", 0);
        blueCubeCut1 = textureAtlas.findRegion("explosion_BlueExplosion", 1);
        blueCubeCut2 = textureAtlas.findRegion("explosion_BlueExplosion", 2);
        blueCubeCut3 = textureAtlas.findRegion("explosion_BlueExplosion", 3);
        blueCubeCut4 = textureAtlas.findRegion("explosion_BlueExplosion", 4);
        blueCubeCut5 = textureAtlas.findRegion("explosion_BlueExplosion", 5);
        blueCubeCut6 = textureAtlas.findRegion("explosion_BlueExplosion", 6);
        TextureRegion[] blueCubeCut = {blueCubeCut0, blueCubeCut1, blueCubeCut2, blueCubeCut3, blueCubeCut4, blueCubeCut5, blueCubeCut6};
        blueCubeCutAnimation = new Animation(FRAME_DURATION, blueCubeCut);
        blueCubeCutAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        redCubeCut0 = textureAtlas.findRegion("explosion_RedExplosion", 0);
        redCubeCut1 = textureAtlas.findRegion("explosion_RedExplosion", 1);
        redCubeCut2 = textureAtlas.findRegion("explosion_RedExplosion", 2);
        redCubeCut3 = textureAtlas.findRegion("explosion_RedExplosion", 3);
        redCubeCut4 = textureAtlas.findRegion("explosion_RedExplosion", 4);
        redCubeCut5 = textureAtlas.findRegion("explosion_RedExplosion", 5);
        redCubeCut6 = textureAtlas.findRegion("explosion_RedExplosion", 6);
        TextureRegion[] redCubeCut = {redCubeCut0, redCubeCut1, redCubeCut2, redCubeCut3, redCubeCut4, redCubeCut5, redCubeCut6};
        redCubeCutAnimation = new Animation(FRAME_DURATION, redCubeCut);
        redCubeCutAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        greenCubeCut0 = textureAtlas.findRegion("explosion_GreenExplosion", 0);
        greenCubeCut1 = textureAtlas.findRegion("explosion_GreenExplosion", 1);
        greenCubeCut2 = textureAtlas.findRegion("explosion_GreenExplosion", 2);
        greenCubeCut3 = textureAtlas.findRegion("explosion_GreenExplosion", 3);
        greenCubeCut4 = textureAtlas.findRegion("explosion_GreenExplosion", 4);
        greenCubeCut5 = textureAtlas.findRegion("explosion_GreenExplosion", 5);
        greenCubeCut6 = textureAtlas.findRegion("explosion_GreenExplosion", 6);
        TextureRegion[] greenCubeCut = {greenCubeCut0, greenCubeCut1, greenCubeCut2, greenCubeCut3, greenCubeCut4, greenCubeCut5, greenCubeCut6};
        greenCubeCutAnimation = new Animation(FRAME_DURATION, greenCubeCut);
        greenCubeCutAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    private void paused() {
        for(Projectiles projectile : projectiles) {
            projectile.pauseSpeed();
        }
    }

    private void unPaused() {
        for(Projectiles projectile : projectiles) {
            projectile.unPauseSpeed();
        }
    }

    public void setPause() {
        state = State.PAUSED;
    }

    public void setRunning() {
        state = State.RUNNING;
    }

    public void setDead() {
        if(score > bestScore) {
            prefs.putInteger("highscore", score);
            prefs.flush();
        }
        state = State.DEAD;
    }

    public State getState() {
        return state;
    }

    private void submitScore(){
        if(playServices.isSignedIn()){
            playServices.submitScore(score);
        }
    }
}