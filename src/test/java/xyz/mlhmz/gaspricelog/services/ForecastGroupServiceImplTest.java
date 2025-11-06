package xyz.mlhmz.gaspricelog.services;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import xyz.mlhmz.gaspricelog.SQLiteTestResource;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@QuarkusTestResource(SQLiteTestResource.class)
class ForecastGroupServiceImplTest {
    @Inject
    ForecastGroupServiceImpl forecastGroupService;

    @Inject
    EntityManager entityManager;

    @AfterEach
    void tearDown() {
        SQLiteTestResource.teardown(entityManager);
    }

    @Test
    void create() {
        ForecastGroup group = createForecastGroup(new BigDecimal("12.00"), new BigDecimal("12.00"));

        ForecastGroup forecastGroup = forecastGroupService.create(group);

        assertThat(forecastGroup.getUuid()).isNotNull();
        assertThat(forecastGroup.getGasPricePerKwh()).isEqualTo(new BigDecimal("12.00"));
        assertThat(forecastGroup.getKwhFactorPerQubicmeter()).isEqualTo(new BigDecimal("12.00"));
    }

    @Test
    @Transactional
    void findAll() {
        ForecastGroup firstForecastGroup = createForecastGroup(new BigDecimal("10.00"), new BigDecimal("10.00"));
        ForecastGroup secondForecastGroup = createForecastGroup(new BigDecimal("13.00"), new BigDecimal("14.00"));

        forecastGroupService.create(firstForecastGroup);
        forecastGroupService.create(secondForecastGroup);
        List<ForecastGroup> groups = forecastGroupService.findAll();

        assertThat(groups).hasSize(2);

        ForecastGroup firstSavedGroup = groups.get(0);
        assertThat(firstSavedGroup.getUuid()).isNotNull();
        assertThat(firstSavedGroup.getGasPricePerKwh()).isEqualTo(new BigDecimal("10"));
        assertThat(firstSavedGroup.getKwhFactorPerQubicmeter()).isEqualTo(new BigDecimal("10"));

        ForecastGroup secondSavedGroup = groups.get(1);
        assertThat(secondSavedGroup.getUuid()).isNotNull();
        assertThat(secondSavedGroup.getGasPricePerKwh()).isEqualTo(new BigDecimal("13"));
        assertThat(secondSavedGroup.getKwhFactorPerQubicmeter()).isEqualTo(new BigDecimal("14"));
    }

    private ForecastGroup createForecastGroup(BigDecimal gasPricePerKwh, BigDecimal kwhFactorPerQubicmeter) {
        return ForecastGroup.builder()
                .gasPricePerKwh(gasPricePerKwh)
                .kwhFactorPerQubicmeter(kwhFactorPerQubicmeter)
                .build();
    }
}