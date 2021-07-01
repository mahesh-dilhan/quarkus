package io.mahesh.quarkus.battlems;

import io.mahesh.quarkus.battlems.model.Battle;
import io.mahesh.quarkus.battlems.model.Civilian;
import io.mahesh.quarkus.battlems.model.Thug;
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

        Uni<Civilian> civilian = ec.getCivilian()
                .ifNoItem().after(Duration.ofMillis(500)).fail()
                .onFailure().recoverWithItem(Civilian.FALLBACK);

        Uni<Thug> thug = ec.getThug()
                .ifNoItem().after(Duration.ofMillis(500)).fail()
                .onFailure().recoverWithItem(Thug.FALLBACK);

        // Combine both and call computeFightOutcome
        return Uni.combine().all().unis(civilian, thug)
                .combinedWith(this::computeFightOutcome);

    }

    //----------------------------------

    private Battle computeFightOutcome(Civilian civilian, Thug thug) {
        Battle battle = new Battle();
        battle.civilian = civilian;
        battle.thug = thug;
        int civilianAdjust = random.nextInt(20);
        int thugAdjust = random.nextInt(20);

        if ((civilian.level + civilianAdjust)
                > (thug.level + thugAdjust)) {
            battle.winner = civilian.name;
        } else if (civilian.level < thug.level) {
            battle.winner = thug.name;
        } else {
            battle.winner = random.nextBoolean() ?
                    civilian.name : thug.name;
        }
        sendToKafka(battle);
        return battle;
    }

    //----------------------------------

    @Inject @Channel("battles") Emitter<Battle> emitter;

    private void sendToKafka(Battle battle) {
        // send fight.
        emitter.send(battle);
    }


}
