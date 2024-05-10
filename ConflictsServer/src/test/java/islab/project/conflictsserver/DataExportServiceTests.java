package islab.project.conflictsserver;

import islab.project.conflictsserver.data.ConflictRowData;
import islab.project.conflictsserver.services.DataExportService;
import islab.project.conflictsserver.services.DataImportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
public class DataExportServiceTests {

    @Autowired
    private DataImportService dataImportService;
    @Autowired
    private DataExportService dataExportService;

    @Test
    public void save_test() throws IOException {
        ClassPathResource resource = new ClassPathResource("MainConflictTable.xls");
        InputStream inputStream = resource.getInputStream();
        List<ConflictRowData> result = dataImportService.importConflictData(inputStream);
        dataExportService.saveConflictList(result);
    }
}
