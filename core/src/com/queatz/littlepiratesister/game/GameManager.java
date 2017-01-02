package com.queatz.littlepiratesister.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Input;
import com.queatz.littlepiratesister.game.engine.Update;
import com.queatz.littlepiratesister.game.things.Player;
import com.queatz.littlepiratesister.game.things.Thing;
import com.queatz.littlepiratesister.game.things.World;

import java.util.Date;

/**
 * Created by jacob on 12/30/16.
 *
 * Handles game loading, lifecycle, system events.
 */

public class GameManager {

    private World world;
    private Camera camera;
    private Date lastFrame;
    private Thing player;
    private Input input;

    public GameManager() {
        world = new World();
        camera = new Camera();
        lastFrame = new Date();

        player = new Player();
        world.add(player);

        input = new Input(this);
        Gdx.input.setInputProcessor(input);
    }

    public void update() {
        Update update = new Update();
        Date now = new Date();
        update.delta = ((double) now.getTime() - (double) lastFrame.getTime()) / 1000d;
        lastFrame = new Date();
        input.update(update);
        world.update(update);

        // Center on player
        camera.setPosition(world.positionOf(player));
    }

    public void render() {
        camera.render(world);
        //ui.render;
    }

    public void dispose() {
        world = null;
        camera = null;
    }

    public void setViewport(Vector2 viewport) {
        this.camera.setViewport(viewport);
    }

    public World getWorld() {
        return world;
    }

    public Thing getPlayer() {
        return player;
    }

    public Camera getCamera() {
        return camera;
    }
}
