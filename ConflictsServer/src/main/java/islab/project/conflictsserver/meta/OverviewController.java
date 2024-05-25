package islab.project.conflictsserver.meta;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/overview")
public class OverviewController {
    private final OverviewService overviewService;

    public OverviewController(OverviewService overviewService) {
        this.overviewService = overviewService;
    }

    @GetMapping()
    public CommodityConflictDTO getCommodityCategoriesAndConflicts() {
        return overviewService.getCommodityCategoriesAndConflicts();
    }
}
