package xyz.mlhmz.gaspricelog.representation.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record EntryDto(UUID uuid, BigDecimal value, LocalDate date, LocalDateTime createdAt, UUID forecastGroupUuid) {
}
