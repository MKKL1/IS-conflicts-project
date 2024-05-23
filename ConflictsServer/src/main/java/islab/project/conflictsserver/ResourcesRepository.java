package islab.project.conflictsserver;

import islab.project.conflictsserver.entity.ResourcesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResourcesRepository extends CrudRepository<ResourcesEntity, Integer> {
    default void saveResourcesList(List<ResourcesEntity> resourcesList) {
        saveAll(resourcesList);
    }
}
