package xyz.mlhmz.gaspricelog.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
@ToString
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
    private BigDecimal difference;

    /**
     * Calculation how much gas is used per day in kwh
     */
    private BigDecimal gasPerDay;

    /**
     * Price between the two data points
     */
    private BigDecimal priceOfSpan;

    /**
     * Price per month on the span basis
     */
    private BigDecimal pricePerMonthOnSpanBasis;

    /**
     *
     */
    private BigDecimal pricePerDay;

    @ManyToOne
    @JoinColumn(name = "forecastGroup_uuid", nullable = false)
    ForecastGroup forecastGroup;
}
