package islab.project.conflictsserver.services;

import islab.project.conflictsserver.ConflictRepository;
import islab.project.conflictsserver.entity.Conflict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ConflictsService {
    private final ConflictRepository repository;

    public ConflictsService(ConflictRepository repository) {
        this.repository = repository;
    }

    public List<Conflict> all() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
