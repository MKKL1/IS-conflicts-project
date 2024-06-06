package islab.project.conflictsserver.commodities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CommoditiesRepository extends CrudRepository<CommodityPrice, Integer> {
    default void saveCommodityList(List<CommodityPrice> commodityList) {
        saveAll(commodityList);
    }

    @Query("SELECT cp FROM CommodityPrice cp WHERE cp.commodityCategory = :category AND cp.date IN :dates")
    List<CommodityPrice> findAllByCommodityCategoryAndDateIn(@Param("category") CommodityCategory category, @Param("dates") List<LocalDate> dates);

    @Query("SELECT new islab.project.conflictsserver.commodities.CommodityPriceDTO(c.price, c.date) " +
            "FROM CommodityPrice c " +
            "WHERE c.commodityCategory = :#{#category} " +
            "order by c.date")
    List<CommodityPriceDTO> findCommodityPriceByCategory(@Param("category") CommodityCategory category);
}
