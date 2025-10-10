package xyz.mlhmz.gaspricelog.representation.dtos;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ForecastGroupDto(
        UUID uuid,
        String groupName,
        double gasPricePerKwh,
        double kwhFactorPerQubicmeter,
        List<EntryReferenceDto> entries,
        List<SpanReferenceDto> spans
) {
}
