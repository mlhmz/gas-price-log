package xyz.mlhmz.gaspricelog.representation.mappers;

import jakarta.enterprise.context.ApplicationScoped;
import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;
import xyz.mlhmz.gaspricelog.representation.dtos.EntryReferenceDto;
import xyz.mlhmz.gaspricelog.representation.dtos.ForecastGroupDto;
import xyz.mlhmz.gaspricelog.representation.dtos.SpanReferenceDto;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ForecastGroupMapperImpl implements ForecastGroupMapper {
    @Override
    public ForecastGroupDto toDto(ForecastGroup forecastGroup) {
        return ForecastGroupDto.builder()
                .uuid(forecastGroup.getUuid())
                .groupName(forecastGroup.getGroupName())
                .gasPricePerKwh(forecastGroup.getGasPricePerKwh())
                .kwhFactorPerQubicmeter(forecastGroup.getKwhFactorPerQubicmeter())
                .entries(mapEntries(forecastGroup))
                .spans(mapSpans(forecastGroup))
                .build();
    }

    private static List<SpanReferenceDto> mapSpans(ForecastGroup forecastGroup) {
        List<Span> spans = forecastGroup.getSpans();
        if (spans == null) {
            return Collections.emptyList();
        }
        return spans.stream()
                .map(span -> new SpanReferenceDto(span.getUuid()))
                .toList();
    }

    private static List<EntryReferenceDto> mapEntries(ForecastGroup forecastGroup) {
        List<Entry> entries = forecastGroup.getEntries();
        if (entries == null) {
            return Collections.emptyList();
        }
        return entries.stream()
                .map(entry -> new EntryReferenceDto(entry.getUuid()))
                .toList();
    }

    @Override
    public ForecastGroup fromDto(ForecastGroupDto dto) {
        return ForecastGroup.builder()
                .uuid(dto.uuid())
                .groupName(dto.groupName())
                .gasPricePerKwh(dto.gasPricePerKwh())
                .kwhFactorPerQubicmeter(dto.kwhFactorPerQubicmeter())
                .build();
    }
}
