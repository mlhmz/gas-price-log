package xyz.mlhmz.gaspricelog.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import xyz.mlhmz.gaspricelog.exceptions.ForecastGroupNotFoundException;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;
import xyz.mlhmz.gaspricelog.persistence.repositories.ForecastGroupRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Slf4j
public class ForecastGroupServiceImpl implements ForecastGroupService {
    @Inject
    ForecastGroupRepository repository;

    @Inject
    SpanService spanService;

    @Override
    @Transactional
    public ForecastGroup create(ForecastGroup group) {
        if (group.getEntries() != null && !group.getEntries().isEmpty()) {
            recalculateForecastGroupSpans(group);
        }
        ForecastGroup forecastGroup = repository.create(group);
        log.info("Created group with the uuid '{}'.", forecastGroup.getUuid());
        return forecastGroup;
    }

    @Override
    public ForecastGroup update(ForecastGroup group) {
        if (group.getEntries() != null && !group.getEntries().isEmpty()) {
            recalculateForecastGroupSpans(group);
        }
        ForecastGroup forecastGroup = repository.update(group);
        log.info("Updated group with the uuid '{}'.", forecastGroup.getUuid());
        return forecastGroup;
    }

    @Override
    public List<ForecastGroup> findAll() {
        return repository.findAll();
    }

    @Override
    public ForecastGroup findByUuid(UUID uuid) throws ForecastGroupNotFoundException {
        return repository.findByUuid(uuid).orElseThrow(() ->
                new ForecastGroupNotFoundException(String.format("The forecast group with the UUID '%s' couldn't be found.", uuid))
        );
    }

    @Override
    public void recalculateForecastGroupSpans(ForecastGroup forecastGroup) {
        deletePreviousSpans(forecastGroup);

        List<Span> spans = this.spanService.calculateSpanFromEntries(forecastGroup.getEntries());
        forecastGroup.setSpans(spans);
    }

    private void deletePreviousSpans(ForecastGroup forecastGroup) {
        if (forecastGroup.getSpans() != null && !forecastGroup.getSpans().isEmpty()) {
            this.spanService.deleteSpans(forecastGroup.getSpans());
            forecastGroup.getSpans().clear();
        }
    }
}
