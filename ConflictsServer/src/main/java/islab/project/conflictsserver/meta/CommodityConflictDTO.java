package islab.project.conflictsserver.meta;

import islab.project.conflictsserver.conflict.Conflict;

import java.util.List;

public record CommodityConflictDTO(List<CommodityCategoryDTO> commodities, List<Conflict> conflicts) {
}
