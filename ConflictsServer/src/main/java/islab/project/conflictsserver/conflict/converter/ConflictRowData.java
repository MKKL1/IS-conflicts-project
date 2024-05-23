package islab.project.conflictsserver.conflict.converter;

import islab.project.conflictsserver.conflict.Conflict;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Optional;
@Builder
@AllArgsConstructor
public class ConflictRowData {
    @NotNull
    @Getter
    private final int docId;

    @NotNull
    @Getter
    private final String location;

    @NotNull
    @Getter
    private final String sideA;

    @NotNull
    @Getter
    private final String sideB;

    @NotNull
    @Getter
    private final ConflictType type;

    @NotNull
    @Getter
    private final ConflictIntensity intensity;

    @NotNull
    @Getter
    private final int year;

    @NotNull
    @Getter
    private final LocalDate startTime;

    @Nullable
    private final LocalDate endTime;

    public Optional<LocalDate> getEndTime() {
        return Optional.ofNullable(endTime);
    }

    public Conflict toEntity() {
        return Conflict.builder()
                .id(docId)
                .location(location)
                .sideA(sideA)
                .sideB(sideB)
                .type(type)
                .intensity(intensity)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
