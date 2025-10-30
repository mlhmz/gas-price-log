package xyz.mlhmz.gaspricelog.representation.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;
import xyz.mlhmz.gaspricelog.representation.dtos.SpanDto;
import xyz.mlhmz.gaspricelog.representation.mappers.SpanMapper;
import xyz.mlhmz.gaspricelog.services.SpanService;

import java.util.List;

@Path("/api/v1/spans")
@Slf4j
public class SpanResource {
    @Inject
    SpanMapper mapper;

    @Inject
    SpanService spanService;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSpans() {
        return Response.ok(spanService.findAllSpans().stream().map(mapper::toDto)).build();
    }


}
