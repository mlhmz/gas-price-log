package xyz.mlhmz.gaspricelog.services;

import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;

import java.util.List;

public interface SpanService {
    List<Span> calculateSpanFromEntries(List<Entry> entries);

    void deleteSpan(Span span);
}
