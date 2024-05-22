package islab.project.conflictsserver.services;

import islab.project.conflictsserver.ResourcesRepository;
import islab.project.conflictsserver.entity.ResourcesEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ResourcesService {

    private final ResourcesRepository resourcesRepository;

    public ResourcesService(ResourcesRepository resourcesRepository) {
        this.resourcesRepository = resourcesRepository;
    }

    //save
    public void saveResourcesList(List<ResourcesEntity> resourcesList) {
        resourcesRepository.saveResourcesList(resourcesList);
    }

    //read
    public List<ResourcesEntity> findAll() {
        return StreamSupport.stream(resourcesRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
