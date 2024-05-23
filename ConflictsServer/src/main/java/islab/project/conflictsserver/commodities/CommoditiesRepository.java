package islab.project.conflictsserver.commodities;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommoditiesRepository extends CrudRepository<Commodity, Integer> {
    default void saveCommodityList(List<Commodity> commodityList) {
        saveAll(commodityList);
    }
}
