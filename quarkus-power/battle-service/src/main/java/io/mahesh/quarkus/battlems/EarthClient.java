package io.mahesh.quarkus.battlems;

import io.smallrye.mutiny.Uni;
import io.mahesh.quarkus.battlems.model.Civilian;
import io.mahesh.quarkus.battlems.model.Thug;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "earth-service")
@Produces(MediaType.APPLICATION_JSON)
public interface EarthClient {

    @GET
    @Path("/civilian")
    Uni<Civilian> getCivilian();

    @GET
    @Path("/thug")
    Uni<Thug> getThug();

}
