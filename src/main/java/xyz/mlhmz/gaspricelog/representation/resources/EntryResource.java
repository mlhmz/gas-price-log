package xyz.mlhmz.gaspricelog.representation.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import xyz.mlhmz.gaspricelog.exceptions.ForecastGroupNotFoundException;
import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.representation.dtos.EntryDto;
import xyz.mlhmz.gaspricelog.representation.dtos.ErrorDto;
import xyz.mlhmz.gaspricelog.representation.mappers.EntryMapper;
import xyz.mlhmz.gaspricelog.representation.mappers.ForecastGroupMapper;
import xyz.mlhmz.gaspricelog.services.EntryService;
import xyz.mlhmz.gaspricelog.services.ForecastGroupService;

@Path("/api/v1/entries")
@Slf4j
public class EntryResource {
    @Inject
    EntryService entryService;

    @Inject
    EntryMapper entryMapper;

    @Inject
    ForecastGroupService forecastGroupService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEntry(EntryDto entryDto) {
        try {
            Entry entry;
            if (entryDto.forecastGroupUuid() != null) {
                ForecastGroup forecastGroup = forecastGroupService.findByUuid(entryDto.uuid());
                entry = entryMapper.fromDto(entryDto, forecastGroup);
            } else {
                entry = entryMapper.fromDto(entryDto);
            }
            Entry savedEntry = entryService.createEntry(entry);
            return Response.status(201).entity(entryMapper.toDto(savedEntry)).build();
        } catch (ForecastGroupNotFoundException exception) {
            return Response.status(404)
                    .entity(new ErrorDto(404, exception.getMessage()))
                    .build();
        }

    }
}
