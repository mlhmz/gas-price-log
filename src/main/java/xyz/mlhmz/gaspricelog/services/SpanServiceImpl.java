package xyz.mlhmz.gaspricelog.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import xyz.mlhmz.gaspricelog.exceptions.EntriesNotFromSameForecastgroupException;
import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;
import xyz.mlhmz.gaspricelog.persistence.repositories.SpanRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class SpanServiceImpl implements SpanService {
    public static final int MONTH_IN_DAYS = 30;
    @Inject
    SpanRepository spanRepository;

    @Override
    public List<Span> calculateSpanFromEntries(List<Entry> entries) {
        entries.sort(Comparator.comparing(Entry::getDate));

        Set<UUID> forecastGroupUuidSet = entries.stream().map(Entry::getUuid).collect(Collectors.toSet());

        if (forecastGroupUuidSet.size() > 1) {
            throw new EntriesNotFromSameForecastgroupException("The given entries are not all from the same forecast group.");
        }

        List<Span> spanList = new ArrayList<>();
        Entry lastEntry = null;
        for (Entry entry : entries) {
            if (lastEntry == null) {
                lastEntry = entry;
                continue;
            }
            ForecastGroup forecastGroup = entry.getForecastGroup();
            BigDecimal gasPricePerKwh = forecastGroup.getGasPricePerKwh();
            BigDecimal kwhFactorPerQubicmeter = forecastGroup.getKwhFactorPerQubicmeter();
            BigDecimal previousKwh = toKwh(lastEntry.getValue(), kwhFactorPerQubicmeter);
            BigDecimal kwh = toKwh(entry.getValue(), kwhFactorPerQubicmeter);
            BigDecimal differenceInKwh = calculateDifferenceInKwh(kwh, previousKwh);
            BigDecimal priceOfDifference = calculatePriceOfDifference(differenceInKwh, gasPricePerKwh);
            long daysBetween = ChronoUnit.DAYS.between(lastEntry.getDate(), entry.getDate());
            BigDecimal pricePerDay = calculatePricePerDay(priceOfDifference, daysBetween);
            Span span = Span.builder()
                    .fromEntry(lastEntry)
                    .toEntry(entry)
                    .days(daysBetween)
                    .difference(differenceInKwh.setScale(2, RoundingMode.CEILING))
                    .gasPerDay(differenceInKwh.divide(BigDecimal.valueOf(daysBetween), 2, RoundingMode.CEILING))
                    .pricePerMonthOnSpanBasis(getPricePerMonthOnSpanBasis(pricePerDay).setScale(2, RoundingMode.CEILING))
                    .pricePerDay(pricePerDay)
                    .build();
            Span savedSpan = this.spanRepository.create(span);
            spanList.add(savedSpan);
            lastEntry = entry;
        }
        return spanList;
    }

    private BigDecimal getPricePerMonthOnSpanBasis(BigDecimal pricePerDay) {
        return pricePerDay.multiply(BigDecimal.valueOf(MONTH_IN_DAYS));
    }

    private BigDecimal calculatePricePerDay(BigDecimal priceOfDifference, long daysBetween) {
        return priceOfDifference.divide(BigDecimal.valueOf(daysBetween), 2, RoundingMode.CEILING);
    }

    private BigDecimal calculatePriceOfDifference(BigDecimal differenceInKwh, BigDecimal gasPricePerKwh) {
        return differenceInKwh.multiply(gasPricePerKwh);
    }

    private BigDecimal calculateDifferenceInKwh(BigDecimal kwh, BigDecimal previousKwh) {
        return kwh.subtract(previousKwh);
    }

    @Override
    public void deleteSpan(Span span) {
        this.spanRepository.deleteSpan(span);
    }

    private BigDecimal toKwh(final BigDecimal qubicmeter, final BigDecimal kwhFactorPerQubicmeter) {
        return qubicmeter.multiply(kwhFactorPerQubicmeter);
    }
}
