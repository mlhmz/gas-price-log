package xyz.mlhmz.gaspricelog.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForecastGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    /**
     * Display name of the forecast group
     */
    private String groupName;

    /**
     * Gas price per kwh, varies from gas provider to gas provider
     */
    private BigDecimal gasPricePerKwh;

    /**
     * There is a calculatable kwh factor per qubicmeter that varies from gas heater to gas heater.
     */
    private BigDecimal kwhFactorPerQubicmeter;

    @OneToMany(mappedBy = "forecastGroup", fetch = FetchType.EAGER)
    List<Entry> entries;

    @OneToMany(mappedBy = "forecastGroup", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    List<Span> spans;
}
