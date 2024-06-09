package islab.project.conflictsserver.dataImports;

import islab.project.conflictsserver.commodities.CommodityCategory;
import islab.project.conflictsserver.conflict.converter.ConflictRowData;
import islab.project.conflictsserver.commodities.CommodityPrice;
import islab.project.conflictsserver.services.DataImportService;
import islab.project.conflictsserver.services.DataSaveService;
import islab.project.conflictsserver.conflict.ConflictRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/imports")
public class ImportsController {

    @Autowired
    private ConflictRepository conflictRepository;

    @Autowired
    private DataImportService dataImportService;

    @Autowired
    private DataSaveService dataSaveService;


    @PostMapping("/conflicts")
    public ResponseEntity<?> importConflicts(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            List<ConflictRowData> result = dataImportService.importConflictData(inputStream);
            conflictRepository.saveConflictList(result);
            return ResponseEntity.ok("Conflicts data imported successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to import conflicts data");
        }
    }

    @PostMapping("/commodities/cmo-historical")
    public ResponseEntity<?> importCMOHistorical(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            Map<CommodityCategory, List<CommodityPrice>> result = dataImportService.importCMOHistoricalData(inputStream);
            dataSaveService.save(result);
            return ResponseEntity.ok("CMO Historical data imported successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to import CMO Historical data");
        }
    }

    /*
    @PostMapping("/commodities/crude-oil")
    public ResponseEntity<?> importCrudeOil(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            List<Commodity> result = dataImportService.importCommoditiesData(inputStream, "Crude Oil");
            dataSaveService.saveCommodities(result);
            return ResponseEntity.ok("Crude Oil data imported successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to import Crude Oil data");
        }
    }

    @PostMapping("/commodities/metals")
    public ResponseEntity<?> importMetals(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            List<Commodity> result = dataImportService.importMetalsData(inputStream);
            dataSaveService.saveCommodities(result);
            return ResponseEntity.ok("Metals data imported successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to import Metals data");
        }
    }
     */

}

