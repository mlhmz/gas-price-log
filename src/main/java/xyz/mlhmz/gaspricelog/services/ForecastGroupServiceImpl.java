package xyz.mlhmz.gaspricelog.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import xyz.mlhmz.gaspricelog.exceptions.ForecastGroupNotFoundException;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;
import xyz.mlhmz.gaspricelog.persistence.repositories.ForecastGroupRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ForecastGroupServiceImpl implements ForecastGroupService {
    @Inject
    ForecastGroupRepository repository;

    @Inject
    SpanService spanService;

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public ForecastGroup create(ForecastGroup group) {
        if (group.getEntries() != null && !group.getEntries().isEmpty()) {
            recalculateForecastGroupSpans(group);
        }
        return repository.create(group);
    }

    @Override
    public ForecastGroup update(ForecastGroup group) {
        if (group.getEntries() != null && !group.getEntries().isEmpty()) {
            recalculateForecastGroupSpans(group);
        }
        return repository.update(group);
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
