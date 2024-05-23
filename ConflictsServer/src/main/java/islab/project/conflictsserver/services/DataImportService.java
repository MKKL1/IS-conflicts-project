package islab.project.conflictsserver.services;

import islab.project.conflictsserver.conflict.converter.ConflictIntensity;
import islab.project.conflictsserver.conflict.converter.ConflictRowData;
import islab.project.conflictsserver.conflict.converter.ConflictType;
import islab.project.conflictsserver.data.CSVConverter;
import islab.project.conflictsserver.data.XLSConverter;
import islab.project.conflictsserver.commodities.Commodity;
import islab.project.conflictsserver.data.XLSXConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
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


    //import CMOHistorical resources Data from XLSX
    public List<Commodity> importCMOHistoricalData(InputStream inputStream) throws IOException {
        class CommodityKey {
            public String type;
            public String region;

            public CommodityKey(String type, String region) {
                this.type = type;
                this.region = region;
            }
        }

        // Definition of types and regions
        Map<Integer, CommodityKey> commodityKeyMap = new HashMap<>();
        commodityKeyMap.put(1, new CommodityKey("Crude oil", "World"));
        commodityKeyMap.put(2, new CommodityKey("Crude oil", "Brent"));
        commodityKeyMap.put(3, new CommodityKey("Crude oil", "Dubai"));
        commodityKeyMap.put(4, new CommodityKey("Crude oil", "WTI"));
        commodityKeyMap.put(5, new CommodityKey("Coal", "Australian"));
        commodityKeyMap.put(6, new CommodityKey("Coal", "South African"));
        commodityKeyMap.put(7, new CommodityKey("Natural gas", "US"));
        commodityKeyMap.put(8, new CommodityKey("Natural gas", "Europe"));
        commodityKeyMap.put(9, new CommodityKey("Liquefied natural gas", "Japan"));
        commodityKeyMap.put(11, new CommodityKey("Cocoa", "World"));
        commodityKeyMap.put(18, new CommodityKey("Coconut oil", "World"));
        commodityKeyMap.put(22, new CommodityKey("Palm oil", "World"));
        commodityKeyMap.put(36, new CommodityKey("Wheat", "US SRW"));
        commodityKeyMap.put(37, new CommodityKey("Wheat", "US HRW"));
        commodityKeyMap.put(45, new CommodityKey("Sugar", "EU"));
        commodityKeyMap.put(46, new CommodityKey("Sugar", "US"));
        commodityKeyMap.put(47, new CommodityKey("Sugar", "World"));
        commodityKeyMap.put(48, new CommodityKey("Tobacco", "US import u.v."));
        commodityKeyMap.put(51, new CommodityKey("Sawnwood", "Cameroon"));
        commodityKeyMap.put(52, new CommodityKey("Sawnwood", "Malaysian"));
        commodityKeyMap.put(54, new CommodityKey("Cotton", "World"));
        commodityKeyMap.put(62, new CommodityKey("Aluminum", "World"));
        commodityKeyMap.put(63, new CommodityKey("Iron ore", "World"));
        commodityKeyMap.put(64, new CommodityKey("Copper", "World"));
        commodityKeyMap.put(65, new CommodityKey("Lead", "World"));
        commodityKeyMap.put(66, new CommodityKey("Tin", "World"));
        commodityKeyMap.put(67, new CommodityKey("Nickel", "World"));
        commodityKeyMap.put(68, new CommodityKey("Zinc", "World"));
        commodityKeyMap.put(69, new CommodityKey("Gold", "World"));
        commodityKeyMap.put(70, new CommodityKey("Platinum", "World"));
        commodityKeyMap.put(71, new CommodityKey("Silver", "World"));

        List<Commodity> commodities = new ArrayList<>();

        XLSXConverter.convert(inputStream, row -> {
            // Fixing date
            String dateStr = row.getCell(0).getStringCellValue();
            String[] parts = dateStr.split("M");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);

            LocalDate date = LocalDate.of(year, month, 1);

            commodityKeyMap.forEach((columnIndex, commodityKey) -> {
                Cell cell = row.getCell(columnIndex);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    double price = cell.getNumericCellValue();
                    //log.info("Adding commodity: " + commodityKey.type + ", " + commodityKey.region + ", " + price + ", " + date); //check info
                    commodities.add(Commodity.builder()
                            .region(commodityKey.region)
                            .type(commodityKey.type)
                            .price(price)
                            .date(date)
                            .build());
                } else {
                    log.info("Skipping cell for " + commodityKey.type + " due to missing or non-numeric value");
                }
            });
            return null; // Returning null because we're adding commodities directly to the list
        }, 6);
        return commodities;
    }


}
