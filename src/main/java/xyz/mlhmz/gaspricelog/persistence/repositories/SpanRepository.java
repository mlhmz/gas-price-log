package xyz.mlhmz.gaspricelog.persistence.repositories;

import jakarta.data.repository.Delete;
import jakarta.data.repository.Insert;
import jakarta.data.repository.Repository;
import jakarta.transaction.Transactional;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;

@Transactional
@Repository
public interface SpanRepository {
    @Insert
    Span create(Span span);

    @Delete
    void deleteSpan(Span span);
}
