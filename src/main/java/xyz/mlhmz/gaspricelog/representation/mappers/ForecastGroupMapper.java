package xyz.mlhmz.gaspricelog.representation.mappers;

import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;
import xyz.mlhmz.gaspricelog.representation.dtos.ForecastGroupDto;

import java.util.List;

public interface ForecastGroupMapper {
    ForecastGroupDto toDto(ForecastGroup forecastGroup);

    ForecastGroup fromDto(ForecastGroupDto forecastGroupDto, List<Entry> entries, List<Span> spans);

    ForecastGroup fromDto(ForecastGroupDto dto);
}
