package xyz.mlhmz.gaspricelog.representation.dtos;

import xyz.mlhmz.gaspricelog.persistence.entities.Entry;

import java.util.UUID;

public record EntryReferenceDto(UUID uuid) {
    public static EntryReferenceDto fromEntity(Entry entry) {
        if (entry != null) {
            return new EntryReferenceDto(entry.getUuid());
        }
        return null;
    }
}
