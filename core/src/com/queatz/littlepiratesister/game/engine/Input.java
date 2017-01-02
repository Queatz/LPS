package com.queatz.littlepiratesister.game.engine;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.GameManager;

/**
 * Created by jacob on 1/1/17.
 */

public class Input implements InputProcessor {

    private GameManager gameManager;
    private Vector2 start = new Vector2();
    private Vector2 dragging;
    private float sensitivity = 1;
    private float maxSpeed = 200;

    public Input(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // ignore if its not left mouse button or first touch pointer
        if (button != com.badlogic.gdx.Input.Buttons.LEFT || pointer > 0) {
            return false;
        }

        start.x = screenX;
        start.y = screenY;
        dragging = new Vector2(start);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button != com.badlogic.gdx.Input.Buttons.LEFT || pointer > 0) {
            return false;
        }

        dragging = null;

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (dragging == null) {
            return false;
        }

        dragging.x = screenX;
        dragging.y = screenY;

        return true;
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
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void update(Update update) {
        // Player movement
        if (dragging != null) {
            Vector2 pos = new Vector2(
                    (int) (dragging.x - start.x) / sensitivity,
                    -(int) (dragging.y - start.y) / sensitivity
            );

            // Find the desired movement speed
            float scl = Math.min(maxSpeed, pos.len() * gameManager.getCamera().getZoom()) * (float) update.delta;

            // Move in the desired direction at that speed
            gameManager.getWorld().move(gameManager.getPlayer(), pos.nor().scl(scl));
        }
    }
}
