package xyz.mlhmz.gaspricelog.representation.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import xyz.mlhmz.gaspricelog.exceptions.EntryNotFoundException;
import xyz.mlhmz.gaspricelog.exceptions.ForecastGroupNotFoundException;
import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.representation.dtos.EntryDto;
import xyz.mlhmz.gaspricelog.representation.dtos.ErrorDto;
import xyz.mlhmz.gaspricelog.representation.dtos.ForecastGroupReferenceDto;
import xyz.mlhmz.gaspricelog.representation.mappers.EntryMapper;
import xyz.mlhmz.gaspricelog.services.EntryService;
import xyz.mlhmz.gaspricelog.services.ForecastGroupService;

import java.util.List;
import java.util.UUID;

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
    public Response create(EntryDto entryDto) {
        try {
            Entry entry;
            ForecastGroupReferenceDto forecastGroupReference = entryDto.forecastGroup();
            if (forecastGroupReference != null) {
                ForecastGroup forecastGroup = forecastGroupService.findByUuid(forecastGroupReference.uuid());
                entry = entryMapper.fromDto(entryDto, forecastGroup);
            } else {
                entry = entryMapper.fromDto(entryDto);
            }
            Entry savedEntry = entryService.createEntry(entry);
            return Response.status(Response.Status.CREATED).entity(entryMapper.toDto(savedEntry)).build();
        } catch (ForecastGroupNotFoundException exception) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorDto(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage()))
                    .build();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Entry> entries = entryService.findAll();
        return Response.ok(entries.stream().map(entryMapper::toDto).toList()).build();
    }

    @GET
    @Path("/{uuid}")
    public Response getById(@PathParam("uuid") UUID uuid) {
        try {
            return Response.ok(entryMapper.toDto(entryService.findByUuid(uuid))).build();
        } catch (EntryNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorDto(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage()))
                    .build();
        }
    }
}
