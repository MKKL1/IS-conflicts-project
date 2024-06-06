package islab.project.conflictsserver.overview;

import islab.project.conflictsserver.commodities.CommoditiesRepository;
import islab.project.conflictsserver.commodities.CommodityCategory;
import islab.project.conflictsserver.commodities.CommodityCategoryDTO;
import islab.project.conflictsserver.commodities.CommodityCategoryRepository;
import islab.project.conflictsserver.conflict.Conflict;
import islab.project.conflictsserver.conflict.ConflictOverviewDTO;
import islab.project.conflictsserver.conflict.ConflictRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OverviewService {
    private final ConflictRepository conflictRepository;
    private final CommodityCategoryRepository commodityCategoryRepository;

    public OverviewService(ConflictRepository conflictRepository, CommodityCategoryRepository commodityCategoryRepository) {
        this.conflictRepository = conflictRepository;
        this.commodityCategoryRepository = commodityCategoryRepository;
    }

    public CommodityConflictDTO getCommodityCategoriesAndConflicts() {
        List<CommodityCategoryDTO> commodityCategoryList = StreamSupport.stream(commodityCategoryRepository.findAll().spliterator(), false)
                .map(x -> new CommodityCategoryDTO(x. getId(), x.getType(), x.getRegion(), x.getUnit()))
                .collect(Collectors.toList());
        List<ConflictOverviewDTO> conflictList = StreamSupport.stream(conflictRepository.findAll().spliterator(), false)
                .map(x -> new ConflictOverviewDTO(x.getId(), x.getLocation(), x.getStartTime(), x.getEndTime()))
                .toList();
        return new CommodityConflictDTO(commodityCategoryList, conflictList);
    }
}
