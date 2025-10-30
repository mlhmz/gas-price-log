package xyz.mlhmz.gaspricelog.representation.mappers;

import xyz.mlhmz.gaspricelog.persistence.entities.Span;
import xyz.mlhmz.gaspricelog.representation.dtos.SpanDto;

public interface SpanMapper {
    SpanDto toDto(Span span);
}
