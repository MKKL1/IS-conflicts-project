package islab.project.conflictsserver;

import islab.project.conflictsserver.entity.Conflict;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConflictRepository extends CrudRepository<Conflict, Integer> {
}
