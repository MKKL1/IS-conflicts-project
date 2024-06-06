package islab.project.conflictsserver;

import islab.project.conflictsserver.commodities.CommoditiesRepository;
import islab.project.conflictsserver.commodities.CommodityCategory;
import islab.project.conflictsserver.conflict.ConflictRepository;
import islab.project.conflictsserver.conflict.converter.ConflictRowData;
import islab.project.conflictsserver.commodities.CommodityPrice;
import islab.project.conflictsserver.services.DataImportService;
import islab.project.conflictsserver.services.DataSaveService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class DataExportServiceTests {

    @Autowired
    private DataImportService dataImportService;

    //Conficts
    @Autowired
    private ConflictRepository conflictRepository;

    @Autowired
    private DataSaveService dataSaveService;

    @Test
    public void save_test() throws IOException {
        ClassPathResource resource = new ClassPathResource("MainConflictTable.xls");
        InputStream inputStream = resource.getInputStream();
        List<ConflictRowData> result = dataImportService.importConflictData(inputStream);
        conflictRepository.saveConflictList(result);
    }

    //Resources
    @Autowired
    private CommoditiesRepository commoditiesRepository;

    @Test
    public void save_test_resources() throws IOException {

        /*
        // Test for CrudeOilPricesData.csv
        ClassPathResource crudeOilResource = new ClassPathResource("CrudeOilPricesData.csv");
        InputStream crudeOilInputStream = crudeOilResource.getInputStream();
        List<Commodity> crudeOilResult = dataImportService.importCommoditiesData(crudeOilInputStream, "Crude Oil");
        commoditiesRepository.saveAll(crudeOilResult);


        // Test for GasPricesData.csv
        ClassPathResource gasResource = new ClassPathResource("GasPricesData.csv");
        InputStream gasInputStream = gasResource.getInputStream();
        List<Commodity> gasResult = dataImportService.importCommoditiesData(gasInputStream, "Gas");
        commoditiesRepository.saveAll(gasResult);

        // Test for GoldPricesData.csv
        ClassPathResource goldResource = new ClassPathResource("GoldPricesData.csv");
        InputStream goldInputStream = goldResource.getInputStream();
        List<Commodity> goldResult = dataImportService.importCommoditiesData(goldInputStream, "Gold");
        commoditiesRepository.saveAll(goldResult);

        // Test for CoalPricesData.csv
        ClassPathResource coalResource = new ClassPathResource("CoalPricesData.csv");
        InputStream coalInputStream = coalResource.getInputStream();
        List<Commodity> coalResult = dataImportService.importCommoditiesData(coalInputStream, "Coal");
        commoditiesRepository.saveAll(coalResult);

        // Test for MetalsPricesData.csv
        ClassPathResource metalsResource = new ClassPathResource("MetalsPricesData.csv");
        InputStream metalsInputStream = metalsResource.getInputStream();
        List<Commodity> metalsResult = dataImportService.importMetalsData(metalsInputStream);
        commoditiesRepository.saveAll(metalsResult);
        */

        // Test for CMO-Historical-Data-Monthly.xlsx
        ClassPathResource cmoResources = new ClassPathResource("CMO-Historical-Data-Monthly.xlsx");
        InputStream cmoInputStream = cmoResources.getInputStream();
        Map<CommodityCategory, List<CommodityPrice>> cmoResult = dataImportService.importCMOHistoricalData(cmoInputStream);
        dataSaveService.save(cmoResult);

    }
}
