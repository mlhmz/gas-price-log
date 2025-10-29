package xyz.mlhmz.gaspricelog.services;

import xyz.mlhmz.gaspricelog.exceptions.EntryNotFoundException;
import xyz.mlhmz.gaspricelog.persistence.entities.Entry;

import java.util.List;
import java.util.UUID;

public interface EntryService {
    Entry createEntry(Entry entry);

    Entry findByUuid(UUID uuid) throws EntryNotFoundException;

    List<Entry> findAll();
}
