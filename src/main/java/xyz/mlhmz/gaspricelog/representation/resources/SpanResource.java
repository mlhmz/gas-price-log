package xyz.mlhmz.gaspricelog.representation.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import xyz.mlhmz.gaspricelog.exceptions.SpanNotFoundException;
import xyz.mlhmz.gaspricelog.representation.dtos.ErrorDto;
import xyz.mlhmz.gaspricelog.representation.mappers.SpanMapper;
import xyz.mlhmz.gaspricelog.services.SpanService;

import java.util.UUID;

@Path("/api/v1/spans")
@Slf4j
public class SpanResource {
    @Inject
    SpanMapper mapper;

    @Inject
    SpanService spanService;

    @GET
    @Path("/{uuid}")
    public Response getById(@PathParam("uuid") UUID uuid) {
        try {
            return Response.ok(mapper.toDto(spanService.findByUuid(uuid))).build();
        } catch (SpanNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorDto(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage()))
                    .build();
        }
    }


}
