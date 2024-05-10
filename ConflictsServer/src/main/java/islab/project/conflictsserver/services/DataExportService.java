package islab.project.conflictsserver.services;

import islab.project.conflictsserver.ConflictRepository;
import islab.project.conflictsserver.data.ConflictRowData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataExportService {

    @Autowired
    private ConflictRepository conflictRepository;
    public void saveConflictList(List<ConflictRowData> conflictRowDataList) {
        conflictRepository.saveAll(conflictRowDataList.stream().map(ConflictRowData::toEntity).collect(Collectors.toList()));
    }
}
