package xyz.mlhmz.gaspricelog.representation.mappers;

import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.representation.dtos.EntryDto;

public interface EntryMapper {
    EntryDto toDto(Entry entry);

    Entry fromDto(EntryDto entryDto);

    Entry fromDto(EntryDto entryDto, ForecastGroup forecastGroup);
}
