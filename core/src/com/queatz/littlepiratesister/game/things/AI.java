package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.GameManager;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Existential;
import com.queatz.littlepiratesister.game.engine.Sentiment;
import com.queatz.littlepiratesister.game.engine.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jacob on 1/2/17.
 */

public class AI extends Thing {

    private float nextAssessment = 3;
    public float sentimentMultiplier = 0;
    private float idleTime = 0;

    @Override
    public void update(Update update) {
        idleTime += update.delta;
        nextAssessment -= update.delta;

        if(nextAssessment <= 0) {
            nextAssessment += 1f;
            assess(update.world, update.game);
        } else if (idleTime > 5) {
            idleTime = 0;

            if (sentimentMultiplier < 0) {
                randomMovements(update.world);
            }
        }
    }

    private void randomMovements(World world) {
        Existential ship = randomShip(world, sentimentMultiplier);

        if (ship == null) {
            return;
        }

        Existential encampment = randomEncampment(world, sentimentMultiplier);

        if (encampment == null) {
            return;
        }

        moveShipTo((Ship) ship.thing, encampment.position);
    }

    private Existential closestEncampment(World world, float sentimentMultiplier, Vector3 position) {
        Existential closest = null;
        float closestDistance = 0;

        for (Existential existential : world.things.values()) {
            Class clazz = existential.thing.getClass();

            if (Encampment.class.isAssignableFrom(clazz)) {
                Encampment encampment = (Encampment) existential.thing;
                if (Math.signum(encampment.existential.sentiment.value) == Math.signum(sentimentMultiplier)) {
                    float distance = position.dst(existential.position);

                    if (closest == null || closestDistance > distance) {
                        closest = existential;
                        closestDistance = distance;
                    }
                }
            }
        }

        return closest;
    }

    private Existential randomShip(World world, float sentiment) {
        List<Existential> options = new ArrayList<>();

        for (Existential existential : world.things.values()) {
            Class clazz = existential.thing.getClass();

            if (Ship.class.isAssignableFrom(clazz)) {
                if (Math.signum(existential.sentiment.value) == Math.signum(sentiment)) {
                    options.add(existential);
                }
            }
        }

        if (options.isEmpty()) {
            return null;
        }

        return options.get(new Random().nextInt(options.size()));
    }

    private Existential randomEncampment(World world, float sentiment) {
        List<Existential> options = new ArrayList<>();

        for (Existential existential : world.things.values()) {
            Class clazz = existential.thing.getClass();

            if (Encampment.class.isAssignableFrom(clazz)) {
                Encampment encampment = (Encampment) existential.thing;
                if (Math.signum(encampment.existential.sentiment.value) == Math.signum(sentiment)) {
                    options.add(existential);
                }
            }
        }

        if (options.isEmpty()) {
            return null;
        }

        return options.get(new Random().nextInt(options.size()));
    }

    private void assess(World world, GameManager game) {
        if (sentimentMultiplier > 0) {
            return;
        }

        Existential pick = randomEncampment(world, -sentimentMultiplier);

        if (pick == null) {
            chasePlayer(world, game.getPlayer());
            return;
        }

        if (this.existential.resources.money < 50) {
            Existential ship = randomShip(world, sentimentMultiplier);

            if (ship != null) {

                pick = closestEncampment(world, -sentimentMultiplier, ship.position);

                if (pick != null) {
                    moveShipTo((Ship) ship.thing, pick.position);
                }
            }

            return;
        }

        Existential pickSendFrom = randomEncampment(world, sentimentMultiplier);

        if (pickSendFrom == null) {
            return;
        }

        this.existential.resources.money -= 50;

        idleTime = 0;

        Ship ship = new Ship();
        Sentiment sentiment = new Sentiment();
        sentiment.capacity = Math.random() > .75 ? Math.abs(sentimentMultiplier * 2) : Math.abs(sentimentMultiplier);
        sentiment.value = sentimentMultiplier;
        sentiment.power = 1;
        sentiment.defense = Math.random() > .75 ? 4 : 1;
        Existential e = new Existential()
                .thing(ship)
                .sentiment(sentiment)
                .position(pickSendFrom.position);
        world.add(e);
        moveShipTo(ship, pick.position);
    }

    private void chasePlayer(World world, Thing player) {
        Existential ship = randomShip(world, sentimentMultiplier);

        Existential targetShip;

        if (Math.signum(player.existential.sentiment.value) != Math.signum(sentimentMultiplier)) {
            targetShip = player.existential;
        } else {
            targetShip = randomShip(world, -sentimentMultiplier);
        }

        if (ship == null || targetShip == null) {
            return;
        }

        moveShipTo(ship, targetShip);
    }

    private void moveShipTo(Existential ship, Existential targetShip) {
        ((Ship) ship.thing).setTarget(targetShip);
    }

    private void moveShipTo(Ship ship, Vector3 position) {
        ship.setTarget(new Vector3((float) (Math.random() * .5) * 30, (float) (Math.random() * .5) * 30, 0).add(position));
    }

    @Override
    public Decal render(Camera camera) {
        return null; // Nothing
    }
}
