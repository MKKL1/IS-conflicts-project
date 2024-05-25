package islab.project.conflictsserver.conflict;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ConflictsService {
    private final ConflictRepository repository;
    private final ConflictMapper conflictMapper;

    public ConflictsService(ConflictRepository repository, ConflictMapper conflictMapper) {
        this.repository = repository;
        this.conflictMapper = conflictMapper;
    }

    public List<Conflict> all() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public ConflictDTO find(int id) {
        return conflictMapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ConflictNotFoundException("Conflict by id: " + id + " not found")));
    }
}
