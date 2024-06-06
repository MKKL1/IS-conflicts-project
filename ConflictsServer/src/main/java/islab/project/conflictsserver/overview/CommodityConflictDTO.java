package islab.project.conflictsserver.overview;

import islab.project.conflictsserver.commodities.CommodityCategory;
import islab.project.conflictsserver.commodities.CommodityCategoryDTO;
import islab.project.conflictsserver.conflict.ConflictOverviewDTO;

import java.util.List;

public record CommodityConflictDTO(List<CommodityCategoryDTO> commodities, List<ConflictOverviewDTO> conflicts) {
}
