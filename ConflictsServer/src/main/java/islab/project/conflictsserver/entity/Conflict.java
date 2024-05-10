package islab.project.conflictsserver.entity;

import islab.project.conflictsserver.data.ConflictIntensity;
import islab.project.conflictsserver.data.ConflictType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "conflict")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conflict {
    @Id
    private Integer id;
    private String location;
    private String sideA;
    private String sideB;
    private ConflictType type;
    private ConflictIntensity intensity;
    private LocalDate startTime;
    private LocalDate endTime;

}
