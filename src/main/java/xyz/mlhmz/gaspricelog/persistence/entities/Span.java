package xyz.mlhmz.gaspricelog.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Span {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @ManyToOne
    Entry fromEntry;

    @ManyToOne
    Entry toEntry;

    private Integer days;

    private Double difference;

    private Double gasPerDay;

    private Double priceOfSpan;

    private Double pricePerMonthOnSpanBasis;

    private Double pricePerDay;

    @ManyToOne
    @JoinColumn(name = "forecastGroup_uuid", nullable = false)
    ForecastGroup forecastGroup;
}
