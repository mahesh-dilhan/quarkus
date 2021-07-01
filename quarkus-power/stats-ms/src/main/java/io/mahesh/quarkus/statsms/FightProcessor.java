package io.mahesh.quarkus.statsms;

import io.mahesh.quarkus.statsms.model.Fight;
import io.mahesh.quarkus.statsms.model.Stats;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FightProcessor {

    @Incoming("fights")
    @Outgoing("stats")
    @Broadcast
    public Multi<Stats> process(Multi<Fight> fights) {
       return fights
               .onItem().scan(
                       Stats::new,
                       this::compute
       );
    }

    private Stats compute(Stats s, Fight f) {
        if (f.winner.equals(f.civilian.name)) {
            s.wonByCivilians = s.wonByCivilians + 1;
        } else {
            s.wonByThugs = s.wonByThugs + 1;
        }
        return s;
    }

}
