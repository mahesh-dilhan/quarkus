package io.mahesh.quarkus.battlems;

import io.mahesh.quarkus.battlems.model.Battle;
import io.mahesh.quarkus.battlems.model.Civilian;
import io.mahesh.quarkus.battlems.model.Villain;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.util.Random;

@ApplicationScoped
public class BattleService {

    @Inject @RestClient
    EarthClient ec;

    private Random random = new Random();

    public Uni<Battle> fight() {

        // Retrieve hero and villain
        Uni<Civilian> hero = ec.getCivilian()
                .ifNoItem().after(Duration.ofMillis(500)).fail()
                .onFailure().recoverWithItem(Civilian.FALLBACK);

        Uni<Villain> villain = ec.getThug()
                .ifNoItem().after(Duration.ofMillis(500)).fail()
                .onFailure().recoverWithItem(Villain.FALLBACK);

        // Combine both and call computeFightOutcome
        return Uni.combine().all().unis(hero, villain)
                .combinedWith(this::computeFightOutcome);

    }

    //----------------------------------

    private Battle computeFightOutcome(Civilian civilian, Villain villain) {
        Battle battle = new Battle();
        battle.civilian = civilian;
        battle.villain = villain;
        int heroAdjust = random.nextInt(20);
        int villainAdjust = random.nextInt(20);

        if ((civilian.level + heroAdjust)
                > (villain.level + villainAdjust)) {
            battle.winner = civilian.name;
        } else if (civilian.level < villain.level) {
            battle.winner = villain.name;
        } else {
            battle.winner = random.nextBoolean() ?
                    civilian.name : villain.name;
        }
        sendToKafka(battle);
        return battle;
    }

    //----------------------------------

    @Inject @Channel("fights") Emitter<Battle> emitter;

    private void sendToKafka(Battle battle) {
        // send fight.
        emitter.send(battle);
    }


}
