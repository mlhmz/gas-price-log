package xyz.mlhmz.gaspricelog.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import xyz.mlhmz.gaspricelog.exceptions.EntryNotFoundException;
import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.persistence.repositories.EntryRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EntryServiceImpl implements EntryService {
    @Inject
    EntryRepository entryRepository;

    @Inject
    ForecastGroupService forecastGroupService;

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public Entry createEntry(Entry entry) {
        Entry savedEntry = entryRepository.createEntry(entry);
        ForecastGroup forecastGroup = savedEntry.getForecastGroup();
        if (forecastGroup != null) {
            em.merge(forecastGroup);
            forecastGroup.getEntries().add(savedEntry);
            forecastGroupService.update(forecastGroup);
        }
        return savedEntry;
    }

    @Override
    public Entry findByUuid(UUID uuid) throws EntryNotFoundException {
        return entryRepository.findByUuid(uuid).orElseThrow(() ->
                new EntryNotFoundException(String.format("The entry with the id '%s' was not found.", uuid))
        );
    }

    @Override
    public List<Entry> findAll() {
        return entryRepository.findAll();
    }
}
