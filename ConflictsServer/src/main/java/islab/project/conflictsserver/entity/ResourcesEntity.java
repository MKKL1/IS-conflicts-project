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
@Table(name = "resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourcesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;
    private Double price;
    private String region;
    private Integer year;

}
