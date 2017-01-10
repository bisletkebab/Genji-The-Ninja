package objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by pernaess on 16.04.2016.
 */
public class Projectiles {
    private static final float WIDTH = 50;
    private static final float HEIGHT = 50;
    private float speedX, pauseSpeedX;
    private float speedY, pauseSpeedY;
    private float x;
    private float y;
    private Rectangle shurikenRect;
    private static float FRAME_DURATION = 0.5f;
    private float animationTimer;
    private TextureRegion redCube0, redCube1, redCube2, redCube3;
    private TextureRegion blueCube0, blueCube1, blueCube2, blueCube3;
    private TextureRegion greenCube0, greenCube1, greenCube2, greenCube3;
    private TextureRegion blueFadeCube0, blueFadeCube1, blueFadeCube2, blueFadeCube3;
    private Animation redCubeAnimation, blueCubeAnimation, greenCubeAnimation, blueFadeCubeAnimation;
    private int cubeColorAnimationNumber = 0;
    private int cubeColorNumber = 0;
    private long startTime;
    private TextureAtlas textureAtlas;

    public Projectiles(float x, float y, float speedX, float speedY, TextureAtlas textureAtlas, int fromColor) {
        this.textureAtlas = textureAtlas;
        animationTimer = 0;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        pauseSpeedX = speedX;
        pauseSpeedY = speedY;
        shurikenRect = new Rectangle(x, y, WIDTH, HEIGHT);
        cubeColorAnimationNumber = fromColor;
        checkCubeColorNumber();

        startTime = TimeUtils.millis();
        animations();
    }

    private void checkCubeColorNumber() {
        if(cubeColorAnimationNumber == 3) {
            cubeColorNumber = 0;
        } else if(cubeColorAnimationNumber == 4){
            cubeColorNumber = 1;
        } else if(cubeColorAnimationNumber == 5) {
            cubeColorNumber = 2;
        } else {
            cubeColorNumber = cubeColorAnimationNumber;
        }
    }

    public void draw(SpriteBatch batch) {
        if(cubeColorAnimationNumber == 0) {
            batch.draw(blueCubeAnimation.getKeyFrame(animationTimer), x, y, WIDTH, HEIGHT);
        } else if(cubeColorAnimationNumber == 1) {
            batch.draw(redCubeAnimation.getKeyFrame(animationTimer), x, y, WIDTH, HEIGHT);
        } else if(cubeColorAnimationNumber == 2){
            batch.draw(greenCubeAnimation.getKeyFrame(animationTimer), x, y, WIDTH, HEIGHT);
        } else if(cubeColorAnimationNumber == 3) {
            if(TimeUtils.millis()<startTime + 1000) {
                batch.draw(blueCubeAnimation.getKeyFrame(animationTimer), x, y, WIDTH, HEIGHT);
            } else {
                batch.draw(blueFadeCubeAnimation.getKeyFrame(animationTimer), x, y, WIDTH, HEIGHT);
            }
        }
    }

    private void animations() {
        blueCube0 = textureAtlas.findRegion("colorcubes_blueCube", 0);
        blueCube1 = textureAtlas.findRegion("colorcubes_blueCube", 1);
        blueCube2 = textureAtlas.findRegion("colorcubes_blueCube", 2);
        blueCube3 = textureAtlas.findRegion("colorcubes_blueCube", 3);
        TextureRegion[] blueCube = {blueCube0, blueCube1, blueCube2, blueCube3};
        blueCubeAnimation = new Animation(FRAME_DURATION, blueCube);
        blueCubeAnimation.setPlayMode(Animation.PlayMode.LOOP);

        redCube0 = textureAtlas.findRegion("colorcubes_redCube", 0);
        redCube1 = textureAtlas.findRegion("colorcubes_redCube", 1);
        redCube2 = textureAtlas.findRegion("colorcubes_redCube", 2);
        redCube3 = textureAtlas.findRegion("colorcubes_redCube", 3);
        TextureRegion[] redCube = {redCube0, redCube1, redCube2, redCube3};
        redCubeAnimation = new Animation(FRAME_DURATION, redCube);
        redCubeAnimation.setPlayMode(Animation.PlayMode.LOOP);

        greenCube0 = textureAtlas.findRegion("colorcubes_greenCube", 0);
        greenCube1 = textureAtlas.findRegion("colorcubes_greenCube", 1);
        greenCube2 = textureAtlas.findRegion("colorcubes_greenCube", 2);
        greenCube3 = textureAtlas.findRegion("colorcubes_greenCube", 3);
        TextureRegion[] greenCube = {greenCube0, greenCube1, greenCube2, greenCube3};
        greenCubeAnimation = new Animation(FRAME_DURATION, greenCube);
        greenCubeAnimation.setPlayMode(Animation.PlayMode.LOOP);

        blueFadeCube0 = textureAtlas.findRegion("colorcubes_blueCubeFade", 0);
        blueFadeCube1 = textureAtlas.findRegion("colorcubes_blueCubeFade", 1);
        blueFadeCube2 = textureAtlas.findRegion("colorcubes_blueCubeFade", 2);
        blueFadeCube3 = textureAtlas.findRegion("colorcubes_blueCubeFade", 3);
        TextureRegion[] blueFadeCube = {blueFadeCube0, blueFadeCube1, blueFadeCube2, blueFadeCube3};
        blueFadeCubeAnimation = new Animation(FRAME_DURATION, blueFadeCube);
    }

    public void update(float delta) {
        animationTimer += delta;
        setPosition(x - (speedX * delta), y - (speedY * delta));
    }

    private void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        shurikenRect.setX(x);
        shurikenRect.setY(y);
    }

    public boolean isSwordColliding(Rectangle sword) {
        return Intersector.overlaps(sword, shurikenRect);
    }

    public boolean isPlayerColliding(Rectangle player) {
        return Intersector.overlaps(player, shurikenRect);
    }

    public void pauseSpeed() {
        speedX = 0;
        speedY = 0;
    }

    public void unPauseSpeed() {
        speedX = pauseSpeedX;
        speedY = pauseSpeedY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return WIDTH;
    }

    public float getHeight() {
        return HEIGHT;
    }

    public int getCubeColorNumber() {
        return cubeColorNumber;
    }
}