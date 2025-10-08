package xyz.mlhmz.gaspricelog.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

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

    private String groupName;

    private double gasPricePerKwh;

    private double kwhFactorPerQubicmeter;

    @OneToMany(mappedBy = "forecastGroup")
    List<Entry> entries;

    @OneToMany(mappedBy = "forecastGroup")
    List<Span> spans;
}
