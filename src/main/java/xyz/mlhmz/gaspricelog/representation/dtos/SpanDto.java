package xyz.mlhmz.gaspricelog.representation.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record SpanDto(
        UUID uuid,
        EntryReferenceDto fromEntry,
        EntryReferenceDto toEntry,
        Long days,
        BigDecimal difference,
        BigDecimal gasPerDay,
        BigDecimal priceOfSpan,
        BigDecimal pricePerMonthOnSpanBasis,
        BigDecimal pricePerDay,
        ForecastGroupReferenceDto forecastGroupReferenceDto
) {
}
