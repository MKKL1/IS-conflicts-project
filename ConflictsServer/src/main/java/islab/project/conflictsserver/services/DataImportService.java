package islab.project.conflictsserver.services;

import islab.project.conflictsserver.commodities.CommodityCategory;
import islab.project.conflictsserver.commodities.CommodityCategoryRepository;
import islab.project.conflictsserver.config.DataImportConfig;
import islab.project.conflictsserver.conflict.converter.ConflictIntensity;
import islab.project.conflictsserver.conflict.converter.ConflictRowData;
import islab.project.conflictsserver.conflict.converter.ConflictType;
import islab.project.conflictsserver.data.CSVConverter;
import islab.project.conflictsserver.data.XLSConverter;
import islab.project.conflictsserver.commodities.CommodityPrice;
import islab.project.conflictsserver.data.XLSXConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataImportService {

    private final Map<Integer, DataImportConfig.CommodityMapValue> commodityKeyMap;

    public DataImportService(Map<Integer, DataImportConfig.CommodityMapValue> commodityKeyMap) {
        this.commodityKeyMap = commodityKeyMap;
    }

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
//    public List<CommodityPrice> importCommoditiesData(InputStream inputStream, String resourceType) throws IOException {
//        return CSVConverter.convert(inputStream, row -> {
//            Integer year = Integer.parseInt(row[2]);
//            return CommodityPrice.builder()
//                    .region(row[0])
//                    .date(LocalDate.of(year, 1, 1)) //Mapping to first day of year
//                    .price(Double.parseDouble(row[3]))
//                    .type(resourceType)
//                    .build();
//        }, true);
//    }

    //Importing metal resources data from CSV
//    public List<CommodityPrice> importMetalsData(InputStream inputStream) throws IOException {
//        List<CommodityPrice> resources = new ArrayList<>();
//        CSVConverter.convert(inputStream, row -> {
//            String region = row[0];
//            Integer year = Integer.parseInt(row[2]);
//
//            String[] metals = {"Iron ore", "Bauxite", "Tin", "Zinc", "Steel", "Manganese", "Aluminum", "Chromium", "Copper", "Lead", "Nickel"};
//            for (int i = 3; i < row.length; i++) {
//                if (!row[i].isEmpty()) {
//                    resources.add(CommodityPrice.builder()
//                            .region(region)
//                            .date(LocalDate.of(year, 1, 1))
//                            .price(Double.parseDouble(row[i]))
//                            .type(metals[i - 3])
//                            .build());
//                }
//            }
//            return null;
//        }, true);
//        return resources;
//    }


    //import CMOHistorical resources Data from XLSX
    public Map<CommodityCategory, List<CommodityPrice>> importCMOHistoricalData(InputStream inputStream) throws IOException {

        Map<CommodityCategory, List<CommodityPrice>> categoryPriceMap = new HashMap<>();

        XLSXConverter.convert(inputStream, row -> {
            // Fixing date
            String dateStr = row.getCell(0).getStringCellValue();
            String[] parts = dateStr.split("M");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);

            LocalDate date = LocalDate.of(year, month, 1);

            commodityKeyMap.forEach((columnIndex, commodityMapValue) -> {
                Cell cell = row.getCell(columnIndex);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    double price = cell.getNumericCellValue();
                    CommodityCategory commodityCategory = CommodityCategory.builder()
                            .type(commodityMapValue.getType())
                            .region(commodityMapValue.getRegion())
                            .unit(commodityMapValue.getUnit())
                            .build();
                    List<CommodityPrice> priceList = categoryPriceMap.getOrDefault(commodityCategory, new ArrayList<>());
                    priceList.add(CommodityPrice.builder()
                            .price(price)
                            .date(date)
                            .build());
                    categoryPriceMap.put(commodityCategory, priceList);
                } else {
                    log.debug("Skipping cell for " + commodityMapValue.getType() + " due to missing or non-numeric value");
                }
            });
        }, 6);
        return categoryPriceMap;
    }
}
