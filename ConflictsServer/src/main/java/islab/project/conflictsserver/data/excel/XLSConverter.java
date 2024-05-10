package islab.project.conflictsserver.data.excel;

import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class XLSConverter {

    public static <T> List<T> convert(InputStream inputStream, Function<Row, T> rowFactory) {
        List<T> rowList = new ArrayList<>();
        try(ReadableWorkbook readableWorkbook = new ReadableWorkbook(inputStream)) {
            Sheet sheet = readableWorkbook.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream()) {
                rows.forEach(r -> rowList.add(rowFactory.apply(r)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rowList;
    }
}
