package islab.project.conflictsserver.conflict;

import com.fasterxml.jackson.annotation.JsonFormat;
import islab.project.conflictsserver.conflict.converter.ConflictIntensity;
import islab.project.conflictsserver.conflict.converter.ConflictType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ConflictDTO(Integer id,
                          String location,
                          String sideA,
                          String sideB,
                          ConflictType conflictType,
                          ConflictIntensity conflictIntensity,
                          @JsonFormat(pattern="yyyy-MM-dd") LocalDate startTime,
                          @JsonFormat(pattern="yyyy-MM-dd") LocalDate endTime) {
}
