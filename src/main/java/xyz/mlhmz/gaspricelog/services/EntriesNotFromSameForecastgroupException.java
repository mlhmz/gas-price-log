package xyz.mlhmz.gaspricelog.services;

public class EntriesNotFromSameForecastgroupException extends RuntimeException {
    public EntriesNotFromSameForecastgroupException(String message) {
        super(message);
    }
}
