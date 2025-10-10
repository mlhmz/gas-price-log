package xyz.mlhmz.gaspricelog.representation.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.representation.dtos.ForecastGroupDto;
import xyz.mlhmz.gaspricelog.representation.mappers.ForecastGroupMapper;
import xyz.mlhmz.gaspricelog.services.ForecastGroupService;

@Path("/api/v1/forecastgroups")
@Slf4j
public class ForecastGroupResource {
    @Inject
    ForecastGroupMapper mapper;

    @Inject
    ForecastGroupService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForecastGroup(ForecastGroupDto dto) {
        ForecastGroup group = mapper.fromDto(dto);
        ForecastGroup result = service.create(group);
        return Response.status(Response.Status.CREATED)
                .entity(mapper.toDto(result))
                .build();
    }
}
