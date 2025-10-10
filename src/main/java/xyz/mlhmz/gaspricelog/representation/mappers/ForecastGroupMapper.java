package xyz.mlhmz.gaspricelog.representation.mappers;

import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.representation.dtos.ForecastGroupDto;

public interface ForecastGroupMapper {
    ForecastGroupDto toDto(ForecastGroup forecastGroup);

    ForecastGroup fromDto(ForecastGroupDto dto);
}
