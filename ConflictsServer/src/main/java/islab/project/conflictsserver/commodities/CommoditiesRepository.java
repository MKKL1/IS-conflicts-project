package islab.project.conflictsserver.commodities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommoditiesRepository extends CrudRepository<Commodity, Integer> {
    default void saveCommodityList(List<Commodity> commodityList) {
        saveAll(commodityList);
    }

    @Query("select new islab.project.conflictsserver.commodities.CommodityCategory(c.type, c.region, c.unit) from Commodity c group by c.type, c.unit, c.region")
    List<CommodityCategory> findDistinctTypeUnitRegion();

    @Query("SELECT new islab.project.conflictsserver.commodities.CommodityPriceDTO(c.price, c.date) " +
            "FROM Commodity c " +
            "WHERE c.type = :#{#category.type} " +
            "AND c.region = :#{#category.region} " +
            "AND c.unit = :#{#category.unit} " +
            "order by c.date")
    List<CommodityPriceDTO> findCommodityPriceByCategory(@Param("category") CommodityCategory category);
}
