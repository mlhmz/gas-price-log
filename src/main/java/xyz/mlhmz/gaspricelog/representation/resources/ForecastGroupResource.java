package xyz.mlhmz.gaspricelog.representation.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import xyz.mlhmz.gaspricelog.exceptions.ForecastGroupNotFoundException;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.representation.dtos.ErrorDto;
import xyz.mlhmz.gaspricelog.representation.dtos.ForecastGroupDto;
import xyz.mlhmz.gaspricelog.representation.mappers.ForecastGroupMapper;
import xyz.mlhmz.gaspricelog.services.ForecastGroupService;

import java.util.UUID;

@Path("/api/v1/forecastgroups")
@Slf4j
public class ForecastGroupResource {
    @Inject
    ForecastGroupMapper mapper;

    @Inject
    ForecastGroupService forecastGroupService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ForecastGroupDto dto) {
        ForecastGroup group = mapper.fromDto(dto);
        ForecastGroup result = forecastGroupService.create(group);
        return Response.status(Response.Status.CREATED)
                .entity(mapper.toDto(result))
                .build();
    }

    @GET
    @Path("/{uuid}")
    public Response getById(@PathParam("uuid") UUID uuid) {
        try {
            return Response.ok(mapper.toDto(forecastGroupService.findByUuid(uuid))).build();
        } catch (ForecastGroupNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorDto(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage()))
                    .build();
        }
    }
}
