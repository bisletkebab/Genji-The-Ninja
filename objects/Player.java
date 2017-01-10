package objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by pernaess on 16.04.2016.
 */
public class Player {
    private static final int WIDTH = 64;
    private static final int HEIGHT = 64;
    private static final int SWORD_WIDTH = 64;
    private static final int SWORD_HEIGHTY = 135;
    private static final int SWORD_WIDTHY = 135;
    private static final int SWORD_HEIGHT = 64;
    private float x;
    private float y;
    private float touchY;
    private float touchX;
    private Rectangle swordRect;
    private Rectangle playerRect;
    private boolean left_attack, right_attack, up_attack, down_attack;
    private float animationTimer;
    private static float FRAME_DURATION = 0.05f;
    private static float DEAD_FRAME_DURATION = 0.1f;
    private TextureRegion playerTextureBlue, playerTextureRed, playerTextureGreen;
    private TextureRegion playerBlueLeftAttack0, playerBlueLeftAttack1, playerBlueLeftAttack2, playerBlueLeftAttack3;
    private TextureRegion playerBlueRightAttack0, playerBlueRightAttack1, playerBlueRightAttack2, playerBlueRightAttack3;
    private TextureRegion playerBlueUpAttack0, playerBlueUpAttack1, playerBlueUpAttack2, playerBlueUpAttack3;
    private TextureRegion playerBlueDownAttack0, playerBlueDownAttack1, playerBlueDownAttack2, playerBlueDownAttack3;
    private TextureRegion playerRedLeftAttack0, playerRedLeftAttack1, playerRedLeftAttack2, playerRedLeftAttack3;
    private TextureRegion playerRedRightAttack0, playerRedRightAttack1, playerRedRightAttack2, playerRedRightAttack3;
    private TextureRegion playerRedUpAttack0, playerRedUpAttack1, playerRedUpAttack2, playerRedUpAttack3;
    private TextureRegion playerRedDownAttack0, playerRedDownAttack1, playerRedDownAttack2, playerRedDownAttack3;
    private TextureRegion playerGreenLeftAttack0, playerGreenLeftAttack1, playerGreenLeftAttack2, playerGreenLeftAttack3;
    private TextureRegion playerGreenRightAttack0, playerGreenRightAttack1, playerGreenRightAttack2, playerGreenRightAttack3;
    private TextureRegion playerGreenUpAttack0, playerGreenUpAttack1, playerGreenUpAttack2, playerGreenUpAttack3;
    private TextureRegion playerGreenDownAttack0, playerGreenDownAttack1, playerGreenDownAttack2, playerGreenDownAttack3;
    private TextureRegion playerDead0, playerDead1, playerDead2, playerDead3, playerDead4, playerDead5;
    private Animation leftBlueAttackAnimation, rightBlueAttackAnimation, upBlueAttackAnimation, downBlueAttackAnimation;
    private Animation leftRedAttackAnimation, rightRedAttackAnimation, upRedAttackAnimation, downRedAttackAnimation;
    private Animation leftGreenAttackAnimation, rightGreenAttackAnimation, upGreenAttackAnimation, downGreenAttackAnimation;
    private Animation playerDeadAnimation;
    private int resetTimer = 40;
    private int timer = resetTimer;
    private int colorNumber = 0;
    private boolean isDead;
    private TextureAtlas textureAtlas;

    public Player(float x, float y, float touchY, float touchX, TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        this.x = x;
        this.y = y;
        this.touchX = touchX;
        this.touchY = touchY;
        swordRect = new Rectangle(0,0,0,0);
        playerRect = new Rectangle(x, y, WIDTH, HEIGHT);
        playerTextureBlue = textureAtlas.findRegion("player_Blue_down_attack", 0);
        playerTextureRed = textureAtlas.findRegion("player_Red_down_attack", 0);
        playerTextureGreen = textureAtlas.findRegion("player_Green_down_attack", 0);
        animations();
        setResetTimer();
        left_attack = false;
        right_attack = false;
        up_attack = false;
        down_attack = false;
    }


    public void draw(SpriteBatch batch) {
        if (left_attack == true) {
            if (colorNumber == 0) {
                batch.draw(leftBlueAttackAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
            } else if (colorNumber == 1) {
                batch.draw(leftRedAttackAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
            } else if (colorNumber == 2) {
                batch.draw(leftGreenAttackAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
            }

        } else if (right_attack == true) {
            if (colorNumber == 0) {
                batch.draw(rightBlueAttackAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
            } else if (colorNumber == 1) {
                batch.draw(rightRedAttackAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
            } else if (colorNumber == 2) {
                batch.draw(rightGreenAttackAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
            }
        } else if (up_attack == true) {
            if (colorNumber == 0) {
                batch.draw(upBlueAttackAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
            } else if (colorNumber == 1) {
                batch.draw(upRedAttackAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
            } else if (colorNumber == 2) {
                batch.draw(upGreenAttackAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
            }
        } else if (down_attack == true) {
            if (colorNumber == 0) {
                batch.draw(downBlueAttackAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
            } else if (colorNumber == 1) {
                batch.draw(downRedAttackAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
            } else if (colorNumber == 2) {
                batch.draw(downGreenAttackAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
            }
        } else if (isDead == true) {
            batch.draw(playerDeadAnimation.getKeyFrame(animationTimer), x - 115, y - 120, 300, 300);
        } else {
            if (colorNumber == 0) {
                batch.draw(playerTextureBlue, x - 115, y - 120, 300, 300);
            } else if (colorNumber == 1) {
                batch.draw(playerTextureRed, x - 115, y - 120, 300, 300);
            } else if (colorNumber == 2) {
                batch.draw(playerTextureGreen, x - 115, y - 120, 300, 300);
            }
        }
    }

    private void animations() {
        playerBlueLeftAttack0 = textureAtlas.findRegion("player_Blue_left_attack", 1);
        playerBlueLeftAttack1 = textureAtlas.findRegion("player_Blue_left_attack", 2);
        playerBlueLeftAttack2 = textureAtlas.findRegion("player_Blue_left_attack", 3);
        playerBlueLeftAttack3 = textureAtlas.findRegion("player_Blue_left_attack", 4);
        TextureRegion[] playerBlueLeftAttack = {playerBlueLeftAttack0, playerBlueLeftAttack1, playerBlueLeftAttack2, playerBlueLeftAttack3};
        leftBlueAttackAnimation = new Animation(FRAME_DURATION, playerBlueLeftAttack);

        playerBlueRightAttack0 = textureAtlas.findRegion("player_Blue_right_attack", 1);
        playerBlueRightAttack1 = textureAtlas.findRegion("player_Blue_right_attack", 2);
        playerBlueRightAttack2 = textureAtlas.findRegion("player_Blue_right_attack", 3);
        playerBlueRightAttack3 = textureAtlas.findRegion("player_Blue_right_attack", 4);
        playerBlueRightAttack3.flip(true, false);
        TextureRegion[] playerBlueRightAttack = {playerBlueRightAttack0, playerBlueRightAttack1, playerBlueRightAttack2, playerBlueRightAttack3};
        rightBlueAttackAnimation = new Animation(FRAME_DURATION, playerBlueRightAttack);

        playerBlueUpAttack0 = textureAtlas.findRegion("player_Blue_up_attack", 1);
        playerBlueUpAttack1 = textureAtlas.findRegion("player_Blue_up_attack", 2);
        playerBlueUpAttack2 = textureAtlas.findRegion("player_Blue_up_attack", 3);
        playerBlueUpAttack3 = textureAtlas.findRegion("player_Blue_up_attack", 4);
        TextureRegion[] playerBlueUpAttack = {playerBlueUpAttack0, playerBlueUpAttack1, playerBlueUpAttack2, playerBlueUpAttack3};
        upBlueAttackAnimation = new Animation(FRAME_DURATION, playerBlueUpAttack);

        playerBlueDownAttack0 = textureAtlas.findRegion("player_Blue_down_attack", 1);
        playerBlueDownAttack1 = textureAtlas.findRegion("player_Blue_down_attack", 2);
        playerBlueDownAttack2 = textureAtlas.findRegion("player_Blue_down_attack", 3);
        playerBlueDownAttack3 = textureAtlas.findRegion("player_Blue_down_attack", 4);
        TextureRegion[] playerBlueDownAttack = {playerBlueDownAttack0, playerBlueDownAttack1, playerBlueDownAttack2, playerBlueDownAttack3};
        downBlueAttackAnimation = new Animation(FRAME_DURATION, playerBlueDownAttack);

        playerRedLeftAttack0 = textureAtlas.findRegion("player_Red_left_attack", 1);
        playerRedLeftAttack1 = textureAtlas.findRegion("player_Red_left_attack", 2);
        playerRedLeftAttack2 = textureAtlas.findRegion("player_Red_left_attack", 3);
        playerRedLeftAttack3 = textureAtlas.findRegion("player_Red_left_attack", 4);
        TextureRegion[] playerRedLeftAttack = {playerRedLeftAttack0, playerRedLeftAttack1, playerRedLeftAttack2, playerRedLeftAttack3};
        leftRedAttackAnimation = new Animation(FRAME_DURATION, playerRedLeftAttack);

        playerRedRightAttack0 = textureAtlas.findRegion("player_Red_right_attack", 1);
        playerRedRightAttack1 = textureAtlas.findRegion("player_Red_right_attack", 2);
        playerRedRightAttack2 = textureAtlas.findRegion("player_Red_right_attack", 3);
        playerRedRightAttack3 = textureAtlas.findRegion("player_Red_right_attack", 4);
        TextureRegion[] playerRedRightAttack = {playerRedRightAttack0, playerRedRightAttack1, playerRedRightAttack2, playerRedRightAttack3};
        rightRedAttackAnimation = new Animation(FRAME_DURATION, playerRedRightAttack);

        playerRedUpAttack0 = textureAtlas.findRegion("player_Red_up_attack", 1);
        playerRedUpAttack1 = textureAtlas.findRegion("player_Red_up_attack", 2);
        playerRedUpAttack2 = textureAtlas.findRegion("player_Red_up_attack", 3);
        playerRedUpAttack3 = textureAtlas.findRegion("player_Red_up_attack", 4);
        TextureRegion[] playerRedUpAttack = {playerRedUpAttack0, playerRedUpAttack1, playerRedUpAttack2, playerRedUpAttack3};
        upRedAttackAnimation = new Animation(FRAME_DURATION, playerRedUpAttack);

        playerRedDownAttack0 = textureAtlas.findRegion("player_Red_down_attack", 1);
        playerRedDownAttack1 = textureAtlas.findRegion("player_Red_down_attack", 2);
        playerRedDownAttack2 = textureAtlas.findRegion("player_Red_down_attack", 3);
        playerRedDownAttack3 = textureAtlas.findRegion("player_Red_down_attack", 4);
        TextureRegion[] playerRedDownAttack = {playerRedDownAttack0, playerRedDownAttack1, playerRedDownAttack2, playerRedDownAttack3};
        downRedAttackAnimation = new Animation(FRAME_DURATION, playerRedDownAttack);

        playerGreenLeftAttack0 = textureAtlas.findRegion("player_Green_left_attack", 1);
        playerGreenLeftAttack1 = textureAtlas.findRegion("player_Green_left_attack", 2);
        playerGreenLeftAttack2 = textureAtlas.findRegion("player_Green_left_attack", 3);
        playerGreenLeftAttack3 = textureAtlas.findRegion("player_Green_left_attack", 4);
        TextureRegion[] playerGreenLeftAttack = {playerGreenLeftAttack0, playerGreenLeftAttack1, playerGreenLeftAttack2, playerGreenLeftAttack3};
        leftGreenAttackAnimation = new Animation(FRAME_DURATION, playerGreenLeftAttack);

        playerGreenRightAttack0 = textureAtlas.findRegion("player_Green_right_attack", 1);
        playerGreenRightAttack1 = textureAtlas.findRegion("player_Green_right_attack", 2);
        playerGreenRightAttack2 = textureAtlas.findRegion("player_Green_right_attack", 3);
        playerGreenRightAttack3 = textureAtlas.findRegion("player_Green_right_attack", 4);
        TextureRegion[] playerGreenRightAttack = {playerGreenRightAttack0, playerGreenRightAttack1, playerGreenRightAttack2, playerGreenRightAttack3};
        rightGreenAttackAnimation = new Animation(FRAME_DURATION, playerGreenRightAttack);

        playerGreenUpAttack0 = textureAtlas.findRegion("player_Green_up_attack", 1);
        playerGreenUpAttack1 = textureAtlas.findRegion("player_Green_up_attack", 2);
        playerGreenUpAttack2 = textureAtlas.findRegion("player_Green_up_attack", 3);
        playerGreenUpAttack3 = textureAtlas.findRegion("player_Green_up_attack", 4);
        TextureRegion[] playerGreenUpAttack = {playerGreenUpAttack0, playerGreenUpAttack1, playerGreenUpAttack2, playerGreenUpAttack3};
        upGreenAttackAnimation = new Animation(FRAME_DURATION, playerGreenUpAttack);

        playerGreenDownAttack0 = textureAtlas.findRegion("player_Green_down_attack", 1);
        playerGreenDownAttack1 = textureAtlas.findRegion("player_Green_down_attack", 2);
        playerGreenDownAttack2 = textureAtlas.findRegion("player_Green_down_attack", 3);
        playerGreenDownAttack3 = textureAtlas.findRegion("player_Green_down_attack", 4);
        TextureRegion[] playerGreenDownAttack = {playerGreenDownAttack0, playerGreenDownAttack1, playerGreenDownAttack2, playerGreenDownAttack3};
        downGreenAttackAnimation = new Animation(FRAME_DURATION, playerGreenDownAttack);

        playerDead0 = textureAtlas.findRegion("player_Death", 0);
        playerDead1 = textureAtlas.findRegion("player_Death", 1);
        playerDead2 = textureAtlas.findRegion("player_Death", 2);
        playerDead3 = textureAtlas.findRegion("player_Death", 3);
        playerDead4 = textureAtlas.findRegion("player_Death", 4);
        playerDead5 = textureAtlas.findRegion("player_Death", 5);
        TextureRegion[] playerDead = {playerDead0, playerDead1, playerDead2, playerDead3, playerDead4, playerDead5};
        playerDeadAnimation = new Animation(DEAD_FRAME_DURATION, playerDead);
        playerDeadAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    public void update(float delta) {
        animationTimer += delta;

        if(leftBlueAttackAnimation.isAnimationFinished(animationTimer)) {
            resetAttack();
        }

        changeColor(delta);

    }

    private void setResetTimer() {
        resetTimer = 30;
        timer = resetTimer;
    }

    private void changeColor(float delta) {
        timer -= delta;
        if(timer <= 0){
            colorNumber++;
            timer = resetTimer;
            checkColor();
        }
    }

    private void checkColor() {
        if(colorNumber >= 3) {
            colorNumber = 0;
        }
    }

    public void attack(float screenX, float screenY) {
        if(screenY > touchY * 0.77) {
            upAttack();
        } else if (screenY < touchY * 0.59) {
            downAttack();
        } else if(screenX < touchX * 0.33) {
            leftAttack();
        } else if(screenX > touchX * 0.4) {
            rightAttack();
        }
    }

    public void upAttack() {
        swordRect.setWidth(SWORD_WIDTH);
        swordRect.setHeight(SWORD_HEIGHTY);
        swordRect.setX(x);
        swordRect.setY(y + HEIGHT);
        up_attack = true;
        animationTimer = 0;
    }

    public void leftAttack() {
        swordRect.setWidth(SWORD_WIDTHY);
        swordRect.setHeight(SWORD_HEIGHT);
        swordRect.setX(x - SWORD_WIDTHY);
        swordRect.setY(y);
        left_attack = true;
        right_attack = false;
        animationTimer = 0;
    }

    public void rightAttack() {
        swordRect.setWidth(SWORD_WIDTHY);
        swordRect.setHeight(SWORD_HEIGHT);
        swordRect.setX(x + WIDTH);
        swordRect.setY(y);
        right_attack = true;
        left_attack = false;
        animationTimer = 0;
    }

    public void downAttack() {
        swordRect.setWidth(SWORD_WIDTH);
        swordRect.setHeight(SWORD_HEIGHTY);
        swordRect.setX(x);
        swordRect.setY(y - SWORD_HEIGHTY);
        down_attack = true;
        animationTimer = 0;
    }

    public void resetAttack() {
        swordRect.setWidth(0);
        swordRect.setHeight(0);
        swordRect.setX(0);
        swordRect.setY(0);
        down_attack = false;
        up_attack = false;
        right_attack = false;
        left_attack = false;
    }

    public Rectangle getSwordCollisionRectangle() {
        return swordRect;
    }

    public Rectangle getPlayerRectangle() {
        return playerRect;
    }

    public int getColorNumber() {
        return colorNumber;
    }

    public void setIsDead() {
        animationTimer = 0;
        isDead = true;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
