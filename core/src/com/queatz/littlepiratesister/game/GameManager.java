package com.queatz.littlepiratesister.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Existential;
import com.queatz.littlepiratesister.game.engine.Input;
import com.queatz.littlepiratesister.game.engine.Resources;
import com.queatz.littlepiratesister.game.engine.Sentiment;
import com.queatz.littlepiratesister.game.engine.Update;
import com.queatz.littlepiratesister.game.things.AI;
import com.queatz.littlepiratesister.game.things.Encampment;
import com.queatz.littlepiratesister.game.things.Player;
import com.queatz.littlepiratesister.game.things.Ship;
import com.queatz.littlepiratesister.game.things.Target;
import com.queatz.littlepiratesister.game.things.Thing;
import com.queatz.littlepiratesister.game.things.World;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Random;

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

    private Existential playerAI;
    private Existential player2AI;
    public UIManager uiManager;

    public GameManager() {
        world = new World();
        camera = new Camera();
        lastFrame = new Date();

        setupWorld();

        input = new Input(this);
        Gdx.input.setInputProcessor(input);
    }

    private void setupWorld() {
        player = new Player();

        AI ai = new AI();
        ai.sentimentMultiplier = -10f;

        player2AI = new Existential().thing(ai).resources(new Resources());
        world.add(player2AI);

        ai = new AI();
        ai.sentimentMultiplier = 10f;
        playerAI = new Existential().thing(ai).resources(new Resources());
        world.add(playerAI);

        Sentiment playerSentiment = new Sentiment();
        playerSentiment.power = 6f;
        playerSentiment.value = 5f;
        playerSentiment.capacity = 5f;
        playerSentiment.defense = 3f;

        world.add(new Existential().thing(player).sentiment(playerSentiment));
        for (int i = 0; i < 5; i++) {
            Sentiment sentiment = new Sentiment();
            sentiment.capacity = (float) (Math.random() * 120f);
            sentiment.power = (float) (Math.random() * .034f);
            sentiment.defense = (float) (Math.random() * 0.04f);
            sentiment.value = -sentiment.capacity;

            Existential encampment = new Existential()
                    .thing(new Encampment())
                    .position(new Vector3(1000f * ((float) Math.random() - .5f), 1000f * ((float) Math.random() - .5f), 0))
                    .sentiment(sentiment);
            world.add(encampment);

            int n = new Random().nextInt(7);
            for (int s = 0; s < n; s++) {
                addShip(encampment);
            }
        }
    }

    private void addShip(Existential encampment) {
        Ship ship = new Ship();
        Sentiment sentiment = new Sentiment();
        sentiment.capacity = Math.random() > .75 ? 20 : 10;
        sentiment.value = -sentiment.capacity;
        sentiment.power = 1;
        sentiment.defense = Math.random() > .75 ? 4 : 1;

        Vector3 pos = new Vector3((float) (Math.random() - .5f) * 60, (float) (Math.random() - .5f) * 60, 0).add(encampment.position);

        ship.setTarget(pos);
        world.add(new Existential()
                .thing(ship)
                .sentiment(sentiment)
                .position(pos));
    }

    public void update() {
        Update update = new Update();
        Date now = new Date();
        update.delta = ((double) now.getTime() - (double) lastFrame.getTime()) / 1000d;
        update.world = world;
        update.game = this;
        lastFrame = new Date();

        // Stutters of over 1 second can cause horrible logic
        if (update.delta > 1) {
            update.delta = 0;
        }

        input.update(update);
        world.update(update);

        // Center on player
        camera.setPosition(world.getPosition(player));
    }

    public void render() {
        camera.render(world);

        Vector3 position = new Vector3(
                -camera.getViewport().x * camera.getZoom() / 2 + 10,
                -camera.getViewport().y * camera.getZoom() / 2 + 10 + 32,
                0
        );

        camera.setPersp(false);
        FontManager.write(camera, "$" + NumberFormat.getInstance().format((int) getPlayerAI().resources.money), position, true);

        String text = "Village of Maqruelle, France";

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(FontManager.getFont(), text);

        FontManager.getFont().setColor(Color.WHITE);

        float s = .3333f;

        position = new Vector3(
                -glyphLayout.width * s / 2,
                camera.getViewport().y * camera.getZoom() / 2 - 64 * s,
                0
        );

        FontManager.write(camera, text, position, true, s);

        camera.setPersp(true);
    }

    public void dispose() {
        world = null;
        camera = null;
        input = null;
        Gdx.input.setInputProcessor(null);
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

    public Existential getPlayerAI() {
        return playerAI;
    }

    public Existential getPlayer2AI() {
        return player2AI;
    }

    public void tap(Vector3 tap) {
        Vector3 position = camera.screenToWorld(tap);

        if (getPlayerAI().resources.money < 50) {
            return;
        }


        Existential closestPositive = null;
        float closestPositiveDistance = -1;
        Existential closestNegative = null;
        float closestNegativeDistance = -1;

        for (Existential existential : world.things.values()) {
            if (Player.class.isAssignableFrom(existential.thing.getClass())) {
                continue;
            }

            if (Encampment.class.isAssignableFrom(existential.thing.getClass())) {
                float dist = position.dst(existential.position);

                if (existential.sentiment.value < 0) {
                    if (closestNegativeDistance == -1 || dist < closestNegativeDistance) {
                        closestNegativeDistance = dist;
                        closestNegative = existential;
                    }
                } else if (existential.sentiment.value > 0) {
                    if (closestPositiveDistance == -1 || dist < closestPositiveDistance) {
                        closestPositiveDistance = dist;
                        closestPositive = existential;
                    }
                }
            }
        }

        if (closestNegative != null && closestPositive != null) {
            if (closestNegativeDistance < closestPositiveDistance) {
                target(closestPositive, closestNegative);
            } else {
                upgrade(closestPositive);
            }
        } else if (closestPositive != null) {
            upgrade(closestPositive);
        }
    }

    private void upgrade(Existential closestPositive) {
        getPlayerAI().resources.money -= 50;
        closestPositive.sentiment.value += 50;
        closestPositive.sentiment.capacity += 50;
        world.add(new Existential().thing(new Target()).position(closestPositive.position));
    }

    private void target(Existential from, Existential to) {
        getPlayerAI().resources.money -= 50;

        float sentimentMultiplier = 10;

        Ship ship = new Ship();
        Sentiment sentiment = new Sentiment();
        sentiment.capacity = Math.random() > .75 ? Math.abs(sentimentMultiplier * 2) : Math.abs(sentimentMultiplier);
        sentiment.value = sentimentMultiplier;
        sentiment.power = 1;
        sentiment.defense = Math.random() > .75 ? 4 : 1;
        ship.setTarget(new Vector3((float) (Math.random() * .5) * 30, (float) (Math.random() * .5) * 30, 0).add(to.position));
        world.add(new Existential()
                .thing(ship)
                .sentiment(sentiment)
                .position(from.position));

        world.add(new Existential().thing(new Target()).position(to.position));
    }

    public boolean click(Vector3 position) {
        return uiManager.tap(new Vector2(position.x, position.y));
    }
}