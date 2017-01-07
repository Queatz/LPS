package com.queatz.littlepiratesister;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.FontManager;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.SceneManager;
import com.queatz.littlepiratesister.game.ShaderManager;
import com.queatz.littlepiratesister.game.scenes.MainScene;

public class Game extends ApplicationAdapter {
	@Override
	public void create() {
        SceneManager.set(new MainScene());
	}

	@Override
	public void render() {
        SceneManager.update();
        SceneManager.render();
	}

    @Override
    public void resize(int width, int height) {
        SceneManager.resize(new Vector2(width, height));
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
	public void dispose() {
        SceneManager.dispose();
        ResourceManager.dispose();
        ShaderManager.dispose();
        FontManager.dispose();
    }
}
