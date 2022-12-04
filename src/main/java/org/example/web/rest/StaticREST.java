package org.example.web.rest;

import org.eclipse.jetty.util.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Objects;

@Path("/")
public class StaticREST {

    @GET
    @Consumes(MediaType.TEXT_HTML)
    public Response getInfo() throws IOException {
        String info  = new String(Objects
                .requireNonNull(Resource.newResource(this
                        .getClass()
                        .getResource("/static/info.html")))
                .getInputStream()
                .readAllBytes());
        return Response
                .ok(info)
                .build();
    }
}
