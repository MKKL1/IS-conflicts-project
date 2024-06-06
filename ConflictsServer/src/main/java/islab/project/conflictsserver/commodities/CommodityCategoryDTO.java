package islab.project.conflictsserver.commodities;

import lombok.Builder;

@Builder
public record CommodityCategoryDTO(Integer id,
                                   String type,
                                   String region,
                                   String unit) {
}
