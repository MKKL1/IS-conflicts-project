package islab.project.conflictsserver.conflict;

import islab.project.conflictsserver.conflict.converter.ConflictRowData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface ConflictRepository extends CrudRepository<Conflict, Integer> {
    default void saveConflictList(List<ConflictRowData> conflictRowDataList) {
        saveAll(conflictRowDataList.stream().map(ConflictRowData::toEntity).collect(Collectors.toList()));
    }
}
