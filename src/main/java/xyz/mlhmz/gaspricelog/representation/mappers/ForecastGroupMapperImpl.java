package xyz.mlhmz.gaspricelog.representation.mappers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;
import xyz.mlhmz.gaspricelog.representation.dtos.*;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ForecastGroupMapperImpl implements ForecastGroupMapper {
    @Inject
    SpanMapper spanMapper;

    @Inject
    EntryMapper entryMapper;

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

    @Override
    public ForecastGroup fromDto(ForecastGroupDto dto) {
        return ForecastGroup.builder()
                .uuid(dto.uuid())
                .groupName(dto.groupName())
                .gasPricePerKwh(dto.gasPricePerKwh())
                .kwhFactorPerQubicmeter(dto.kwhFactorPerQubicmeter())
                .build();
    }

    @Override
    public ForecastGroup fromDto(ForecastGroupDto forecastGroupDto, List<Entry> entries, List<Span> spans) {
        return ForecastGroup.builder()
                .uuid(forecastGroupDto.uuid())
                .groupName(forecastGroupDto.groupName())
                .gasPricePerKwh(forecastGroupDto.gasPricePerKwh())
                .kwhFactorPerQubicmeter(forecastGroupDto.kwhFactorPerQubicmeter())
                .entries(entries)
                .spans(spans)
                .build();
    }

    private List<SpanDto> mapSpans(ForecastGroup forecastGroup) {
        List<Span> spans = forecastGroup.getSpans();
        if (spans == null) {
            return Collections.emptyList();
        }
        return spans.stream()
                .map(spanMapper::toDto)
                .toList();
    }

    private List<EntryDto> mapEntries(ForecastGroup forecastGroup) {
        List<Entry> entries = forecastGroup.getEntries();
        if (entries == null) {
            return Collections.emptyList();
        }
        return entries.stream()
                .map(entryMapper::toDto)
                .toList();
    }
}
