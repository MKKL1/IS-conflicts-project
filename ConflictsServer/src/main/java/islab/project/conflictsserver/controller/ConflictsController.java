package islab.project.conflictsserver.controller;

import islab.project.conflictsserver.ConflictRepository;
import islab.project.conflictsserver.entity.Conflict;
import islab.project.conflictsserver.services.ConflictsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConflictsController {

    private final ConflictsService conflictsService;

    public ConflictsController(ConflictsService conflictsService) {
        this.conflictsService = conflictsService;
    }

    @GetMapping("/conflicts")
    public List<Conflict> all() {
        return conflictsService.all();
    }
}
