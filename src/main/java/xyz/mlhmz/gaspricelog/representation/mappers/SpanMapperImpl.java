package xyz.mlhmz.gaspricelog.representation.mappers;

import jakarta.enterprise.context.ApplicationScoped;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;
import xyz.mlhmz.gaspricelog.representation.dtos.EntryReferenceDto;
import xyz.mlhmz.gaspricelog.representation.dtos.ForecastGroupReferenceDto;
import xyz.mlhmz.gaspricelog.representation.dtos.SpanDto;

@ApplicationScoped
public class SpanMapperImpl implements SpanMapper {
    @Override
    public SpanDto toDto(Span span) {
        return SpanDto.builder()
                .uuid(span.getUuid())
                .fromEntry(EntryReferenceDto.fromEntity(span.getFromEntry()))
                .toEntry(EntryReferenceDto.fromEntity(span.getToEntry()))
                .days(span.getDays())
                .difference(span.getDifference())
                .gasPerDay(span.getGasPerDay())
                .priceOfSpan(span.getPriceOfSpan())
                .pricePerMonthOnSpanBasis(span.getPricePerMonthOnSpanBasis())
                .pricePerDay(span.getPricePerDay())
                .forecastGroupReferenceDto(ForecastGroupReferenceDto.ofEntity(span.getForecastGroup()))
                .build();
    }
}
