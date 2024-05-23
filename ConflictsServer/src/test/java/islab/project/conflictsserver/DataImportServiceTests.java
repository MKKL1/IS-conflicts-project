package islab.project.conflictsserver;

import islab.project.conflictsserver.conflict.converter.ConflictRowData;
import islab.project.conflictsserver.services.DataImportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DataImportServiceTests {
    private static final int CONFLICT_COUNT = 240;
    @Autowired
    private DataImportService dataImportService;
    @Test
    public void test_valid_excel_file() throws IOException {
        //TODO testing on resource, move resource somewhere else
        ClassPathResource resource = new ClassPathResource("MainConflictTable.xls");
        InputStream inputStream = resource.getInputStream();
        List<ConflictRowData> result = dataImportService.importConflictData(inputStream);
        assertNotNull(result);
        Assertions.assertEquals(CONFLICT_COUNT, result.size());
    }

    public void test_valid_xlsx_file() throws IOException {
        ClassPathResource resource = new ClassPathResource("CMO-Historical-Data-Monthly.xlsx");
        InputStream inputStream = resource.getInputStream();
        //TODO
    }
}
