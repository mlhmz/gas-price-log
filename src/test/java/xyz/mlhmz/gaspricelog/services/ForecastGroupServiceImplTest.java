package xyz.mlhmz.gaspricelog.services;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import xyz.mlhmz.gaspricelog.PostgresDBTestResource;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@QuarkusTestResource(PostgresDBTestResource.class)
class ForecastGroupServiceImplTest {
    @Inject
    ForecastGroupServiceImpl forecastGroupService;

    @AfterEach
    void tearDown() {
        PostgresDBTestResource.teardown();
    }

    @Test
    void create() {
        ForecastGroup group = createForecastGroup(12, 12);

        ForecastGroup forecastGroup = forecastGroupService.create(group);

        assertThat(forecastGroup.getUuid()).isNotNull();
        assertThat(forecastGroup.getGasPricePerKwh()).isEqualTo(12);
        assertThat(forecastGroup.getKwhFactorPerQubicmeter()).isEqualTo(12);
    }

    @Test
    @Transactional
    void findAll() {
        ForecastGroup firstForecastGroup = createForecastGroup(10, 10);
        ForecastGroup secondForecastGroup = createForecastGroup(13, 14);

        forecastGroupService.create(firstForecastGroup);
        forecastGroupService.create(secondForecastGroup);
        List<ForecastGroup> groups = forecastGroupService.findAll();

        assertThat(groups).hasSize(2);

        ForecastGroup firstSavedGroup = groups.get(0);
        assertThat(firstSavedGroup.getUuid()).isNotNull();
        assertThat(firstSavedGroup.getGasPricePerKwh()).isEqualTo(10);
        assertThat(firstSavedGroup.getKwhFactorPerQubicmeter()).isEqualTo(10);

        ForecastGroup secondSavedGroup = groups.get(1);
        assertThat(secondSavedGroup.getUuid()).isNotNull();
        assertThat(secondSavedGroup.getGasPricePerKwh()).isEqualTo(13);
        assertThat(secondSavedGroup.getKwhFactorPerQubicmeter()).isEqualTo(14);
    }

    private ForecastGroup createForecastGroup(int gasPricePerKwh, int kwhFactorPerQubicmeter) {
        return ForecastGroup.builder()
                .gasPricePerKwh(BigDecimal.valueOf(gasPricePerKwh))
                .kwhFactorPerQubicmeter(BigDecimal.valueOf(kwhFactorPerQubicmeter))
                .build();
    }
}