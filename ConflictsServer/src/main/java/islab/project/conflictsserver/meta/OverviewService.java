package islab.project.conflictsserver.meta;

import islab.project.conflictsserver.commodities.CommoditiesRepository;
import islab.project.conflictsserver.conflict.Conflict;
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
        List<CommodityCategoryDTO> commodityCategoryDTOList = commoditiesRepository.findDistinctTypeUnitRegion();
        List<Conflict> conflictList = StreamSupport.stream(conflictRepository.findAll().spliterator(), false).toList();
        return new CommodityConflictDTO(commodityCategoryDTOList, conflictList);
    }
}
