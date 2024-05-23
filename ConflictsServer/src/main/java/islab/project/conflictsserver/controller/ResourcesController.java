package islab.project.conflictsserver.controller;

import islab.project.conflictsserver.entity.ResourcesEntity;
import islab.project.conflictsserver.services.ResourcesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResourcesController {
    private final ResourcesService resourcesService;

    public ResourcesController(ResourcesService resourcesService) {
        this.resourcesService = resourcesService;
    }

    @GetMapping("/resources")
    public List<ResourcesEntity> findAll() {
        return resourcesService.findAll();
    }
}
