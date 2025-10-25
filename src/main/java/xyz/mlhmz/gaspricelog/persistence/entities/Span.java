package xyz.mlhmz.gaspricelog.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Spans are actual data points that represent calculations from one point to another.
 */
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

    /**
     * Days between the two data points
     */
    private Long days;

    /**
     * Difference between the two data points in kwh
     */
    private Double difference;

    /**
     * Calculation how much gas is used per day in kwh
     */
    private Double gasPerDay;

    /**
     * Price between the two data points
     */
    private Double priceOfSpan;

    /**
     * Price per month on the span basis
     */
    private Double pricePerMonthOnSpanBasis;

    /**
     *
     */
    private Double pricePerDay;

    @ManyToOne
    @JoinColumn(name = "forecastGroup_uuid", nullable = false)
    ForecastGroup forecastGroup;
}
