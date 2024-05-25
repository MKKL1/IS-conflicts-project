package islab.project.conflictsserver.overview;

import islab.project.conflictsserver.commodities.CommoditiesRepository;
import islab.project.conflictsserver.commodities.CommodityCategory;
import islab.project.conflictsserver.conflict.Conflict;
import islab.project.conflictsserver.conflict.ConflictOverviewDTO;
import islab.project.conflictsserver.conflict.ConflictRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class OverviewService {
    private final ConflictRepository conflictRepository;
    private final CommoditiesRepository commoditiesRepository;

    public OverviewService(ConflictRepository conflictRepository, CommoditiesRepository commoditiesRepository) {
        this.conflictRepository = conflictRepository;
        this.commoditiesRepository = commoditiesRepository;
    }

    public CommodityConflictDTO getCommodityCategoriesAndConflicts() {
        List<CommodityCategory> commodityCategoryList = commoditiesRepository.findDistinctTypeUnitRegion();
        List<ConflictOverviewDTO> conflictList = StreamSupport.stream(conflictRepository.findAll().spliterator(), false)
                .map(x -> new ConflictOverviewDTO(x.getId(), x.getLocation(), x.getStartTime(), x.getEndTime()))
                .toList();
        return new CommodityConflictDTO(commodityCategoryList, conflictList);
    }
}
