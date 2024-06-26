package islab.project.conflictsserver.data;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class XLSXConverter {
    public static <T> List<T> convert(InputStream inputStream, Function<Row, T> rowFactory, int skiplines) throws IOException {
        List<T> rowList = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip the specified number of lines
            for (int i = 0; i < skiplines; i++) {
                if (rowIterator.hasNext()) {
                    rowIterator.next();
                } else {
                    break;
                }
            }



            while (rowIterator.hasNext()) {
                rowList.add(rowFactory.apply(rowIterator.next()));
            }
        }
        return rowList;
    }

    public static void convert(InputStream inputStream, Consumer<Row> consumer, int skiplines) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.iterator();

            for (int i = 0; i < skiplines; i++) {
                if (rowIterator.hasNext()) {
                    rowIterator.next();
                } else {
                    break;
                }
            }

            while (rowIterator.hasNext()) {
                consumer.accept(rowIterator.next());
            }
        }
    }
}
