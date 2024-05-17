package islab.project.conflictsserver.services;

import islab.project.conflictsserver.ConflictRepository;
import islab.project.conflictsserver.data.ConflictRowData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Tutaj dajemy eksport na xml i json jakis rzeczy
@Service
public class DataExportService {

    @Autowired
    private ConflictRepository conflictRepository;

}
