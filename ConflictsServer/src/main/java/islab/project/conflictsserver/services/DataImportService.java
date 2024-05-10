package islab.project.conflictsserver.services;

import islab.project.conflictsserver.data.ConflictIntensity;
import islab.project.conflictsserver.data.ConflictPOJO;
import islab.project.conflictsserver.data.ConflictType;
import islab.project.conflictsserver.data.excel.XLSConverter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DataImportService {

    public List<ConflictPOJO> importConflictData(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            return importConflictData(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ConflictPOJO> importConflictData(InputStream inputStream) {
        return XLSConverter.convert(inputStream, row ->
                ConflictPOJO.builder()
                        .docId(row.getCellAsNumber(0).orElseThrow(() -> new RuntimeException("index 0 was empty")).intValue())
                        .location(row.getCellText(1))
                        .sideA(row.getCellText(2))
                        .sideB(row.getCellText(4))
                        .intensity(ConflictIntensity.getById(row.getCellAsNumber(9).orElseThrow(() -> new RuntimeException("index 9 was empty")).intValue()))
                        .type(ConflictType.getById(row.getCellAsNumber(11).orElseThrow(() -> new RuntimeException("index 11 was empty")).intValue()))
                        .startTime(row.getCellAsDate(12).orElseThrow(() -> new RuntimeException("index 12 was empty")).toLocalDate())
                        .endTime(row.getCellAsDate(17).map(LocalDateTime::toLocalDate).orElse(null))
                        .build()
        );
    }
}
