package islab.project.conflictsserver.commodities;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/commodities")
@RestController
public class CommoditiesController {
    private final CommoditiesService commoditiesService;

    public CommoditiesController(CommoditiesService commoditiesService) {
        this.commoditiesService = commoditiesService;
    }

    @GetMapping()
    public List<Commodity> findAll() {
        return commoditiesService.findAll();
    }
}
