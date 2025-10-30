package xyz.mlhmz.gaspricelog.services;

import xyz.mlhmz.gaspricelog.exceptions.SpanNotFoundException;
import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;

import java.util.List;
import java.util.UUID;

public interface SpanService {
    List<Span> calculateSpanFromEntries(List<Entry> entries);

    List<Span> findAllSpans();

    Span findByUuid(UUID uuid) throws SpanNotFoundException;

    void deleteSpan(Span span);
}
