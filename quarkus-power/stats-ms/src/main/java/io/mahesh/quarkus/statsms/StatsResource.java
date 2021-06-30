package io.mahesh.quarkus.statsms;

import io.mahesh.quarkus.statsms.model.Stats;
import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.annotations.SseElementType;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/stats")
public class StatsResource {

    @Inject @Channel("stats") Multi<Stats> stats;

    @GET
    @SseElementType(MediaType.APPLICATION_JSON)
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<Stats> getStats() {
        return stats;
    }
}
