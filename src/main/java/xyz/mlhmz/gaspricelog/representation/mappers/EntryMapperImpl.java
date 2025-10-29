package xyz.mlhmz.gaspricelog.representation.mappers;

import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.representation.dtos.EntryDto;

import java.util.UUID;

public class EntryMapperImpl implements EntryMapper {
    @Override
    public EntryDto toDto(Entry entry) {
        return EntryDto.builder()
                .uuid(entry.getUuid())
                .value(entry.getValue())
                .date(entry.getDate())
                .createdAt(entry.getCreatedAt())
                .forecastGroupUuid(retrieveForecastGroupUuid(entry))
                .build();
    }

    private UUID retrieveForecastGroupUuid(Entry entry) {
        ForecastGroup forecastGroup = entry.getForecastGroup();
        if (forecastGroup != null) {
            return forecastGroup.getUuid();
        }
        return null;
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
