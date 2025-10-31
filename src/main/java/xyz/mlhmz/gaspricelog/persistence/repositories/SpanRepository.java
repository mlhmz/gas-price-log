package xyz.mlhmz.gaspricelog.persistence.repositories;

import jakarta.data.repository.Delete;
import jakarta.data.repository.Find;
import jakarta.data.repository.Insert;
import jakarta.data.repository.Repository;
import jakarta.transaction.Transactional;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Repository
public interface SpanRepository {
    @Insert
    Span create(Span span);

    @Find
    List<Span> findAll();

    @Find
    Optional<Span> findByUuid(UUID uuid);

    @Delete
    void deleteSpan(Span span);

    @Delete
    void deleteSpans(List<Span> spans);
}
