package xyz.mlhmz.gaspricelog.representation.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record ForecastGroupDto(
        UUID uuid,
        String groupName,
        BigDecimal gasPricePerKwh,
        BigDecimal kwhFactorPerQubicmeter,
        List<EntryReferenceDto> entries,
        List<SpanReferenceDto> spans
) {
}
