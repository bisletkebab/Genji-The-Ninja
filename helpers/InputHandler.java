package helpers;

import com.badlogic.gdx.InputProcessor;

import screens.GameScreen;

/**
 * Created by Per on 13.07.2016.
 */
public class InputHandler implements InputProcessor {
    private GameScreen gameScreen;

    public InputHandler(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
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
        if(gameScreen.getState() == GameScreen.State.BEGINNING){
            gameScreen.setRunning();
        } else if(gameScreen.getState() == GameScreen.State.RUNNING){
            gameScreen.running(screenX, screenY);
        } else if(gameScreen.getState() == GameScreen.State.PAUSED) {
            gameScreen.paused(screenX,screenY);
        } else if(gameScreen.getState() == GameScreen.State.DEAD) {
            gameScreen.death(screenX, screenY);
        }

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