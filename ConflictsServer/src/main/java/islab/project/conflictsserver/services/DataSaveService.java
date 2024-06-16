package islab.project.conflictsserver.services;

import islab.project.conflictsserver.commodities.CommoditiesRepository;
import islab.project.conflictsserver.commodities.CommodityCategory;
import islab.project.conflictsserver.commodities.CommodityCategoryRepository;
import islab.project.conflictsserver.commodities.CommodityPrice;
import islab.project.conflictsserver.conflict.ConflictRepository;
import islab.project.conflictsserver.conflict.converter.ConflictRowData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DataSaveService {
    private final CommodityCategoryRepository commodityCategoryRepository;
    private final CommoditiesRepository commodityPriceRepository;
    private final ConflictRepository conflictRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void save(List<ConflictRowData> conflictRowDataList) {
        conflictRepository.saveAll(conflictRowDataList.stream().map(ConflictRowData::toEntity).collect(Collectors.toList()));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void save(Map<CommodityCategory, List<CommodityPrice>> commodityPriceMap) {
        Set<String> types = commodityPriceMap.keySet().stream().map(CommodityCategory::getType).collect(Collectors.toSet());
        Set<String> regions = commodityPriceMap.keySet().stream().map(CommodityCategory::getRegion).collect(Collectors.toSet());
        Set<String> units = commodityPriceMap.keySet().stream().map(CommodityCategory::getUnit).collect(Collectors.toSet());

        List<CommodityCategory> existingCategories = commodityCategoryRepository.findAllByTypeInAndRegionInAndUnitIn(types, regions, units);
        Map<String, CommodityCategory> categoryMap = existingCategories.stream().collect(Collectors.toMap(
                category -> category.getType() + "-" + category.getRegion() + "-" + category.getUnit(),
                category -> category
        ));

        for (Map.Entry<CommodityCategory, List<CommodityPrice>> entry : commodityPriceMap.entrySet()) {
            CommodityCategory category = entry.getKey();
            List<CommodityPrice> prices = entry.getValue();

            String categoryKey = category.getType() + "-" + category.getRegion() + "-" + category.getUnit();

            CommodityCategory categoryToUse;
            if (categoryMap.containsKey(categoryKey)) {
                categoryToUse = categoryMap.get(categoryKey);
            } else {
                categoryToUse = commodityCategoryRepository.save(category);
                categoryMap.put(categoryKey, categoryToUse);
            }

            List<LocalDate> dates = prices.stream().map(CommodityPrice::getDate).collect(Collectors.toList());

            //Wielokrotne czytanie dlatego repeatable read
            List<CommodityPrice> existingPrices = commodityPriceRepository.findAllByCommodityCategoryAndDateIn(categoryToUse, dates);
            Set<LocalDate> existingDates = existingPrices.stream().map(CommodityPrice::getDate).collect(Collectors.toSet());

            List<CommodityPrice> newPrices = prices.stream()
                    .filter(price -> !existingDates.contains(price.getDate()))
                    .collect(Collectors.toList());

            newPrices.forEach(price -> price.setCommodityCategory(categoryToUse));
            commodityPriceRepository.saveAll(newPrices);
        }
    }
}
