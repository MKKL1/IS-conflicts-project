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

}
