package islab.project.conflictsserver.services;

import islab.project.conflictsserver.conflict.converter.ConflictIntensity;
import islab.project.conflictsserver.conflict.converter.ConflictRowData;
import islab.project.conflictsserver.conflict.converter.ConflictType;
import islab.project.conflictsserver.data.CSVConverter;
import islab.project.conflictsserver.data.XLSConverter;
import islab.project.conflictsserver.commodities.Commodity;
import islab.project.conflictsserver.data.XLSXConverter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
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

    //Importing resources data from CSV
    public List<Commodity> importCommoditiesData(InputStream inputStream, String resourceType) throws IOException {
        return CSVConverter.convert(inputStream, row -> {
            Integer year = Integer.parseInt(row[2]);
            return Commodity.builder()
                    .region(row[0])
                    .date(LocalDate.of(year, 1, 1)) //Mapping to first day of year
                    .price(Double.parseDouble(row[3]))
                    .type(resourceType)
                    .build();
        }, true);
    }

    //Importing metal resources data from CSV
    public List<Commodity> importMetalsData(InputStream inputStream) throws IOException {
        List<Commodity> resources = new ArrayList<>();
        CSVConverter.convert(inputStream, row -> {
            String region = row[0];
            Integer year = Integer.parseInt(row[2]);

            String[] metals = {"Iron ore", "Bauxite", "Tin", "Zinc", "Steel", "Manganese", "Aluminum", "Chromium", "Copper", "Lead", "Nickel"};
            for (int i = 3; i < row.length; i++) {
                if (!row[i].isEmpty()) {
                    resources.add(Commodity.builder()
                            .region(region)
                            .date(LocalDate.of(year, 1, 1))
                            .price(Double.parseDouble(row[i]))
                            .type(metals[i - 3])
                            .build());
                }
            }
            return null;
        }, true);
        return resources;
    }

    public List<Commodity> importCMOHistoricalData(InputStream inputStream) throws IOException {
        return XLSXConverter.convert(inputStream, row -> {
            System.out.println(row.toString());
            return null;
        }, 6);
    }
}
