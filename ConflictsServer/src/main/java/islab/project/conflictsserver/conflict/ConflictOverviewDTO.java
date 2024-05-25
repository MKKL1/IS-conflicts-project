package islab.project.conflictsserver.conflict;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ConflictOverviewDTO(Integer id,
                                  String location,
                                  @JsonFormat(pattern="yyyy-MM-dd") LocalDate start,
                                  @JsonFormat(pattern="yyyy-MM-dd") LocalDate end) {
}
