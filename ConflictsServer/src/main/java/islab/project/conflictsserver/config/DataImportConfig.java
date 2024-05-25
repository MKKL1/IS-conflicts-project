package islab.project.conflictsserver.config;

import islab.project.conflictsserver.services.DataImportService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataImportConfig {

    @Getter
    @AllArgsConstructor
    public static class CommodityMapValue {
        String type;
        String region;
        String unit;
    }

    @Bean
    public Map<Integer, CommodityMapValue> integerCommodityMapValueMap() {
        Map<Integer, CommodityMapValue> commodityKeyMap = new HashMap<>();
        commodityKeyMap.put(1, new CommodityMapValue("Crude oil", "World", "bbl"));
        commodityKeyMap.put(2, new CommodityMapValue("Crude oil", "Brent", "bbl"));
        commodityKeyMap.put(3, new CommodityMapValue("Crude oil", "Dubai", "bbl"));
        commodityKeyMap.put(4, new CommodityMapValue("Crude oil", "WTI", "bbl"));
        commodityKeyMap.put(5, new CommodityMapValue("Coal", "Australian", "mt"));
        commodityKeyMap.put(6, new CommodityMapValue("Coal", "South African", "mt"));
        commodityKeyMap.put(7, new CommodityMapValue("Natural gas", "US", "mmbtu"));
        commodityKeyMap.put(8, new CommodityMapValue("Natural gas", "Europe", "mmbtu"));
        commodityKeyMap.put(9, new CommodityMapValue("Liquefied natural gas", "Japan", "mmbtu"));
        commodityKeyMap.put(11, new CommodityMapValue("Cocoa", "World", "kg"));
        commodityKeyMap.put(18, new CommodityMapValue("Coconut oil", "World", "mt"));
        commodityKeyMap.put(22, new CommodityMapValue("Palm oil", "World", "mt"));
        commodityKeyMap.put(36, new CommodityMapValue("Wheat", "US SRW", "mt"));
        commodityKeyMap.put(37, new CommodityMapValue("Wheat", "US HRW", "mt"));
        commodityKeyMap.put(45, new CommodityMapValue("Sugar", "EU", "kg"));
        commodityKeyMap.put(46, new CommodityMapValue("Sugar", "US", "kg"));
        commodityKeyMap.put(47, new CommodityMapValue("Sugar", "World", "kg"));
        commodityKeyMap.put(48, new CommodityMapValue("Tobacco", "US import u.v.", "mt"));
        commodityKeyMap.put(51, new CommodityMapValue("Sawnwood", "Cameroon", "cubic meter"));
        commodityKeyMap.put(52, new CommodityMapValue("Sawnwood", "Malaysian", "cubic meter"));
        commodityKeyMap.put(54, new CommodityMapValue("Cotton", "World", "kg"));
        commodityKeyMap.put(62, new CommodityMapValue("Aluminum", "World", "mt"));
        commodityKeyMap.put(63, new CommodityMapValue("Iron ore", "World", "dmtu"));
        commodityKeyMap.put(64, new CommodityMapValue("Copper", "World", "mt"));
        commodityKeyMap.put(65, new CommodityMapValue("Lead", "World", "mt"));
        commodityKeyMap.put(66, new CommodityMapValue("Tin", "World", "mt"));
        commodityKeyMap.put(67, new CommodityMapValue("Nickel", "World", "mt"));
        commodityKeyMap.put(68, new CommodityMapValue("Zinc", "World", "mt"));
        commodityKeyMap.put(69, new CommodityMapValue("Gold", "World", "troy oz"));
        commodityKeyMap.put(70, new CommodityMapValue("Platinum", "World", "troy oz"));
        commodityKeyMap.put(71, new CommodityMapValue("Silver", "World", "troy oz"));
        return commodityKeyMap;
    }
}
