package islab.project.conflictsserver.services;

import islab.project.conflictsserver.data.ConflictIntensity;
import islab.project.conflictsserver.data.ConflictRowData;
import islab.project.conflictsserver.data.ConflictType;
import islab.project.conflictsserver.data.converter.CSVConverter;
import islab.project.conflictsserver.data.converter.XLSConverter;
import islab.project.conflictsserver.entity.ResourcesEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public List<ResourcesEntity> importResourcesData(InputStream inputStream, String resourceType) throws IOException {
        return CSVConverter.convert(inputStream, row -> {
            Integer year = Integer.parseInt(row[2]);
            return ResourcesEntity.builder()
                    .region(row[0])
                    .year(year)
                    .price(Double.parseDouble(row[3]))
                    .type(resourceType)
                    .build();
        }, true);
    }


    //Importing metal resources data from CSV
    public List<ResourcesEntity> importMetalsData(InputStream inputStream) throws IOException {
        List<ResourcesEntity> resources = new ArrayList<>();
        CSVConverter.convert(inputStream, row -> {
            String region = row[0];
            Integer year = Integer.parseInt(row[2]);

            String[] metals = {"Iron ore", "Bauxite", "Tin", "Zinc", "Steel", "Manganese", "Aluminum", "Chromium", "Copper", "Lead", "Nickel"};
            for (int i = 3; i < row.length; i++) {
                if (!row[i].isEmpty()) {
                    resources.add(ResourcesEntity.builder()
                            .region(region)
                            .year(year)
                            .price(Double.parseDouble(row[i]))
                            .type(metals[i - 3])
                            .build());
                }
            }
            return null;
        }, true);
        return resources;
    }

}
