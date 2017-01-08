package com.queatz.littlepiratesister.game.engine;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.UIManager;

/**
 * Created by jacob on 1/4/17.
 */

public class UIInput implements InputProcessor {

    private UIManager uiManager;

    public UIInput(UIManager uiManager) {
        this.uiManager = uiManager;
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
        return uiManager.startTap(new Vector2(screenX, screenY));
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        uiManager.tap(new Vector2(screenX, screenY));
        return true;
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
