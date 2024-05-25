package islab.project.conflictsserver.conflict;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/conflicts")
public class ConflictsController {

    private final ConflictsService conflictsService;

    public ConflictsController(ConflictsService conflictsService) {
        this.conflictsService = conflictsService;
    }

    @GetMapping()
    public List<Conflict> all() {
        return conflictsService.all();
    }

    @GetMapping("/{id}")
    public ConflictDTO find(@PathVariable() Integer id) {
        return conflictsService.find(id);
    }
}
