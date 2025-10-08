package xyz.mlhmz.gaspricelog.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.persistence.repositories.ForecastGroupRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ForecastGroupServiceImpl implements ForecastGroupService {
    @Inject
    private ForecastGroupRepository repository;
    @Inject
    private EntityManager entityManager;

    @Override
    @Transactional
    public ForecastGroup create(ForecastGroup group) {
        repository.create(group);
        return group;
    }

    @Override
    public List<ForecastGroup> findAll() {
        return repository.findAll();
    }

    @Override
    public ForecastGroup findByUuid(UUID uuid) {
        return repository.findByUuid(uuid).orElse(null);
    }
}
