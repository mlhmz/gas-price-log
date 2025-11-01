package xyz.mlhmz.gaspricelog.representation.mappers;

import jakarta.enterprise.context.ApplicationScoped;
import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.representation.dtos.EntryDto;
import xyz.mlhmz.gaspricelog.representation.dtos.ForecastGroupReferenceDto;

import java.util.UUID;

@ApplicationScoped
public class EntryMapperImpl implements EntryMapper {
    @Override
    public EntryDto toDto(Entry entry) {
        return EntryDto.builder()
                .uuid(entry.getUuid())
                .value(entry.getValue())
                .date(entry.getDate())
                .createdAt(entry.getCreatedAt())
                .forecastGroup(ForecastGroupReferenceDto.ofEntity(entry.getForecastGroup()))
                .build();
    }

    @Override
    public Entry fromDto(EntryDto entryDto) {
        return fromDto(entryDto, null);
    }

    @Override
    public Entry fromDto(EntryDto entryDto, ForecastGroup forecastGroup) {
        return Entry.builder()
                .uuid(entryDto.uuid())
                .value(entryDto.value())
                .date(entryDto.date())
                .forecastGroup(forecastGroup)
                .build();
    }
}
