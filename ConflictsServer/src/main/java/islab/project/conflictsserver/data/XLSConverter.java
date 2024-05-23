package islab.project.conflictsserver.data;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class XLSConverter {

    public static <T> List<T> convert(InputStream inputStream, Function<Row, T> rowFactory, boolean skipHeader) throws IOException {
        List<T> rowList = new ArrayList<>();
        try(Workbook workbook = new HSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            //Taking iterator to skip one row
            Iterator<Row> rowIterator = sheet.iterator();
            if(skipHeader) rowIterator.next();

            while(rowIterator.hasNext()) {
                rowList.add(rowFactory.apply(rowIterator.next()));
            }

        }
        return rowList;
    }
}
