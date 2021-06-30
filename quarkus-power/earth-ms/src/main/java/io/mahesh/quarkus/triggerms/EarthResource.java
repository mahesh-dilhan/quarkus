package io.mahesh.quarkus.triggerms;

import io.mahesh.quarkus.triggerms.model.Civilian;
import io.mahesh.quarkus.triggerms.model.Thug;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.annotations.SseElementType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;

@Path("/earth")
@Produces(MediaType.APPLICATION_JSON)
public class EarthResource {

    @GET
    @Path("/civilian")
    public Uni<Civilian> civilian() {

        return Civilian.findRandom();
    }

    @GET
    @Path("/thug")
    public Uni<Thug> thug() {

        return Thug.findRandom();
    }

    @GET
    @Path("/civilians")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<Civilian> stream() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onItem().produceUni(x -> civilian()).merge();
    }





}
