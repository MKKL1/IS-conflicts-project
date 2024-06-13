package islab.project.conflictsserver.data;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class CSVConverter {

    public static void convert(InputStream inputStream, Consumer<String[]> rowConsumer, boolean skipHeader) throws IOException{
        try(CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))){
            List<String[]> allRows = csvReader.readAll();
            Stream<String[]> rowStream = allRows.stream();

            if(skipHeader) {
                rowStream = rowStream.skip(1);
            }

            rowStream.forEach(rowConsumer);
        } catch (CsvException e){
            throw new IOException("Error reading CSV data", e);
        }
    }
}
