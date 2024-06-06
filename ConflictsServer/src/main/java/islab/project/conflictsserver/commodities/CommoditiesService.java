package islab.project.conflictsserver.commodities;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CommoditiesService {

    private final CommoditiesRepository commoditiesRepository;
    private final CommodityCategoryRepository commodityCategoryRepository;

    public CommoditiesService(CommoditiesRepository commoditiesRepository, CommodityCategoryRepository commodityCategoryRepository) {
        this.commoditiesRepository = commoditiesRepository;
        this.commodityCategoryRepository = commodityCategoryRepository;
    }

    //save
    public void saveResourcesList(List<CommodityPrice> resourcesList) {
        commoditiesRepository.saveCommodityList(resourcesList);
    }

    //read
    public List<CommodityPrice> findAll() {
        return StreamSupport.stream(commoditiesRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<CommodityPriceDTO> findValuesByCategory(Integer id) {
        Optional<CommodityCategory> commodityCategory = commodityCategoryRepository.findById(id);
        if(commodityCategory.isEmpty()) return new ArrayList<>();
        List<CommodityPriceDTO> commodityPriceDTOList = commoditiesRepository.findCommodityPriceByCategory(commodityCategory.get());
        if(commodityPriceDTOList.isEmpty()) throw new CommodityNotFoundException("Commodity with values " + commodityCategory.toString() + " not found.");
        return commodityPriceDTOList;
    }

}
