package islab.project.conflictsserver.conflict;

import org.springframework.stereotype.Component;

@Component
public class ConflictMapper {

    public ConflictDTO toDTO(Conflict conflict) {
        return ConflictDTO.builder()
                .id(conflict.getId())
                .location(conflict.getLocation())
                .sideA(conflict.getSideA())
                .sideB(conflict.getSideB())
                .conflictType(conflict.getType())
                .conflictIntensity(conflict.getIntensity())
                .startTime(conflict.getStartTime())
                .endTime(conflict.getEndTime())
                .build();
    }
}
