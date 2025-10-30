package xyz.mlhmz.gaspricelog.representation.dtos;

import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;

import java.util.UUID;

public record ForecastGroupReferenceDto(UUID uuid) {
    public static ForecastGroupReferenceDto ofEntity(ForecastGroup forecastGroup) {
        if (forecastGroup != null) {
            return new ForecastGroupReferenceDto(forecastGroup.getUuid());
        }
        return null;
    }
}
