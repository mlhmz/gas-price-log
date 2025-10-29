package xyz.mlhmz.gaspricelog.persistence.repositories;

import jakarta.data.repository.Find;
import jakarta.data.repository.Insert;
import jakarta.data.repository.Repository;
import jakarta.transaction.Transactional;
import xyz.mlhmz.gaspricelog.persistence.entities.Entry;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Repository
public interface EntryRepository {
    @Insert
    Entry createEntry(Entry entry);

    @Find
    Optional<Entry> findByUuid(UUID uuid);

    @Find
    List<Entry> findAll();
}
