package islab.project.conflictsserver;

import islab.project.conflictsserver.data.ConflictPOJO;
import islab.project.conflictsserver.services.DataImportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DataImportServiceTests {
    @Autowired
    private DataImportService dataImportService;
    @Test
    public void test_valid_excel_file() throws IOException {
        //TODO testing on resource, move resource somewhere else
        ClassPathResource resource = new ClassPathResource("MainConflictTable.xls");
        InputStream inputStream = resource.getInputStream();
        List<ConflictPOJO> result = dataImportService.importConflictData(inputStream);
        assertNotNull(result);
    }
}
