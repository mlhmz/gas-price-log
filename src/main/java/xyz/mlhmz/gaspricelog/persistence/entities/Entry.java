package xyz.mlhmz.gaspricelog.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entries are a point where a measurement of the gas counter succeeded.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private BigDecimal value;

    private LocalDate date;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "forecastGroup_uuid", nullable = false)
    ForecastGroup forecastGroup;
}
