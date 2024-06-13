package islab.project.conflictsserver;

import islab.project.conflictsserver.commodities.CommodityCategory;
import islab.project.conflictsserver.commodities.CommodityPrice;
import islab.project.conflictsserver.conflict.ConflictRepository;
import islab.project.conflictsserver.conflict.converter.ConflictRowData;
import islab.project.conflictsserver.services.DataImportService;
import islab.project.conflictsserver.services.DataSaveService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DataImportServiceTests {
    private static final int CONFLICT_COUNT = 240;
    @Autowired
    private DataImportService dataImportService;
    @Autowired
    private ConflictRepository conflictRepository;
    @Autowired
    private DataSaveService dataSaveService;
    @Test
    public void test_valid_excel_file() throws IOException {
        //TODO testing on resource, move resource somewhere else
        ClassPathResource resource = new ClassPathResource("MainConflictTable.xls");
        InputStream inputStream = resource.getInputStream();
        List<ConflictRowData> result = dataImportService.importConflictData(inputStream);
        assertNotNull(result);
        Assertions.assertEquals(CONFLICT_COUNT, result.size());
    }

    @Test
    public void save_test() throws IOException {
        ClassPathResource resource = new ClassPathResource("MainConflictTable.xls");
        InputStream inputStream = resource.getInputStream();
        List<ConflictRowData> result = dataImportService.importConflictData(inputStream);
        conflictRepository.saveConflictList(result);
    }

//    @Test
//    public void test_valid_xlsx_file() throws IOException {
//        ClassPathResource resource = new ClassPathResource("CMO-Historical-Data-Monthly.xlsx");
//        InputStream inputStream = resource.getInputStream();
//        //TODO
//    }

    @Test
    public void test_oil_import_and_save() throws IOException {
        ClassPathResource crudeOilResource = new ClassPathResource("CrudeOilPricesData.csv");
        InputStream crudeOilInputStream = crudeOilResource.getInputStream();
        var crudeOilResult = dataImportService.importCommoditiesData(crudeOilInputStream, "Crude Oil", null);
        dataSaveService.save(crudeOilResult);
    }

    @Test
    public void test_gas_import_and_save() throws IOException {
        ClassPathResource gasResource = new ClassPathResource("GasPricesData.csv");
        InputStream gasInputStream = gasResource.getInputStream();
        var gasResult = dataImportService.importCommoditiesData(gasInputStream, "Gas", null);
        dataSaveService.save(gasResult);
    }

    @Test
    public void test_gold_import_and_save() throws IOException {
        ClassPathResource goldResource = new ClassPathResource("GoldPricesData.csv");
        InputStream goldInputStream = goldResource.getInputStream();
        var goldResult = dataImportService.importCommoditiesData(goldInputStream, "Gold", null);
        dataSaveService.save(goldResult);
    }

    @Test
    public void test_coal_import_and_save() throws IOException {
        ClassPathResource coalResource = new ClassPathResource("CoalPricesData.csv");
        InputStream coalInputStream = coalResource.getInputStream();
        var coalResult = dataImportService.importCommoditiesData(coalInputStream, "Coal", null);
        dataSaveService.save(coalResult);
    }

    @Test
    public void test_metals_import_and_save() throws IOException {
        ClassPathResource metalsResource = new ClassPathResource("MetalsPricesData.csv");
        InputStream metalsInputStream = metalsResource.getInputStream();
        var metalsResult = dataImportService.importMetalsData(metalsInputStream);
        dataSaveService.save(metalsResult);
    }

    @Test
    public void test_cmo_historical_data_import_and_save() throws IOException {
        ClassPathResource cmoResources = new ClassPathResource("CMO-Historical-Data-Monthly.xlsx");
        InputStream cmoInputStream = cmoResources.getInputStream();
        Map<CommodityCategory, List<CommodityPrice>> cmoResult = dataImportService.importCMOHistoricalData(cmoInputStream);
        dataSaveService.save(cmoResult);
    }
}
