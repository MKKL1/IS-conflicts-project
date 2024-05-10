package islab.project.conflictsserver.services;

import islab.project.conflictsserver.data.ConflictIntensity;
import islab.project.conflictsserver.data.ConflictRowData;
import islab.project.conflictsserver.data.ConflictType;
import islab.project.conflictsserver.data.excel.XLSConverter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Service
public class DataImportService {

    public List<ConflictRowData> importConflictData(InputStream inputStream) throws IOException {
        return filterConflictData(XLSConverter.convert(inputStream, row -> {

            Cell endTimeCell = row.getCell(17);
            return ConflictRowData.builder()
                    .docId((int) row.getCell(0).getNumericCellValue())
                    .location(row.getCell(1).getStringCellValue())
                    .sideA(row.getCell(2).getStringCellValue())
                    .sideB(row.getCell(4).getStringCellValue())
                    .year((int) row.getCell(8).getNumericCellValue())
                    .intensity(ConflictIntensity.getById((int) row.getCell(9).getNumericCellValue()))
                    .type(ConflictType.getById((int) row.getCell(11).getNumericCellValue()))
                    .startTime(row.getCell(12).getLocalDateTimeCellValue().toLocalDate())
                    .endTime(endTimeCell != null && endTimeCell.getCellType() != CellType.BLANK ? endTimeCell.getLocalDateTimeCellValue().toLocalDate() : null)
                    .build();
        }, true));
    }

    //Concatenate rows with same id
    private List<ConflictRowData> filterConflictData(Collection<ConflictRowData> conflictData) {
        List<ConflictRowData> filteredData = new ArrayList<>();
        conflictData.stream()
                .collect(Collectors.groupingBy(ConflictRowData::getDocId))
                .forEach((integer, groupedData) ->
                    filteredData.add(groupedData.stream()
                                    .max(Comparator.comparingInt(ConflictRowData::getYear))
                                    .orElseThrow()));
        return filteredData;
    }
}
