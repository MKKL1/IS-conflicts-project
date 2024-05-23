package islab.project.conflictsserver;

import islab.project.conflictsserver.data.ConflictRowData;
import islab.project.conflictsserver.entity.ResourcesEntity;
import islab.project.conflictsserver.services.DataExportService;
import islab.project.conflictsserver.services.DataImportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
public class DataExportServiceTests {

    @Autowired
    private DataImportService dataImportService;

    //Conficts
    @Autowired
    private ConflictRepository conflictRepository;

    @Test
    public void save_test() throws IOException {
        ClassPathResource resource = new ClassPathResource("MainConflictTable.xls");
        InputStream inputStream = resource.getInputStream();
        List<ConflictRowData> result = dataImportService.importConflictData(inputStream);
        conflictRepository.saveConflictList(result);
    }

    //Resources
    @Autowired
    private ResourcesRepository resourcesRepository;

    @Test
    public void save_test_resources() throws IOException {

        // Test for CrudeOilPricesData.csv
        ClassPathResource crudeOilResource = new ClassPathResource("CrudeOilPricesData.csv");
        InputStream crudeOilInputStream = crudeOilResource.getInputStream();
        List<ResourcesEntity> crudeOilResult = dataImportService.importResourcesData(crudeOilInputStream, "Crude Oil");
        resourcesRepository.saveAll(crudeOilResult);


        // Test for GasPricesData.csv
        ClassPathResource gasResource = new ClassPathResource("GasPricesData.csv");
        InputStream gasInputStream = gasResource.getInputStream();
        List<ResourcesEntity> gasResult = dataImportService.importResourcesData(gasInputStream, "Gas");
        resourcesRepository.saveAll(gasResult);

        // Test for GoldPricesData.csv
        ClassPathResource goldResource = new ClassPathResource("GoldPricesData.csv");
        InputStream goldInputStream = goldResource.getInputStream();
        List<ResourcesEntity> goldResult = dataImportService.importResourcesData(goldInputStream, "Gold");
        resourcesRepository.saveAll(goldResult);

        // Test for CoalPricesData.csv
        ClassPathResource coalResource = new ClassPathResource("CoalPricesData.csv");
        InputStream coalInputStream = coalResource.getInputStream();
        List<ResourcesEntity> coalResult = dataImportService.importResourcesData(coalInputStream, "Coal");
        resourcesRepository.saveAll(coalResult);

        // Test for MetalsPricesData.csv
        ClassPathResource metalsResource = new ClassPathResource("MetalsPricesData.csv");
        InputStream metalsInputStream = metalsResource.getInputStream();
        List<ResourcesEntity> metalsResult = dataImportService.importMetalsData(metalsInputStream);
        resourcesRepository.saveAll(metalsResult);

    }
}
