package islab.project.conflictsserver.commodities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CommodityCategoryRepository extends CrudRepository<CommodityCategory, Integer> {
    Optional<CommodityCategory> findByTypeAndRegionAndUnit(String type, String region, String unit);

    @Query("SELECT cc FROM CommodityCategory cc WHERE cc.type IN :types AND cc.region IN :regions AND cc.unit IN :units")
    List<CommodityCategory> findAllByTypeInAndRegionInAndUnitIn(
            @Param("types") Set<String> types,
            @Param("regions") Set<String> regions,
            @Param("units") Set<String> units);
}
