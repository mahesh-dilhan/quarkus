package io.mahesh.quarkus.battlems;

import io.mahesh.quarkus.battlems.model.Battle;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.annotations.SseElementType;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;

@Path("/battle")
public class FightResource {

    @Inject
    BattleService fights;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Battle> fight() {
        return fights.fight();
    }


    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<Battle> stream() {
        Multi<Long> ticks = Multi.createFrom().ticks()
            .every(Duration.ofSeconds(1));
        return ticks
                    .onItem().transformToUniAndMerge(x -> fight());
    }
}
