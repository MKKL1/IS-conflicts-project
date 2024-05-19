package islab.project.conflictsserver.data.excel;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class CSVConverter {

    public static <T> List<T> convert(InputStream inputStream, Function<String[], T> rowFactory, boolean skipHeader) throws IOException{
        List<T> rowList = new ArrayList<>();
        try(CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))){
            List<String[]> allRows = csvReader.readAll();
            Stream<String[]> rowStream = allRows.stream();

            if(skipHeader) {
                rowStream = rowStream.skip(1);
            }

            rowStream.forEach(row -> rowList.add(rowFactory.apply(row)));
        } catch (CsvException e){
            throw new IOException("Error reading CSV data", e);
        }
        return rowList;
    }
}
