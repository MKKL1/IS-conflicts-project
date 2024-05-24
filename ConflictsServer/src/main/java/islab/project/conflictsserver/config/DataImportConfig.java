package islab.project.conflictsserver.config;

import islab.project.conflictsserver.services.DataImportService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataImportConfig {

    @Bean
    public Map<Integer, DataImportService.CommodityMapValue> integerCommodityMapValueMap() {
        Map<Integer, DataImportService.CommodityMapValue> commodityKeyMap = new HashMap<>();
        commodityKeyMap.put(1, new DataImportService.CommodityMapValue("Crude oil", "World", "bbl"));
        commodityKeyMap.put(2, new DataImportService.CommodityMapValue("Crude oil", "Brent", "bbl"));
        commodityKeyMap.put(3, new DataImportService.CommodityMapValue("Crude oil", "Dubai", "bbl"));
        commodityKeyMap.put(4, new DataImportService.CommodityMapValue("Crude oil", "WTI", "bbl"));
        commodityKeyMap.put(5, new DataImportService.CommodityMapValue("Coal", "Australian", "mt"));
        commodityKeyMap.put(6, new DataImportService.CommodityMapValue("Coal", "South African", "mt"));
        commodityKeyMap.put(7, new DataImportService.CommodityMapValue("Natural gas", "US", "mmbtu"));
        commodityKeyMap.put(8, new DataImportService.CommodityMapValue("Natural gas", "Europe", "mmbtu"));
        commodityKeyMap.put(9, new DataImportService.CommodityMapValue("Liquefied natural gas", "Japan", "mmbtu"));
        commodityKeyMap.put(11, new DataImportService.CommodityMapValue("Cocoa", "World", "kg"));
        commodityKeyMap.put(18, new DataImportService.CommodityMapValue("Coconut oil", "World", "mt"));
        commodityKeyMap.put(22, new DataImportService.CommodityMapValue("Palm oil", "World", "mt"));
        commodityKeyMap.put(36, new DataImportService.CommodityMapValue("Wheat", "US SRW", "mt"));
        commodityKeyMap.put(37, new DataImportService.CommodityMapValue("Wheat", "US HRW", "mt"));
        commodityKeyMap.put(45, new DataImportService.CommodityMapValue("Sugar", "EU", "kg"));
        commodityKeyMap.put(46, new DataImportService.CommodityMapValue("Sugar", "US", "kg"));
        commodityKeyMap.put(47, new DataImportService.CommodityMapValue("Sugar", "World", "kg"));
        commodityKeyMap.put(48, new DataImportService.CommodityMapValue("Tobacco", "US import u.v.", "mt"));
        commodityKeyMap.put(51, new DataImportService.CommodityMapValue("Sawnwood", "Cameroon", "cubic meter"));
        commodityKeyMap.put(52, new DataImportService.CommodityMapValue("Sawnwood", "Malaysian", "cubic meter"));
        commodityKeyMap.put(54, new DataImportService.CommodityMapValue("Cotton", "World", "kg"));
        commodityKeyMap.put(62, new DataImportService.CommodityMapValue("Aluminum", "World", "mt"));
        commodityKeyMap.put(63, new DataImportService.CommodityMapValue("Iron ore", "World", "dmtu"));
        commodityKeyMap.put(64, new DataImportService.CommodityMapValue("Copper", "World", "mt"));
        commodityKeyMap.put(65, new DataImportService.CommodityMapValue("Lead", "World", "mt"));
        commodityKeyMap.put(66, new DataImportService.CommodityMapValue("Tin", "World", "mt"));
        commodityKeyMap.put(67, new DataImportService.CommodityMapValue("Nickel", "World", "mt"));
        commodityKeyMap.put(68, new DataImportService.CommodityMapValue("Zinc", "World", "mt"));
        commodityKeyMap.put(69, new DataImportService.CommodityMapValue("Gold", "World", "troy oz"));
        commodityKeyMap.put(70, new DataImportService.CommodityMapValue("Platinum", "World", "troy oz"));
        commodityKeyMap.put(71, new DataImportService.CommodityMapValue("Silver", "World", "troy oz"));
        return commodityKeyMap;
    }
}
