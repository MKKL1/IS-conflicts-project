package islab.project.conflictsserver.conflict;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Conflict> all() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ConflictDTO find(int id) {
        return conflictMapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ConflictNotFoundException("Conflict by id: " + id + " not found")));
    }
}
