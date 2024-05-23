package islab.project.conflictsserver.services;

import islab.project.conflictsserver.conflict.ConflictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Tutaj dajemy eksport na xml i json jakis rzeczy
@Service
public class DataExportService {

    @Autowired
    private ConflictRepository conflictRepository;

}
