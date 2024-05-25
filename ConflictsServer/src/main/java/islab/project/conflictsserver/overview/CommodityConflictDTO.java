package islab.project.conflictsserver.overview;

import islab.project.conflictsserver.commodities.CommodityCategory;
import islab.project.conflictsserver.conflict.ConflictOverviewDTO;

import java.util.List;

public record CommodityConflictDTO(List<CommodityCategory> commodities, List<ConflictOverviewDTO> conflicts) {
}
