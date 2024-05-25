package islab.project.conflictsserver.commodities;

import islab.project.conflictsserver.meta.CommodityCategoryDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommoditiesRepository extends CrudRepository<Commodity, Integer> {
    default void saveCommodityList(List<Commodity> commodityList) {
        saveAll(commodityList);
    }

    @Query("select new islab.project.conflictsserver.meta.CommodityCategoryDTO(c.type, c.region, c.unit) from Commodity c group by c.type, c.unit, c.region")
    List<CommodityCategoryDTO> findDistinctTypeUnitRegion();
}
