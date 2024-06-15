package islab.project.conflictsserver.overview;

import islab.project.conflictsserver.commodities.CommoditiesRepository;
import islab.project.conflictsserver.commodities.CommodityCategory;
import islab.project.conflictsserver.commodities.CommodityCategoryDTO;
import islab.project.conflictsserver.commodities.CommodityCategoryRepository;
import islab.project.conflictsserver.conflict.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OverviewService {
    private final ConflictRepository conflictRepository;
    private final CommodityCategoryRepository commodityCategoryRepository;
    private final ConflictMapper conflictMapper;

    public OverviewService(ConflictRepository conflictRepository, CommodityCategoryRepository commodityCategoryRepository, ConflictMapper conflictMapper) {
        this.conflictRepository = conflictRepository;
        this.commodityCategoryRepository = commodityCategoryRepository;
        this.conflictMapper = conflictMapper;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CommodityConflictDTO getCommodityCategoriesAndConflicts() {
        List<CommodityCategoryDTO> commodityCategoryList = StreamSupport.stream(commodityCategoryRepository.findAll().spliterator(), false)
                .map(x -> new CommodityCategoryDTO(x. getId(), x.getType(), x.getRegion(), x.getUnit()))
                .collect(Collectors.toList());
        List<ConflictDTO> conflictList = StreamSupport.stream(conflictRepository.findAll().spliterator(), false)
                .map(conflictMapper::toDTO)
                .toList();
        return new CommodityConflictDTO(commodityCategoryList, conflictList);
    }
}
