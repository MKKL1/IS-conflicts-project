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

    @PostMapping("/{name}")
    public ResponseEntity<?> importData(@PathVariable("name") String name, @RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            switch (name) {
                case "conflicts":
                    List<ConflictRowData> conflictData = dataImportService.importConflictData(inputStream);
                    conflictRepository.saveConflictList(conflictData);
                    break;
                case "cmo-historical":
                    Map<CommodityCategory, List<CommodityPrice>> commodityData = dataImportService.importCMOHistoricalData(inputStream);
                    dataSaveService.save(commodityData);
                    break;
                case "metals":
                    Map<CommodityCategory, List<CommodityPrice>> metalsData = dataImportService.importMetalsData(inputStream);
                    dataSaveService.save(metalsData);
                    break;
                case "coal":
                    Map<CommodityCategory, List<CommodityPrice>> coalData = dataImportService.importCommoditiesData(inputStream, "Coal", "t");
                    dataSaveService.save(coalData);
                    break;
                case "crudeOil":
                    Map<CommodityCategory, List<CommodityPrice>> crudeOilData = dataImportService.importCommoditiesData(inputStream, "Crude Oil", "m^3");
                    dataSaveService.save(crudeOilData);
                    break;
                case "gas":
                    Map<CommodityCategory, List<CommodityPrice>> gasData = dataImportService.importCommoditiesData(inputStream, "Gas", "MWh");
                    dataSaveService.save(gasData);
                    break;
                case "gold":
                    Map<CommodityCategory, List<CommodityPrice>> goldData = dataImportService.importCommoditiesData(inputStream, "Gold", "kg");
                    dataSaveService.save(goldData);
                    break;
                default:
                    return ResponseEntity.status(400).body("Invalid import type specified");
            }
            return ResponseEntity.ok("Data imported successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to import data");
        }
    }
}
