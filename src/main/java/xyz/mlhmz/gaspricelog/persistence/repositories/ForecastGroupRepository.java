package xyz.mlhmz.gaspricelog.persistence.repositories;

import jakarta.data.Order;
import jakarta.data.repository.*;
import jakarta.transaction.Transactional;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Repository
public interface ForecastGroupRepository {
    @Find
    Optional<ForecastGroup> findByUuid(UUID uuid);

    @Find
    List<ForecastGroup> findAll();

    @Find
    List<ForecastGroup> findAll(Order<ForecastGroup> order);

    @Insert
    ForecastGroup create(ForecastGroup forecastGroup);

    @Update
    ForecastGroup update(ForecastGroup forecastGroup);

    @Delete
    void deleteByUUID(UUID uuid);
}
