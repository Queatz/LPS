package com.queatz.littlepiratesister;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.GameManager;
import com.queatz.littlepiratesister.game.ResourceManager;

public class Game extends ApplicationAdapter {
	private GameManager gameManager;

	@Override
	public void create() {
        gameManager = new GameManager();
	}

	@Override
	public void render() {
        gameManager.update();
        gameManager.render();
	}

    @Override
    public void resize(int width, int height) {
        gameManager.setViewport(new Vector2(width, height));
    }
	
	@Override
	public void dispose() {
        gameManager.dispose();
        ResourceManager.dispose();
	}
}
