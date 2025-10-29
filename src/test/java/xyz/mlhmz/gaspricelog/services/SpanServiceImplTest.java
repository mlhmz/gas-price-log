package xyz.mlhmz.gaspricelog.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;
import xyz.mlhmz.gaspricelog.persistence.repositories.SpanRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpanServiceImplTest {
    @Mock
    SpanRepository spanRepository;

    @Test
    void calculateSpanFromEntries() {
        SpanServiceImpl spanService = new SpanServiceImpl();
        spanService.spanRepository = spanRepository;

        when(spanRepository.create(any())).thenAnswer(answer -> answer.getArguments()[0]);

        UUID uuid = UUID.randomUUID();
        ForecastGroup forecastGroup = ForecastGroup.builder()
                .uuid(uuid)
                .gasPricePerKwh(new BigDecimal("0.1415"))
                .kwhFactorPerQubicmeter(new BigDecimal("11.512"))
                .build();

        Entry firstEntry = Entry.builder()
                .value(new BigDecimal("395.737"))
                .date(LocalDate.of(2025, 7, 22))
                .forecastGroup(forecastGroup)
                .build();

        Entry secondEntry = Entry.builder()
                .value(new BigDecimal("402.101"))
                .date(LocalDate.of(2025, 8, 24))
                .forecastGroup(forecastGroup)
                .build();

        Entry thirdEntry = Entry.builder()
                .value(new BigDecimal("410.492"))
                .date(LocalDate.of(2025, 9, 27))
                .forecastGroup(forecastGroup)
                .build();

        Entry fourthEntry = Entry.builder()
                .value(new BigDecimal("452.930"))
                .date(LocalDate.of(2025, 10, 25))
                .forecastGroup(forecastGroup)
                .build();

        List<Entry> entries = new ArrayList<>();
        entries.add(firstEntry);
        entries.add(secondEntry);
        entries.add(thirdEntry);
        entries.add(fourthEntry);
        forecastGroup.setEntries(entries);

        List<Span> spans = spanService.calculateSpanFromEntries(entries);

        assertThat(spans).hasSize(3);

        Span firstSpan = spans.get(0);
        assertThat(firstSpan.getDays()).isEqualTo(33);
        assertThat(firstSpan.getDifference()).isEqualTo(new BigDecimal("73.27"));
        assertThat(firstSpan.getGasPerDay()).isEqualTo(new BigDecimal("2.23"));
        assertThat(firstSpan.getPricePerMonthOnSpanBasis()).isEqualTo(new BigDecimal("9.60"));
        assertThat(firstSpan.getPricePerDay()).isEqualTo(new BigDecimal("0.32"));

        Span secondSpan = spans.get(1);
        assertThat(secondSpan.getDays()).isEqualTo(34);
        assertThat(secondSpan.getDifference()).isEqualTo(new BigDecimal("96.60"));
        assertThat(secondSpan.getGasPerDay()).isEqualTo(new BigDecimal("2.85"));
        assertThat(secondSpan.getPricePerMonthOnSpanBasis()).isEqualTo(new BigDecimal("12.30"));
        assertThat(secondSpan.getPricePerDay()).isEqualTo(new BigDecimal("0.41"));

        Span thirdSpan = spans.get(2);
        assertThat(thirdSpan.getDays()).isEqualTo(28);
        assertThat(thirdSpan.getDifference()).isEqualTo(new BigDecimal("488.55"));
        assertThat(thirdSpan.getGasPerDay()).isEqualTo(new BigDecimal("17.45"));
        assertThat(thirdSpan.getPricePerMonthOnSpanBasis()).isEqualTo(new BigDecimal("74.10"));
        assertThat(thirdSpan.getPricePerDay()).isEqualTo(new BigDecimal("2.47"));
    }
}