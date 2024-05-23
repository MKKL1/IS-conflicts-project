package islab.project.conflictsserver;

import islab.project.conflictsserver.data.XLSXConverter;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class XLSXConverterTest {

    @Test
    public void test_non_empty_xlsx_conversion() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[]{1, 2, 3, 4}); // Mocked data for non-empty XLSX
        Function<Row, String> rowFactory = row -> "Converted";
        List<String> result = XLSXConverter.convert(inputStream, rowFactory, 0);
        assertFalse(result.isEmpty());
        assertEquals("Converted", result.get(0));
    }
}
