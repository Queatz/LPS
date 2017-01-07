package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
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

    @Override
    public void update(Update update) {
        // Check money

        nextAssessment -= update.delta;

        if(nextAssessment <= 0) {
            nextAssessment += 3f;
            assess(update.world);
        }
    }

    private void assess(World world) {
        if (sentimentMultiplier > 0) {
            return;
        }

        if (this.existential.resources.money < 50) {
            return;
        }

        List<Existential> options = new ArrayList<>();
        List<Existential> sendFromOptions = new ArrayList<>();

        for (Existential existential : world.things.values()) {
            Class clazz = existential.thing.getClass();

            if (Encampment.class.isAssignableFrom(clazz)) {
                Encampment encampment = (Encampment) existential.thing;
                if (Math.signum(encampment.existential.sentiment.value) != Math.signum(sentimentMultiplier)) {
                    options.add(existential);
                } else {
                    sendFromOptions.add(existential);
                }
            }
        }

        if (!options.isEmpty() && !sendFromOptions.isEmpty()) {
            this.existential.resources.money -= 50;

            Existential pick = options.get(new Random().nextInt(options.size()));
            Existential pickSendFrom = sendFromOptions.get(new Random().nextInt(sendFromOptions.size()));

            Ship ship = new Ship();
            Sentiment sentiment = new Sentiment();
            sentiment.capacity = Math.random() > .75 ? Math.abs(sentimentMultiplier * 2) : Math.abs(sentimentMultiplier);
            sentiment.value = sentimentMultiplier;
            sentiment.power = 1;
            sentiment.defense = Math.random() > .75 ? 4 : 1;
            ship.setTarget(new Vector3((float) (Math.random() * .5) * 30, (float) (Math.random() * .5) * 30, 0).add(pick.position));
            world.add(new Existential()
                    .thing(ship)
                    .sentiment(sentiment)
                    .position(pickSendFrom.position));
        }
    }

    @Override
    public Decal render(Camera camera) {
        return null; // Nothing
    }
}
