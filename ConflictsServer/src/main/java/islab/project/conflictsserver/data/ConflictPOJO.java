package islab.project.conflictsserver.data;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
@Builder
@AllArgsConstructor
public class ConflictPOJO {
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
    private final LocalDate startTime;

    @Nullable
    private final LocalDate endTime;

    public Optional<LocalDate> getEndTime() {
        return Optional.ofNullable(endTime);
    }
}
