package xyz.mlhmz.gaspricelog.services;

import xyz.mlhmz.gaspricelog.exceptions.ForecastGroupNotFoundException;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;

import java.util.List;
import java.util.UUID;

public interface ForecastGroupService {
    ForecastGroup create(ForecastGroup group);

    ForecastGroup update(ForecastGroup group);

    List<ForecastGroup> findAll();

    ForecastGroup findByUuid(UUID uuid) throws ForecastGroupNotFoundException;

    void recalculateForecastGroupSpans(ForecastGroup forecastGroup);
}
