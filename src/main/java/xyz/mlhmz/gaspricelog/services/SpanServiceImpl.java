package xyz.mlhmz.gaspricelog.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import xyz.mlhmz.gaspricelog.persistence.entities.Entry;
import xyz.mlhmz.gaspricelog.persistence.entities.ForecastGroup;
import xyz.mlhmz.gaspricelog.persistence.entities.Span;
import xyz.mlhmz.gaspricelog.persistence.repositories.SpanRepository;

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
            double gasPricePerKwh = forecastGroup.getGasPricePerKwh();
            double kwhFactorPerQubicmeter = forecastGroup.getKwhFactorPerQubicmeter();
            double previousKwh = toKwh(lastEntry.getValue(), kwhFactorPerQubicmeter);
            double kwh = toKwh(entry.getValue(), kwhFactorPerQubicmeter);
            double differenceInKwh = calculateDifferenceInKwh(kwh, previousKwh);
            double priceOfDifference = calculatePriceOfDifference(differenceInKwh, gasPricePerKwh);
            long daysBetween = ChronoUnit.DAYS.between(lastEntry.getDate(), entry.getDate());
            double pricePerDay = calculatePricePerDay(priceOfDifference, daysBetween);
            Span span = Span.builder()
                    .fromEntry(lastEntry)
                    .toEntry(entry)
                    .days(daysBetween)
                    .difference(differenceInKwh)
                    .gasPerDay(differenceInKwh / daysBetween)
                    .pricePerMonthOnSpanBasis(getPricePerMonthOnSpanBasis(pricePerDay))
                    .pricePerDay(pricePerDay)
                    .build();
            Span savedSpan = this.spanRepository.create(span);
            spanList.add(savedSpan);
            lastEntry = entry;
        }
        return spanList;
    }

    private static double getPricePerMonthOnSpanBasis(double pricePerDay) {
        return pricePerDay * MONTH_IN_DAYS;
    }

    private double calculatePricePerDay(double priceOfDifference, long daysBetween) {
        return priceOfDifference / daysBetween;
    }

    private double calculatePriceOfDifference(double differenceInKwh, double gasPricePerKwh) {
        return differenceInKwh * gasPricePerKwh;
    }

    private double calculateDifferenceInKwh(double kwh, double previousKwh) {
        return kwh - previousKwh;
    }

    @Override
    public void deleteSpan(Span span) {
        this.spanRepository.deleteSpan(span);
    }

    private double toKwh(final double qubicmeter, final double kwhFactorPerQubicmeter) {
        return qubicmeter * kwhFactorPerQubicmeter;
    }
}
